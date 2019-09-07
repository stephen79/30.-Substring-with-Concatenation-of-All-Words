import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eclipsesource.json.JsonArray;

class Solution {
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> list = new ArrayList<Integer>();
		int strLen = s.length();
		pn("s:" + s);
		int word_count = words.length;
		if (word_count == 0) {
			return list;
		}
		int jump = words[0].length();
		Integer[] found = new Integer[s.length()];
		
		for (int i = 0 ; i < words.length; i++) {
			String target = words[i];
			pn("words:" + target);
			int index = 0;
			int foundIndex = 0;
			while( index < strLen) {
				foundIndex = s.substring(index).indexOf(target);
				if (foundIndex >= 0) {
					pn("foundIndex:" + (foundIndex + index));
					if (found[foundIndex + index] == null) {
						found[foundIndex + index] = (1 << i);
					}
					else {
						found[foundIndex + index] += (1 << i);
					}

					index = foundIndex + index + jump;
				}
				else {
					break;
				}
				
			}
			
		}
		pn("found:" + Arrays.toString(found));
		pn("jump:" + jump);
		pn("words:" + words.length);
		int complete = (1 << words.length) - 1;
		pn("complete:" + complete);
		int count = 0;
		int index = 0;
		int startIndex = 0;
		while(index < strLen) {
			if (found[index] != null) {
				startIndex = index;
				pn("startIndex:" + startIndex);
				count = 0;
				int step = 0;
				while(found[startIndex] != null) {
					int values = found[startIndex];
					boolean shoudBreak = false;
					int value = values;
					step++;
					count += value;
					pn("step:" + step);
					pn("count:" + count);
					if (step == word_count) {
						if (count == complete) {
							pn("add:" + index);
							list.add(index);
						}
						shoudBreak = true;
						break;
					}
					startIndex += jump;
					if (startIndex >= strLen) {
						shoudBreak = true;
						break;
					}
					if (shoudBreak) {
						break;
					}
				}
				index++;
			}
			else {
				index++;
			}
		}

		return list;
	}
	
	public void pn(String str) {
		System.out.println(str);
	}
}

public class MainClass {
	public static String stringToString(String input) {
		return JsonArray.readFrom("[" + input + "]").get(0).asString();
	}

	public static String[] stringToStringArray(String line) {
		JsonArray jsonArray = JsonArray.readFrom(line);
		String[] arr = new String[jsonArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = jsonArray.get(i).asString();
		}
		return arr;
	}

	public static String integerArrayListToString(List<Integer> nums, int length) {
		if (length == 0) {
			return "[]";
		}

		String result = "";
		for (int index = 0; index < length; index++) {
			Integer number = nums.get(index);
			result += Integer.toString(number) + ", ";
		}
		return "[" + result.substring(0, result.length() - 2) + "]";
	}

	public static String integerArrayListToString(List<Integer> nums) {
		return integerArrayListToString(nums, nums.size());
	}

	public static void main(String[] args) throws IOException {
		// System.out.println("Hello" + ((1 << 2) & 2));
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		String line;
//		while ((line = in.readLine()) != null) {
//			String s = stringToString(line);
//			line = in.readLine();
//			String[] words = stringToStringArray(line);
//
//			List<Integer> ret = new Solution().findSubstring(s, words);
//
//			String out = integerArrayListToString(ret);
//
//			System.out.print(out);
//		}
		String s = "barfoothefoobarman";
		String[] words = stringToStringArray("		[\"foo\",\"bar\"]      ");
//		String s = "wordgoodgoodgoodbestword";
//		String[] words = stringToStringArray(" [\"word\",\"good\",\"best\",\"good\"]  ");
		List<Integer> ret = new Solution().findSubstring(s, words);

		String out = integerArrayListToString(ret);

		System.out.print(out);
		
	}
}