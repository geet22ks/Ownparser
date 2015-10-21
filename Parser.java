import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	public static void main(String[] args) throws IOException {
		HashMap<Integer, Integer> Questions = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> Answers = new HashMap<Integer, Integer>();
		String file = "posts.xml";

		int[] ids = null;
		int pti = 0;
		ArrayList listquestion = new ArrayList();
		ArrayList listanswer = new ArrayList();
		
		String s = null;
		int j = 0;
		int c = 0;
		int x = 0;
		int id = 0, f = 0;
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				int i = 0;
				int k = 0;
				m = p.matcher(line);
				while (m.find()) {
					s = m.group(1);

					if (i == 1) {
						if (stringnotnull(s) && isNumeric(s)) {
							pti = Integer.parseInt(s);
						}
					} else if (i == 7 || i == 6 && isNumeric(s)) {
						if (stringnotnull(s) && isNumeric(s)) {
							id = Integer.parseInt(s);
						}
						if (pti == 1 && (i == 7 || i == 6 && isNumeric(s))) {
							if (Questions.containsKey(id)) {
								Questions.put(id, (Questions.get(id) + 1));
								break;
							} else {
								Questions.put(id, 1);
								break;
							}
						} else if (pti == 2) {
							if (Answers.containsKey(id))
								Answers.put(id, Answers.get(id) + 1);

							else
								Answers.put(id, 1);
						}
					}

					i++;
					if (i == 10)
						break;
				}
				f++;
			}

			System.out.println(f);
			Map<Integer, Integer> tmp = new HashMap<Integer, Integer>();
			tmp = sortByComparator(Questions);
			Map<Integer, Integer> tmpans = new HashMap<Integer, Integer>();
			tmpans = sortByComparator(Answers);

			int l = 0;
			for (Entry<Integer, Integer> entry : tmp.entrySet()) {

				int key = entry.getKey();
				listquestion.add(key);
				Integer value4 = entry.getValue();
				l++;
				if (l == 10) {
					break;
				}
			}
			l=0;
			for (Entry<Integer, Integer> entry : tmpans.entrySet()) {

				int key = entry.getKey();
				listanswer.add(key);
				Integer value4 = entry.getValue();
				l++;
				if (l == 10) {
					break;
				}
			}

		}
		System.out.println("Top 10 users for questions");
		printnames(listquestion);
		System.out.println("\n\n\n\nTop 10 users for answers");
		printnames(listanswer);
			
	}

	private static void printnames(ArrayList l) throws IOException {
		String file1 = "users.xml";
		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m;
		String s = null;
		int c =0;
		Integer id = 0, foundid = 0;
		boolean b = false;
        String line=br1.readLine();
        br1.readLine();
		for (String line1; (line = br1.readLine()) != null;) {
			c=0;
			m = p.matcher(line);
			int k=0;
			while (m.find()) {
				s = m.group(1);
//		
				if (c == 0) {
					
			    if (isNumeric(s)) {
	            id = Integer.parseInt(s);
	    //      System.out.println(id);
			    }
					for (int i = 0; i < 10; i++) {
						//System.out.println("dfdfd"+l.get(i));
						
						if (l.get(i).equals(id)) {
                        //System.out.println("fff"+l.get(i));
							b = true;
							foundid = (Integer) l.get(i);
						}

					}}
					if (b == true && c == 3) {
						s = m.group(1);
						System.out.println( " User Id : "+foundid + "  Name:" + s);
						b = false;
						break;
					}
					c++;
					//b=true;
				
				
			}
		}

		// TODO Auto-generated method stub

	}

	private static boolean stringnotnull(String s) {
		if (s != null)
			return true;

		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isNumeric(String s) {
		return s.matches("-?\\d+(\\.\\d+)?");
		// // TODO Auto-generated method stub

	}

	private static Map<Integer, Integer> sortByComparator(
			Map<Integer, Integer> unsortMap) {

		// Convert Map to List
		LinkedList<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(
				unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1,
					Map.Entry<Integer, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it
				.hasNext();) {
			Map.Entry<Integer, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}