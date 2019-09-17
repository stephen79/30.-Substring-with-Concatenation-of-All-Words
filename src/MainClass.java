import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eclipsesource.json.JsonArray;

class Solution {
	public List<Integer> findSubstring(String s, String[] words) {

		int[] counts = new int[s.length()];
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		buildMap(words, wordMap);
		List<Integer> list = new ArrayList<Integer>();
		if (s == null || s == "" || words.length == 0) {
			return list;
		}
		int word_len = words[0].length();
		int word_count = words.length;
        int l_index = 0;
        int r_index = 0;
        int str_len = s.length();
        int sub_str_len = word_len*word_count;
        Map<String, Integer> currWordMap = new HashMap<String, Integer>();
        for (int i = 0; i < word_len; i++) {
        	pn("i:" + i + " sub_str_len:" + sub_str_len + " str_len:" + str_len);
        	l_index = i;
        	r_index = l_index;
        	currWordMap = new HashMap<String, Integer>();
	        while (l_index + sub_str_len <= str_len && r_index + word_len <= str_len) {
	        	String currentStr = s.substring(r_index, r_index + word_len);
	        	// valid word
	        	if (wordMap.containsKey(currentStr)) {
	        		int count = wordMap.get(currentStr);
	        		int collect = currWordMap.getOrDefault(currentStr, 0);
	        		if (collect < count) {
	        			pn("add:" + currentStr);
	        			// add into collection and move toward
	        			currWordMap.put(currentStr, currWordMap.getOrDefault(currentStr, 0) + 1);
	        			r_index += word_len;
	        			if (r_index - l_index == sub_str_len) {
	        				pn("sol:" + l_index);
	        				list.add(l_index);
	        				String word = s.substring(l_index, l_index + word_len);
	        				currWordMap.put(word, currWordMap.getOrDefault(word, 0) - 1);
	        				l_index += word_len;
	        			}
	        		}
	        		else {
	        			// have enough currentStr, try to remove from the beginning
	        			int indexOfCurrStr = l_index + s.substring(l_index, r_index).indexOf(currentStr);
	        			for (int index = l_index; index + word_len <= r_index; index = index + word_len) {
	        				String word = s.substring(index, index + word_len);
	        				pn("remove:" + word);
	        				currWordMap.put(word, currWordMap.getOrDefault(word, 0) - 1);
	        				l_index += word_len;
	        				if (l_index == indexOfCurrStr + word_len) {
	        					break;
	        				}
	        			}
	        			pn("add after remove:" + currentStr);
	        			currWordMap.put(currentStr, currWordMap.getOrDefault(currentStr, 0) + 1);
	        			r_index += word_len;
	        		}
	        	}
	        	else {
	        		currWordMap = new HashMap<String, Integer>();
	        		l_index = r_index + word_len;
	        		r_index = l_index;
	        	}
	        }
        }

		return list;
	}
	
	public void pn(String str) {
		System.out.println(str);
	}
	public void buildMap(String[] words, Map<String, Integer> wordMap) {
		for (int i=0; i < words.length; i++) {
			wordMap.put(words[i], wordMap.getOrDefault(words[i], 0) + 1);
		}
	}
	public List<Integer> mod_solution(String s, String[] words) {

		int[] counts = new int[s.length()];
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		buildMap(words, wordMap);
		List<Integer> list = new ArrayList<Integer>();
		if (s == null || s == "" || words.length == 0) {
			return list;
		}
		int word_len = words[0].length();
		int word_count = words.length;
		int collect_words = 0;
		Map<String, Integer> currWordMap;
		pn("s:" + s);
		// every position in s has been divided into word_len parts
		// 0, word_len, 2*word_len, 3*word_len
		// 1, 1+word_len, 1+2*word_len, 1+3*word_len
		for (int i = 0 ; i < word_len; i++) {
			int beginningIndex = i;
			pn("i:" + i);
			currWordMap = new HashMap<String, Integer>();
			collect_words = 0;
			for (int j = i ; j <= s.length() - word_len; j = j + word_len) {
				if (beginningIndex + (word_count - collect_words)*word_len > s.length()) {
                    break;
                }
				String currentStr = s.substring(j, j+word_len);
				pn("currentStr:" + currentStr);
				Integer inMapCount = wordMap.get(currentStr);
				Integer inCurrentMapCount = currWordMap.get(currentStr);
				// A valid word
				if (inMapCount != null) {
					// But we have enough
					if (inMapCount == inCurrentMapCount) {
						// Let's remove from the beginning of our collected words (currWordMap) until empty or found the same word as currentStr 
						// So that we can move toward with this currentStr
						String beginStr;
						if (s.substring(beginningIndex, j).contains(currentStr)) {
							do {
								beginStr = s.substring(beginningIndex, beginningIndex + word_len);
								currWordMap.put(beginStr, currWordMap.get(beginStr) - 1);
								collect_words--;
								beginningIndex += word_len;
								// found same string in the collected words as the currentStr
								if (beginStr.equals(currentStr)) {
									break;
								}
							} while (collect_words > 0);
						}
						else {
							collect_words = 0;
							currWordMap = new HashMap<String, Integer>();
						}
						// The do while must end with either found the same currentStr or the collect_words becomes empty
						if (collect_words == 0) {
							beginningIndex = j;
						}
						currWordMap.put(currentStr, currWordMap.get(currentStr) + 1);
						collect_words++;
						pn("have enough:" + collect_words);
						pn("beginningIndex:" + beginningIndex);
					}
					else {
						// add to our current collect words
						if (collect_words == 0) {
							beginningIndex = j;
						}
						if (currWordMap.containsKey(currentStr)) {
							currWordMap.put(currentStr, currWordMap.get(currentStr) + 1);
						}
						else {
							currWordMap.put(currentStr, 1);
						}
						collect_words++;
						pn("add into:" + collect_words);
					}
				}
				else {
					// Let's reset our current collection
					currWordMap = new HashMap<String, Integer>();
					collect_words = 0;
					pn("reset:" + collect_words);
				}
				
				if (collect_words == word_count) {
					list.add(beginningIndex);
					pn("FOUND:" + beginningIndex);
				}
			}
			
		}

		return list;
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
		String s = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
		String[] words = stringToStringArray(" [\"fooo\",\"barr\",\"wing\",\"ding\",\"wing\"] ");
		
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
//		String s = "barfoothefoobarman";
////		String[] words = stringToStringArray("		[\"foo\",\"bar\"]      ");
//		 String s = "wordgoodgoodgoodbestword";
//		 String[] words = stringToStringArray(" [\"word\",\"good\",\"best\",\"good\"]  ");
		// String s = "aaaaaaaa";
		// String[] words = stringToStringArray(" [\"aa\",\"aa\",\"aa\"] ");
		// String s = "ejwwmybnorgshugzmoxopwuvshlcwasclobxmckcvtxfndeztdqiakfusswqsovdfwatanwxgtctyjvsmlcoxijrahivwfybbbudosawnfpmomgczirzscqvlaqhfqkithlhbodptvdhjljltckogcjsdbbktotnxgwyuapnxuwgfirbmdrvgapldsvwgqjfxggtixjhshnzphcemtzsvodygbxpriwqockyavfscvtsewyqpxlnnqnvrkmjtjbjllilinflkbfoxdhocsbpirmcbznuioevcojkdqvoraeqdlhffkwqbjsdkfxstdpxryixrdligpzldgtiqryuasxmxwgtcwsvwasngdwovxzafuixmjrobqbbnhwpdokcpfpxinlfmkfrfqrtzkhabidqszhxorzfypcjcnopzwigmbznmjnpttflsmjifknezrneedvgzfmnhoavxqksjreddpmibbodtbhzfehgluuukupjmbbvshzxyniaowdjamlfssndojyyephstlonsplrettspwepipwcjmfyvfybxiuqtkdlzqedjxxbvdsfurhedneauccrkyjfiptjfxmpxlssrkyldfriuvjranikluqtjjcoiqffdxaukagphzycvjtvwdhhxzagkevvuccxccuoccdkbboymjtimdrmerspxpktsmrwrlkvpnhqrvpdekmtpdfuxzjwpvqjjhfaupylefbvbsbhdncsshmrhxoyuejenqgjheulkxjnqkwvzznriclrbzryfaeuqkfxrbldyusoeoldpbwadhrgijeplijcvqbormrqglgmzsprtmryvkeevlthvflsvognbxfjilwkdndyzwwxgdbeqwlldyezmkopktzugxgkklimhhjqkmuaifnodtpredhqygmedtqpezboimeuyyujfjxkdmbjpizpqltvgknnlodtbhnbhjkmuhwxvzgmkhbcvvadhnssbvneecglnqxhavhvxpkjxlluilzpysjcnwguyofnhfvhaceztoiscumkhociglkvispihvyoatxcxbeqsmluixgsliatukrecgoldmzfhwkgaqzsckonjuhxdhqztjfxstjvikdrhpyjfxbjjryslfpqoiphrwfjqqhaamrjbrsiovrxmqsyxhqmritjeauwqbwtpqcqhvyyssvfknfhxvtodpzipueixdbntdfcaeatyyainfpkclbgaaqrwwzwbcjwiqzkwzfuxfclmsxpdyvfbnwxjytnaejivivriamhgqsskqhnqeurttrfrmstrbeokzhuzvbfmwywohmgogyhzpmsdemugqkspsmoppwbnwabdmiruibwznqcuczculujfiavzwynsyqxmarjkshjhxobandwyzggjibjgzyaaqxorqxbkenscbveqbaociwmqxxyzvyblypeongzrttvwqzmrccwkzidyfbxcaypyquodcpwxkstbthuvjqgialhfmgjohzoxvdaxuywfqrgmyahhtpqtazbphmfoluliznftodyguesshcacrsvutylalqrykehjuofisdookjhrljvedsywrlyccpaowjaqyfaqioesxnlkwgpbznzszyudpwrlgrdgwdyhucztsneqttsuirmjriohhgunzatyfrfzvgvptbgpwajgtysligupoqeoqxoyqtzozufvvlktnvahvsseymtpeyfvxttqosgpplkmxwgmsgtpantazppgnubmpwcdqkvhwfuvcahwibniohiqyywnuzzmxeppokxksrfwrpuzqhjgqryorwboxdauhrkxehiwaputeouwxdfoudcoagcxjcuqvenznxxnprgvhasffxtzaxpcfrcovwgrcwqptoekhmgpoywtxruxokcubekzcrqengviwbtgnzvdzrwwkqvacxwgdhffyvjldgvchoiwnfzoyvkiogisdfyjmfomcazigukqlumyzmnzjzhzfpslwsukykwckvktswjdqxdrlsqvsxwxpqkljeyjpulbswwmuhplfueqnvnhukgjarxlxvwmriqjgmxawmndhsvwnjdjvjtxcsjapfogpesxtpypenunfpjuyoevzztctecilqqbxkaqcyhiobvtqgqruumvvhxolbyzsqcrdchhdqprtkkjsccowrjtyjjmkhleanvfpemuublnnyzfabtxsestncfalqenfcswgerbfcqsapzdtscnzugmwlmidtxkvqhbuaecevwhmwkfqmvpgbefpqpsjmdecmixmmbsjxzwvjdmxydechlraajjmoqpcyoqmrjwoiumuzatydzcnktnkeyztoqvogodxxznhvzduzxudwwqhpftwdspuimioanlzobhjakgajafgzxpqckmhdbbnqmcszpuoqbztnftzgahhxwxbgkilnmzfydyxusnnvngksbjabqjaohdvrniezhmxmkxhemwbbclwdxwgngicplzgajmaryzfkyoqlkrmmfirchzrphveuwmvgaxzbwenvteifxuuefnimnadwxhruvoavlzyhfmeasmgrjawongccgfbgoualiaivbhcgvjjnxpggrewglalthmzvgziobrjeanlvyukwlscexbkibvdjhdgnepdiimmkcxhattwglbkicvsfswocbvphmtpwhcgjbnmxgidtlqcnnwtfujhvgzdussqbwynylzvtjapvqtidpdjkpshvrmqlhindhabubyokzdfrwqvnvgzkyhistydagsgnujiviyijdnabfxqbdqnexvwsvzvcsbrmkbkuzsdehghndyqjodnnblfwmaygdstotfkvxozgwhtbhlkvrzismnozqpfthajafuxekzlgigjpsukjvsdihrjzgovnreqwapdkoqswyclqyvbvpedzyoyedvuuamscbxnqnfmmjyehvidnoimmxmtcinwkbqmcobubjjpshucechrqrffqsyscnqoohcsxenypyqhfklloudgmklcejvgynwouzhtfwuuukdbwpmkjrqxeeaipxrokncholathupdetgaktmvmftqjvzyssocftjwemroghrncynmtchhhcaqxbqpthuaafwgrouaxonzocljeuslzsdwvuoodipdpnlhdihaywzmymxdjrqikughquwtenyucjdgrmipiidiwclhuepgyynoslhzahtdqwliktzsddaahohbszhqxxgripqlwlomjbwtuynydoakejmwkvojuwbfltqjfgxqhwkduzbxpdhtpvrzrfjndmsqfizmqxdxtpbpoemekvxzrrakwjxcxqsdasptruqmjtbaapgmkfnbwnlvzlxwdpzfjryanrmzmpzoefapmnsjdgecrdywsabctaegttffigupnwgakylngrrxurtotxqmzxvsqazajvrwsxyeyjteakeudzjxwbjvagnsjntskmocmpgkybqbnwvrwgoskzqkgffpsyhfmxhymqinrbohxlytsmoeleqrjvievpjipsgdkrqeuglrsjnmvdsihicsgkybcjltcswolpsfxdypmlbjotuxewskisnmczfgreuevnjssjifvlqlhkllifxrxkdbjlhcpegmtrelbosyajljvwwedtxbdccpnmreqaqjrxwulpunagwxesbilalrdniqbzxrbpcvmzpyqklsskpwctgqtrjwhrpisocwderqfiqxsdpkphjsapkvhvsqojyixaechvuoemmyqdlfkuzmlliugckuljfkljoshjhlvvlnywvjswvekfyqhjnsusefdtakejxbejrchoncklguqgnyrcslwztbstmycjziuskegagtlonducdogwbevugppsptdqbajmepmmizaycwcgmjeopbivsyphtvxvvgjbyxpgwpganjiaumojpyhhywosrmnouwpstgbrvhtlqcnmqbygbfnabesvshjmdbhyhirfrkqkmfwdgujhzyjdcbyuijjnkqluaczrnrbbwaeeupnwqzbsazplkyaxqorqsshhlljjlpphhedxdepgfgrqerpuhgmaawhnhqwsgnznrfmxjbdrkwjopylxezxgvetcvrwdewsxdeumhzfrvoilmvksuhyqltuimrnsphqslmgvmmojawwptghonigbdclqtbikiacwpjrbxhmzejozpypfixglatdvuogdoizdtsgsztsfcihtgwyqugeuahpuvvzmgarbsyuutmbxuisdfrvbxzxzhmuektssuktoknkfbmcwwubbnwenybmfqglaceuyqnoadzfenjcjfdlvcpiatuhjdujhaffqsvqvuxchgerokejovrqonxxstibunikiedfyahijobxyhimebctobsjudkqstbcxgixgrhpfiofpwruzvpqyjzvollheoldutddnksutjakhtghpxxnjykxjwgqmsvhnykclexepxqxqzghwfxfdhfmflesfabvanxlrurjtigkjotftqnwyskffpxlragrnfffawqtgyfpmzxfpkdpenxlewyxxgrkmwrmshhzfnorolyfxbvdrspxqnxnuoygkruczddgssygfymdcjgvdxutlrhffhnpyjuxmxefrelxezcgikdliyhvpocvvpkvagvmezrxffujeysplvavtjqjxsgujqsjznxforctwzecxyrkwufpdxadrgzczrnyelfschnagucguuqqqwitviynrypsrdswqxqsegulcwrwsjnihxedfcqychqumiscfkwmqqxunqrfbgqjdwmkyelbldxympctbzfupeocwhkypchuyvhybsbmvymjppfrqmlfrbkpjwpyyytytawuuyjrwxboogfessmltwdcssdqtwomymjskujjtmxiueopwacrwfuqazitvyhvlspvoaeipdsjhgyfjbxhityisidnhlksfznubucqxwaheamndjxmcxwufajmnveuwuoyosqnoqwvtjkwuhkzghvmjhawcfszbhzrbpgsidnbmxxihihnrfbamcyojqpkzodbejtmmipahojoysepzhpljpaugrghgjimtdahnpivdtlcnptnxjyiaafislqavamqgmxtdfoiaakorebqpbbpegawrqymqkewycsdjglkiwaacdqterkixkgraedtqirqmjtvsfhadhafktyrmkzmvidxmisfskvevpcnujqxrqedleuyowkjgphsxzzqlvujkwwgiodbfjesnbsbzcnftuzrvzjjudsgcqmmfpnmyrenuxotbbyvxyovzxgtcyzgqnsvcfhczoptnfnojnlinbfmylhdlijcvcxzjhdixuckaralemvsnbgooorayceuedtomzyjtctvtwgyiesxhynvogxnjdjphcftbefxgasawzagfugmuthjahylkhatlgpnkuksuesrduxkodwjzgubpsmzzmvkskzeglxaqrrvmrgcwcnvkhwzbibaxwnriowoavosminabvfxastkcrkdclgzjvqrjofjjvbyfragofeoazzeqljuypthkmywaffmcjkickqqsuhsviyovhitxeajqahshpejaqtcdkuvgdpclnsguabtgbfwdmrmbvydorfrbcokfdmtsgboidkpgpnmdeyhawkqqshtwxdbarwuxykgduxjlkxppwyruihkcqgynjcpbylayvgdqfpbqmshksyfbhrfxxemhgbkgmkhjtkzyzdqmxxwqvdtevyducpdksntgyaqtkrrkwiyuhukfadjvdnrievszilfinxbyrvknfihmetreydbcstkwoexwsfhfekfvfplmxszcosgovisnbemrjlndqwkvhqsofdbdychmupcsxvhazvrihhnxfyumonbvqeyoghccxfuwacxzxqkezxefxarnnujgyjugrzjoefmghjfhcrnbrtgouaehwnnxwkdplodpuqxdbemfwahptpfppjzowoltyqijfoabgzejerpatwponuefgdtcrgxswiddygeeflpjeelzccnsztxfyqhqyhkuppapvgvdtkmxraytcolbhkiiasaazkvqzvfxbaaxkoudovxrjkusxdazxaawmvoostlvvnsfbpjqkijvudpriqrfsrdfortimgdhtypunakzituezjyhbrpuksbamuiycngvlvpyvczfxvlwhjgicvempfobbwadkiavdswyuxdttoqaaykctprkwfmyeodowglzyjzuhencufcwdobydslazxadnftllhmjslfbrtdlahkgwlebdpdeofidldoymakfnpgekmsltcrrnxvspywfggjrmxryybdltmsfykstmlnzjitaipfoyohkmzimcozxardydxtpjgquoluzbznzqvlewtqyhryjldjoadgjlyfckzbnbootlzxhupieggntjxilcqxnocpyesnhjbauaxcvmkzusmodlyonoldequfunsbwudquaurogsiyhydswsimflrvfwruouskxjfzfynmrymyyqsvkajpnanvyepnzixyteyafnmwnbwmtojdpsucthxtopgpxgnsmnsrdhpskledapiricvdmtwaifrhnebzuttzckroywranbrvgmashxurelyrrbslxnmzyeowchwpjplrdnjlkfcoqdhheavbnhdlltjpahflwscafnnsspikuqszqpcdyfrkaabdigogatgiitadlinfyhgowjuvqlhrniuvrketfmboibttkgakohbmsvhigqztbvrsgxlnjndrqwmcdnntwofojpyrhamivfcdcotodwhvtuyyjlthbaxmrvfzxrhvzkydartfqbalxyjilepmemawjfxhzecyqcdswxxmaaxxyifmouauibstgpcfwgfmjlfhketkeshfcorqirmssfnbuqiqwqfhbmol";
		// String[] words = stringToStringArray("[\"toiscumkhociglkvispihvyoatxcx\",\"ndojyyephstlonsplrettspwepipw\",\"yzfkyoqlkrmmfirchzrphveuwmvga\",\"mxxihihnrfbamcyojqpkzodbejtmm\",\"fenjcjfdlvcpiatuhjdujhaffqsvq\",\"ehghndyqjodnnblfwmaygdstotfkv\",\"heoldutddnksutjakhtghpxxnjykx\",\"cvrwdewsxdeumhzfrvoilmvksuhyq\",\"ftqjvzyssocftjwemroghrncynmtc\",\"idiwclhuepgyynoslhzahtdqwlikt\",\"eurttrfrmstrbeokzhuzvbfmwywoh\",\"jxlluilzpysjcnwguyofnhfvhacez\",\"uskegagtlonducdogwbevugppsptd\",\"xmcxwufajmnveuwuoyosqnoqwvtjk\",\"wolpsfxdypmlbjotuxewskisnmczf\",\"fjryanrmzmpzoefapmnsjdgecrdyw\",\"jgmxawmndhsvwnjdjvjtxcsjapfog\",\"wuhkzghvmjhawcfszbhzrbpgsidnb\",\"yelbldxympctbzfupeocwhkypchuy\",\"vzduzxudwwqhpftwdspuimioanlzo\",\"bdpdeofidldoymakfnpgekmsltcrr\",\"fmyeodowglzyjzuhencufcwdobyds\",\"dhtypunakzituezjyhbrpuksbamui\",\"bdmiruibwznqcuczculujfiavzwyn\",\"eudzjxwbjvagnsjntskmocmpgkybq\",\"tuynydoakejmwkvojuwbfltqjfgxq\",\"psrdswqxqsegulcwrwsjnihxedfcq\",\"cokfdmtsgboidkpgpnmdeyhawkqqs\",\"fujhvgzdussqbwynylzvtjapvqtid\",\"rqeuglrsjnmvdsihicsgkybcjltcs\",\"vhybsbmvymjppfrqmlfrbkpjwpyyy\",\"aukagphzycvjtvwdhhxzagkevvucc\",\"hwkduzbxpdhtpvrzrfjndmsqfizmq\",\"ywnuzzmxeppokxksrfwrpuzqhjgqr\",\"qbajmepmmizaycwcgmjeopbivsyph\",\"uamscbxnqnfmmjyehvidnoimmxmtc\",\"nxvspywfggjrmxryybdltmsfykstm\",\"amrjbrsiovrxmqsyxhqmritjeauwq\",\"yorwboxdauhrkxehiwaputeouwxdf\",\"qkewycsdjglkiwaacdqterkixkgra\",\"ycngvlvpyvczfxvlwhjgicvempfob\",\"jgphsxzzqlvujkwwgiodbfjesnbsb\",\"mkxhemwbbclwdxwgngicplzgajmar\",\"mryvkeevlthvflsvognbxfjilwkdn\",\"mezrxffujeysplvavtjqjxsgujqsj\",\"rtotxqmzxvsqazajvrwsxyeyjteak\",\"sabctaegttffigupnwgakylngrrxu\",\"xccuoccdkbboymjtimdrmerspxpkt\",\"xusnnvngksbjabqjaohdvrniezhmx\",\"oyuejenqgjheulkxjnqkwvzznricl\",\"mxszcosgovisnbemrjlndqwkvhqso\",\"wsgnznrfmxjbdrkwjopylxezxgvet\",\"dxmisfskvevpcnujqxrqedleuyowk\",\"dhrgijeplijcvqbormrqglgmzsprt\",\"vuxchgerokejovrqonxxstibuniki\",\"lumyzmnzjzhzfpslwsukykwckvkts\",\"inwkbqmcobubjjpshucechrqrffqs\",\"ywtxruxokcubekzcrqengviwbtgnz\",\"ccpnmreqaqjrxwulpunagwxesbila\",\"pesxtpypenunfpjuyoevzztctecil\",\"sygfymdcjgvdxutlrhffhnpyjuxmx\",\"uisdfrvbxzxzhmuektssuktoknkfb\",\"cejvgynwouzhtfwuuukdbwpmkjrqx\",\"oudcoagcxjcuqvenznxxnprgvhasf\",\"sxnlkwgpbznzszyudpwrlgrdgwdyh\",\"qqbxkaqcyhiobvtqgqruumvvhxolb\",\"mkhleanvfpemuublnnyzfabtxsest\",\"bibaxwnriowoavosminabvfxastkc\",\"bcxgixgrhpfiofpwruzvpqyjzvoll\",\"lzccnsztxfyqhqyhkuppapvgvdtkm\",\"pdjkpshvrmqlhindhabubyokzdfrw\",\"qbbnhwpdokcpfpxinlfmkfrfqrtzk\",\"rnyelfschnagucguuqqqwitviynry\",\"qtrjwhrpisocwderqfiqxsdpkphjs\",\"vxttqosgpplkmxwgmsgtpantazppg\",\"tyisidnhlksfznubucqxwaheamndj\",\"kgaqzsckonjuhxdhqztjfxstjvikd\",\"jeuslzsdwvuoodipdpnlhdihaywzm\",\"vdzrwwkqvacxwgdhffyvjldgvchoi\",\"cftbefxgasawzagfugmuthjahylkh\",\"xraytcolbhkiiasaazkvqzvfxbaax\",\"oyqtzozufvvlktnvahvsseymtpeyf\",\"rnnujgyjugrzjoefmghjfhcrnbrtg\",\"rfzvgvptbgpwajgtysligupoqeoqx\",\"igbdclqtbikiacwpjrbxhmzejozpy\",\"dyzwwxgdbeqwlldyezmkopktzugxg\",\"hmetreydbcstkwoexwsfhfekfvfpl\",\"zcnftuzrvzjjudsgcqmmfpnmyrenu\",\"zzmvkskzeglxaqrrvmrgcwcnvkhwz\",\"vjswvekfyqhjnsusefdtakejxbejr\",\"rwwzwbcjwiqzkwzfuxfclmsxpdyvf\",\"fdbdychmupcsxvhazvrihhnxfyumo\",\"vdtevyducpdksntgyaqtkrrkwiyuh\",\"nbvqeyoghccxfuwacxzxqkezxefxa\",\"vpgbefpqpsjmdecmixmmbsjxzwvjd\",\"jwgqmsvhnykclexepxqxqzghwfxfd\",\"olyfxbvdrspxqnxnuoygkruczddgs\",\"qgmxtdfoiaakorebqpbbpegawrqym\",\"liaivbhcgvjjnxpggrewglalthmzv\",\"choncklguqgnyrcslwztbstmycjzi\",\"fpkdpenxlewyxxgrkmwrmshhzfnor\",\"hhhcaqxbqpthuaafwgrouaxonzocl\",\"ipahojoysepzhpljpaugrghgjimtd\",\"wosrmnouwpstgbrvhtlqcnmqbygbf\",\"nwyskffpxlragrnfffawqtgyfpmzx\",\"bcvvadhnssbvneecglnqxhavhvxpk\",\"hoavxqksjreddpmibbodtbhzfehgl\",\"lazxadnftllhmjslfbrtdlahkgwle\",\"uuukupjmbbvshzxyniaowdjamlfss\",\"tpqtazbphmfoluliznftodyguessh\",\"ychqumiscfkwmqqxunqrfbgqjdwmk\",\"rkdclgzjvqrjofjjvbyfragofeoaz\",\"pphhedxdepgfgrqerpuhgmaawhnhq\",\"cacrsvutylalqrykehjuofisdookj\",\"kyldfriuvjranikluqtjjcoiqffdx\",\"bnwvrwgoskzqkgffpsyhfmxhymqin\",\"uzmlliugckuljfkljoshjhlvvlnyw\",\"abfxqbdqnexvwsvzvcsbrmkbkuzsd\",\"xotbbyvxyovzxgtcyzgqnsvcfhczo\",\"bwtpqcqhvyyssvfknfhxvtodpzipu\",\"nsfbpjqkijvudpriqrfsrdfortimg\",\"tgwyqugeuahpuvvzmgarbsyuutmbx\",\"upnwqzbsazplkyaxqorqsshhlljjl\",\"edfyahijobxyhimebctobsjudkqst\",\"ialhfmgjohzoxvdaxuywfqrgmyahh\",\"jlhcpegmtrelbosyajljvwwedtxbd\",\"tpfppjzowoltyqijfoabgzejerpat\",\"mgogyhzpmsdemugqkspsmoppwbnwa\",\"nubmpwcdqkvhwfuvcahwibniohiqy\",\"ukfadjvdnrievszilfinxbyrvknfi\",\"dgnepdiimmkcxhattwglbkicvsfsw\",\"syqxmarjkshjhxobandwyzggjibjg\",\"bnwxjytnaejivivriamhgqsskqhnq\",\"hzyjdcbyuijjnkqluaczrnrbbwaee\",\"yscnqoohcsxenypyqhfklloudgmkl\",\"habidqszhxorzfypcjcnopzwigmbz\",\"wjdqxdrlsqvsxwxpqkljeyjpulbsw\",\"tytawuuyjrwxboogfessmltwdcssd\",\"pfixglatdvuogdoizdtsgsztsfcih\",\"apkvhvsqojyixaechvuoemmyqdlfk\",\"ouaehwnnxwkdplodpuqxdbemfwahp\",\"ixuckaralemvsnbgooorayceuedto\",\"ymxdjrqikughquwtenyucjdgrmipi\",\"smrwrlkvpnhqrvpdekmtpdfuxzjwp\",\"bhjakgajafgzxpqckmhdbbnqmcszp\",\"beqsmluixgsliatukrecgoldmzfhw\",\"greuevnjssjifvlqlhkllifxrxkdb\",\"yzsqcrdchhdqprtkkjsccowrjtyjj\",\"sviyovhitxeajqahshpejaqtcdkuv\",\"qtwomymjskujjtmxiueopwacrwfuq\",\"mzyjtctvtwgyiesxhynvogxnjdjph\",\"dyfbxcaypyquodcpwxkstbthuvjqg\",\"hfmflesfabvanxlrurjtigkjotftq\",\"mxydechlraajjmoqpcyoqmrjwoium\",\"nabesvshjmdbhyhirfrkqkmfwdguj\",\"bhrfxxemhgbkgmkhjtkzyzdqmxxwq\",\"gziobrjeanlvyukwlscexbkibvdjh\",\"mcwwubbnwenybmfqglaceuyqnoadz\",\"xyzvyblypeongzrttvwqzmrccwkzi\",\"ncfalqenfcswgerbfcqsapzdtscnz\",\"dtqpezboimeuyyujfjxkdmbjpizpq\",\"wmuhplfueqnvnhukgjarxlxvwmriq\",\"qwapdkoqswyclqyvbvpedzyoyedvu\",\"uoqbztnftzgahhxwxbgkilnmzfydy\",\"zsddaahohbszhqxxgripqlwlomjbw\",\"bwadkiavdswyuxdttoqaaykctprkw\",\"eixdbntdfcaeatyyainfpkclbgaaq\",\"nmjnpttflsmjifknezrneedvgzfmn\",\"avlzyhfmeasmgrjawongccgfbgoua\",\"kklimhhjqkmuaifnodtpredhqygme\",\"xzbwenvteifxuuefnimnadwxhruvo\",\"ugmwlmidtxkvqhbuaecevwhmwkfqm\",\"rhpyjfxbjjryslfpqoiphrwfjqqha\",\"eeaipxrokncholathupdetgaktmvm\",\"ltuimrnsphqslmgvmmojawwptghon\",\"azitvyhvlspvoaeipdsjhgyfjbxhi\",\"efrelxezcgikdliyhvpocvvpkvagv\",\"znxforctwzecxyrkwufpdxadrgzcz\",\"kcqgynjcpbylayvgdqfpbqmshksyf\",\"hrljvedsywrlyccpaowjaqyfaqioe\",\"cjmfyvfybxiuqtkdlzqedjxxbvdsf\",\"zeqljuypthkmywaffmcjkickqqsuh\",\"wnfzoyvkiogisdfyjmfomcazigukq\",\"zyaaqxorqxbkenscbveqbaociwmqx\",\"ahnpivdtlcnptnxjyiaafislqavam\",\"edtqirqmjtvsfhadhafktyrmkzmvi\",\"wponuefgdtcrgxswiddygeeflpjee\",\"xozgwhtbhlkvrzismnozqpfthajaf\",\"ptnfnojnlinbfmylhdlijcvcxzjhd\",\"uxekzlgigjpsukjvsdihrjzgovnre\",\"rbohxlytsmoeleqrjvievpjipsgdk\",\"fxtzaxpcfrcovwgrcwqptoekhmgpo\",\"tvxvvgjbyxpgwpganjiaumojpyhhy\",\"vqjjhfaupylefbvbsbhdncsshmrhx\",\"urhedneauccrkyjfiptjfxmpxlssr\",\"ltvgknnlodtbhnbhjkmuhwxvzgmkh\",\"ucztsneqttsuirmjriohhgunzatyf\",\"rbzryfaeuqkfxrbldyusoeoldpbwa\",\"atlgpnkuksuesrduxkodwjzgubpsm\",\"lrdniqbzxrbpcvmzpyqklsskpwctg\",\"qvnvgzkyhistydagsgnujiviyijdn\",\"uzatydzcnktnkeyztoqvogodxxznh\",\"ocbvphmtpwhcgjbnmxgidtlqcnnwt\",\"koudovxrjkusxdazxaawmvoostlvv\",\"ptruqmjtbaapgmkfnbwnlvzlxwdpz\",\"xdxtpbpoemekvxzrrakwjxcxqsdas\",\"gdpclnsguabtgbfwdmrmbvydorfrb\",\"htwxdbarwuxykgduxjlkxppwyruih\"]");
//		String s = "barfoofoobarthefoobarman";
//		String[] words = stringToStringArray(" [\"bar\",\"foo\",\"the\"] ");
//				String s = 		"aaa";
//				String[] words = stringToStringArray(" [\"aa\",\"aa\"] ");
				
		List<Integer> ret = new Solution().findSubstring(s, words);

		String out = integerArrayListToString(ret);

		System.out.print(out);
		
	}
}