import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.eclipsesource.json.JsonArray;

class Solution {
	public List<Integer> findSubstring(String s, String[] words) {
		Date date= new Date();
		long time = date.getTime();
		pn("Time in Milliseconds: " + time);
		// long time = 0;
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

					index = foundIndex + index + 1;
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

		int index = 0;
		while(index < strLen) {
			if (found[index] != null) {
				
				boolean full = true;
				for (int i = 0 ; i < word_count; i++) {
					if (found[index + i*jump] == null) {
						full = false;
						break;
					}
				}
				pn("startIndex:" + index + " word_count:" + word_count + " strLen:" + strLen);
				if (full) {
					for (int i = 0 ; i < word_count; i++) {
						if (found[index] != null && ((found[index] & (1 << i)) > 0)) {
							findPath(0, 0, index, index, i, jump, word_count, strLen, complete, list, found, time);
						}
					}
				}
				index++;
			}
			else {
				index++;
			}
		}
		Date date1= new Date();
		long time1 = date1.getTime();
		pn("Time in Milliseconds: " + (time1 - time));
		return list;
	}
	public void findPath(int count, int step, int startIndex, int index, int select, int jump, int word_count, int strLen,
			int complete, List<Integer> list, Integer[] found, long time) {
		Date date1= new Date();
		long time1 = date1.getTime();
//		if (time1 - time > 1000) {
//			return;
//		}
		if ((word_count - step)*jump + startIndex >= strLen) {
			return;
		}
		int value = found[index] & (1<<select);
		if (value == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == startIndex) {
				return;
			}
		}
		step++;
		count += value;
		pn("startIndex:" + startIndex + " strLen:" + strLen + " step:" + step + " index:" + index + " select:" + select);
		pn("word_count: " + word_count + " count:" + count);
		if (step == word_count) {
			if (count == complete) {
				pn("add:" + startIndex);
				list.add(startIndex);
			}
			return;
		}
		pn("debug index:" + index);

		int newindex = index + jump;
		if (newindex >= strLen) {
			return;
		}
		pn("debug index jump:" + newindex);
		for (int i = 0 ; i < word_count; i++) {
			int target_value = found[newindex] & (1<<i);
			if (found[newindex] != null && (target_value > 0)) {
				pn("findPath startIndex:" + startIndex + " strLen:" + strLen + " step:" + step + " newindex:" + newindex + " select:" + i);
				
				findPath(count, step, startIndex, newindex, i, jump, word_count, strLen, complete, list, found, time);
			}
		}
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
//		String s = "barfoothefoobarman";
//		String[] words = stringToStringArray("		[\"foo\",\"bar\"]      ");
		// String s = "wordgoodgoodgoodbestword";
		// String[] words = stringToStringArray(" [\"word\",\"good\",\"best\",\"good\"]  ");
//		String s = "aaaaaaaa";
//		String[] words = stringToStringArray(" [\"aa\",\"aa\",\"aa\"] ");
		String s = "ejwwmybnorgshugzmoxopwuvshlcwasclobxmckcvtxfndeztdqiakfusswqsovdfwatanwxgtctyjvsmlcoxijrahivwfybbbudosawnfpmomgczirzscqvlaqhfqkithlhbodptvdhjljltckogcjsdbbktotnxgwyuapnxuwgfirbmdrvgapldsvwgqjfxggtixjhshnzphcemtzsvodygbxpriwqockyavfscvtsewyqpxlnnqnvrkmjtjbjllilinflkbfoxdhocsbpirmcbznuioevcojkdqvoraeqdlhffkwqbjsdkfxstdpxryixrdligpzldgtiqryuasxmxwgtcwsvwasngdwovxzafuixmjrobqbbnhwpdokcpfpxinlfmkfrfqrtzkhabidqszhxorzfypcjcnopzwigmbznmjnpttflsmjifknezrneedvgzfmnhoavxqksjreddpmibbodtbhzfehgluuukupjmbbvshzxyniaowdjamlfssndojyyephstlonsplrettspwepipwcjmfyvfybxiuqtkdlzqedjxxbvdsfurhedneauccrkyjfiptjfxmpxlssrkyldfriuvjranikluqtjjcoiqffdxaukagphzycvjtvwdhhxzagkevvuccxccuoccdkbboymjtimdrmerspxpktsmrwrlkvpnhqrvpdekmtpdfuxzjwpvqjjhfaupylefbvbsbhdncsshmrhxoyuejenqgjheulkxjnqkwvzznriclrbzryfaeuqkfxrbldyusoeoldpbwadhrgijeplijcvqbormrqglgmzsprtmryvkeevlthvflsvognbxfjilwkdndyzwwxgdbeqwlldyezmkopktzugxgkklimhhjqkmuaifnodtpredhqygmedtqpezboimeuyyujfjxkdmbjpizpqltvgknnlodtbhnbhjkmuhwxvzgmkhbcvvadhnssbvneecglnqxhavhvxpkjxlluilzpysjcnwguyofnhfvhaceztoiscumkhociglkvispihvyoatxcxbeqsmluixgsliatukrecgoldmzfhwkgaqzsckonjuhxdhqztjfxstjvikdrhpyjfxbjjryslfpqoiphrwfjqqhaamrjbrsiovrxmqsyxhqmritjeauwqbwtpqcqhvyyssvfknfhxvtodpzipueixdbntdfcaeatyyainfpkclbgaaqrwwzwbcjwiqzkwzfuxfclmsxpdyvfbnwxjytnaejivivriamhgqsskqhnqeurttrfrmstrbeokzhuzvbfmwywohmgogyhzpmsdemugqkspsmoppwbnwabdmiruibwznqcuczculujfiavzwynsyqxmarjkshjhxobandwyzggjibjgzyaaqxorqxbkenscbveqbaociwmqxxyzvyblypeongzrttvwqzmrccwkzidyfbxcaypyquodcpwxkstbthuvjqgialhfmgjohzoxvdaxuywfqrgmyahhtpqtazbphmfoluliznftodyguesshcacrsvutylalqrykehjuofisdookjhrljvedsywrlyccpaowjaqyfaqioesxnlkwgpbznzszyudpwrlgrdgwdyhucztsneqttsuirmjriohhgunzatyfrfzvgvptbgpwajgtysligupoqeoqxoyqtzozufvvlktnvahvsseymtpeyfvxttqosgpplkmxwgmsgtpantazppgnubmpwcdqkvhwfuvcahwibniohiqyywnuzzmxeppokxksrfwrpuzqhjgqryorwboxdauhrkxehiwaputeouwxdfoudcoagcxjcuqvenznxxnprgvhasffxtzaxpcfrcovwgrcwqptoekhmgpoywtxruxokcubekzcrqengviwbtgnzvdzrwwkqvacxwgdhffyvjldgvchoiwnfzoyvkiogisdfyjmfomcazigukqlumyzmnzjzhzfpslwsukykwckvktswjdqxdrlsqvsxwxpqkljeyjpulbswwmuhplfueqnvnhukgjarxlxvwmriqjgmxawmndhsvwnjdjvjtxcsjapfogpesxtpypenunfpjuyoevzztctecilqqbxkaqcyhiobvtqgqruumvvhxolbyzsqcrdchhdqprtkkjsccowrjtyjjmkhleanvfpemuublnnyzfabtxsestncfalqenfcswgerbfcqsapzdtscnzugmwlmidtxkvqhbuaecevwhmwkfqmvpgbefpqpsjmdecmixmmbsjxzwvjdmxydechlraajjmoqpcyoqmrjwoiumuzatydzcnktnkeyztoqvogodxxznhvzduzxudwwqhpftwdspuimioanlzobhjakgajafgzxpqckmhdbbnqmcszpuoqbztnftzgahhxwxbgkilnmzfydyxusnnvngksbjabqjaohdvrniezhmxmkxhemwbbclwdxwgngicplzgajmaryzfkyoqlkrmmfirchzrphveuwmvgaxzbwenvteifxuuefnimnadwxhruvoavlzyhfmeasmgrjawongccgfbgoualiaivbhcgvjjnxpggrewglalthmzvgziobrjeanlvyukwlscexbkibvdjhdgnepdiimmkcxhattwglbkicvsfswocbvphmtpwhcgjbnmxgidtlqcnnwtfujhvgzdussqbwynylzvtjapvqtidpdjkpshvrmqlhindhabubyokzdfrwqvnvgzkyhistydagsgnujiviyijdnabfxqbdqnexvwsvzvcsbrmkbkuzsdehghndyqjodnnblfwmaygdstotfkvxozgwhtbhlkvrzismnozqpfthajafuxekzlgigjpsukjvsdihrjzgovnreqwapdkoqswyclqyvbvpedzyoyedvuuamscbxnqnfmmjyehvidnoimmxmtcinwkbqmcobubjjpshucechrqrffqsyscnqoohcsxenypyqhfklloudgmklcejvgynwouzhtfwuuukdbwpmkjrqxeeaipxrokncholathupdetgaktmvmftqjvzyssocftjwemroghrncynmtchhhcaqxbqpthuaafwgrouaxonzocljeuslzsdwvuoodipdpnlhdihaywzmymxdjrqikughquwtenyucjdgrmipiidiwclhuepgyynoslhzahtdqwliktzsddaahohbszhqxxgripqlwlomjbwtuynydoakejmwkvojuwbfltqjfgxqhwkduzbxpdhtpvrzrfjndmsqfizmqxdxtpbpoemekvxzrrakwjxcxqsdasptruqmjtbaapgmkfnbwnlvzlxwdpzfjryanrmzmpzoefapmnsjdgecrdywsabctaegttffigupnwgakylngrrxurtotxqmzxvsqazajvrwsxyeyjteakeudzjxwbjvagnsjntskmocmpgkybqbnwvrwgoskzqkgffpsyhfmxhymqinrbohxlytsmoeleqrjvievpjipsgdkrqeuglrsjnmvdsihicsgkybcjltcswolpsfxdypmlbjotuxewskisnmczfgreuevnjssjifvlqlhkllifxrxkdbjlhcpegmtrelbosyajljvwwedtxbdccpnmreqaqjrxwulpunagwxesbilalrdniqbzxrbpcvmzpyqklsskpwctgqtrjwhrpisocwderqfiqxsdpkphjsapkvhvsqojyixaechvuoemmyqdlfkuzmlliugckuljfkljoshjhlvvlnywvjswvekfyqhjnsusefdtakejxbejrchoncklguqgnyrcslwztbstmycjziuskegagtlonducdogwbevugppsptdqbajmepmmizaycwcgmjeopbivsyphtvxvvgjbyxpgwpganjiaumojpyhhywosrmnouwpstgbrvhtlqcnmqbygbfnabesvshjmdbhyhirfrkqkmfwdgujhzyjdcbyuijjnkqluaczrnrbbwaeeupnwqzbsazplkyaxqorqsshhlljjlpphhedxdepgfgrqerpuhgmaawhnhqwsgnznrfmxjbdrkwjopylxezxgvetcvrwdewsxdeumhzfrvoilmvksuhyqltuimrnsphqslmgvmmojawwptghonigbdclqtbikiacwpjrbxhmzejozpypfixglatdvuogdoizdtsgsztsfcihtgwyqugeuahpuvvzmgarbsyuutmbxuisdfrvbxzxzhmuektssuktoknkfbmcwwubbnwenybmfqglaceuyqnoadzfenjcjfdlvcpiatuhjdujhaffqsvqvuxchgerokejovrqonxxstibunikiedfyahijobxyhimebctobsjudkqstbcxgixgrhpfiofpwruzvpqyjzvollheoldutddnksutjakhtghpxxnjykxjwgqmsvhnykclexepxqxqzghwfxfdhfmflesfabvanxlrurjtigkjotftqnwyskffpxlragrnfffawqtgyfpmzxfpkdpenxlewyxxgrkmwrmshhzfnorolyfxbvdrspxqnxnuoygkruczddgssygfymdcjgvdxutlrhffhnpyjuxmxefrelxezcgikdliyhvpocvvpkvagvmezrxffujeysplvavtjqjxsgujqsjznxforctwzecxyrkwufpdxadrgzczrnyelfschnagucguuqqqwitviynrypsrdswqxqsegulcwrwsjnihxedfcqychqumiscfkwmqqxunqrfbgqjdwmkyelbldxympctbzfupeocwhkypchuyvhybsbmvymjppfrqmlfrbkpjwpyyytytawuuyjrwxboogfessmltwdcssdqtwomymjskujjtmxiueopwacrwfuqazitvyhvlspvoaeipdsjhgyfjbxhityisidnhlksfznubucqxwaheamndjxmcxwufajmnveuwuoyosqnoqwvtjkwuhkzghvmjhawcfszbhzrbpgsidnbmxxihihnrfbamcyojqpkzodbejtmmipahojoysepzhpljpaugrghgjimtdahnpivdtlcnptnxjyiaafislqavamqgmxtdfoiaakorebqpbbpegawrqymqkewycsdjglkiwaacdqterkixkgraedtqirqmjtvsfhadhafktyrmkzmvidxmisfskvevpcnujqxrqedleuyowkjgphsxzzqlvujkwwgiodbfjesnbsbzcnftuzrvzjjudsgcqmmfpnmyrenuxotbbyvxyovzxgtcyzgqnsvcfhczoptnfnojnlinbfmylhdlijcvcxzjhdixuckaralemvsnbgooorayceuedtomzyjtctvtwgyiesxhynvogxnjdjphcftbefxgasawzagfugmuthjahylkhatlgpnkuksuesrduxkodwjzgubpsmzzmvkskzeglxaqrrvmrgcwcnvkhwzbibaxwnriowoavosminabvfxastkcrkdclgzjvqrjofjjvbyfragofeoazzeqljuypthkmywaffmcjkickqqsuhsviyovhitxeajqahshpejaqtcdkuvgdpclnsguabtgbfwdmrmbvydorfrbcokfdmtsgboidkpgpnmdeyhawkqqshtwxdbarwuxykgduxjlkxppwyruihkcqgynjcpbylayvgdqfpbqmshksyfbhrfxxemhgbkgmkhjtkzyzdqmxxwqvdtevyducpdksntgyaqtkrrkwiyuhukfadjvdnrievszilfinxbyrvknfihmetreydbcstkwoexwsfhfekfvfplmxszcosgovisnbemrjlndqwkvhqsofdbdychmupcsxvhazvrihhnxfyumonbvqeyoghccxfuwacxzxqkezxefxarnnujgyjugrzjoefmghjfhcrnbrtgouaehwnnxwkdplodpuqxdbemfwahptpfppjzowoltyqijfoabgzejerpatwponuefgdtcrgxswiddygeeflpjeelzccnsztxfyqhqyhkuppapvgvdtkmxraytcolbhkiiasaazkvqzvfxbaaxkoudovxrjkusxdazxaawmvoostlvvnsfbpjqkijvudpriqrfsrdfortimgdhtypunakzituezjyhbrpuksbamuiycngvlvpyvczfxvlwhjgicvempfobbwadkiavdswyuxdttoqaaykctprkwfmyeodowglzyjzuhencufcwdobydslazxadnftllhmjslfbrtdlahkgwlebdpdeofidldoymakfnpgekmsltcrrnxvspywfggjrmxryybdltmsfykstmlnzjitaipfoyohkmzimcozxardydxtpjgquoluzbznzqvlewtqyhryjldjoadgjlyfckzbnbootlzxhupieggntjxilcqxnocpyesnhjbauaxcvmkzusmodlyonoldequfunsbwudquaurogsiyhydswsimflrvfwruouskxjfzfynmrymyyqsvkajpnanvyepnzixyteyafnmwnbwmtojdpsucthxtopgpxgnsmnsrdhpskledapiricvdmtwaifrhnebzuttzckroywranbrvgmashxurelyrrbslxnmzyeowchwpjplrdnjlkfcoqdhheavbnhdlltjpahflwscafnnsspikuqszqpcdyfrkaabdigogatgiitadlinfyhgowjuvqlhrniuvrketfmboibttkgakohbmsvhigqztbvrsgxlnjndrqwmcdnntwofojpyrhamivfcdcotodwhvtuyyjlthbaxmrvfzxrhvzkydartfqbalxyjilepmemawjfxhzecyqcdswxxmaaxxyifmouauibstgpcfwgfmjlfhketkeshfcorqirmssfnbuqiqwqfhbmol";
		String[] words = stringToStringArray("[\"toiscumkhociglkvispihvyoatxcx\",\"ndojyyephstlonsplrettspwepipw\",\"yzfkyoqlkrmmfirchzrphveuwmvga\",\"mxxihihnrfbamcyojqpkzodbejtmm\",\"fenjcjfdlvcpiatuhjdujhaffqsvq\",\"ehghndyqjodnnblfwmaygdstotfkv\",\"heoldutddnksutjakhtghpxxnjykx\",\"cvrwdewsxdeumhzfrvoilmvksuhyq\",\"ftqjvzyssocftjwemroghrncynmtc\",\"idiwclhuepgyynoslhzahtdqwlikt\",\"eurttrfrmstrbeokzhuzvbfmwywoh\",\"jxlluilzpysjcnwguyofnhfvhacez\",\"uskegagtlonducdogwbevugppsptd\",\"xmcxwufajmnveuwuoyosqnoqwvtjk\",\"wolpsfxdypmlbjotuxewskisnmczf\",\"fjryanrmzmpzoefapmnsjdgecrdyw\",\"jgmxawmndhsvwnjdjvjtxcsjapfog\",\"wuhkzghvmjhawcfszbhzrbpgsidnb\",\"yelbldxympctbzfupeocwhkypchuy\",\"vzduzxudwwqhpftwdspuimioanlzo\",\"bdpdeofidldoymakfnpgekmsltcrr\",\"fmyeodowglzyjzuhencufcwdobyds\",\"dhtypunakzituezjyhbrpuksbamui\",\"bdmiruibwznqcuczculujfiavzwyn\",\"eudzjxwbjvagnsjntskmocmpgkybq\",\"tuynydoakejmwkvojuwbfltqjfgxq\",\"psrdswqxqsegulcwrwsjnihxedfcq\",\"cokfdmtsgboidkpgpnmdeyhawkqqs\",\"fujhvgzdussqbwynylzvtjapvqtid\",\"rqeuglrsjnmvdsihicsgkybcjltcs\",\"vhybsbmvymjppfrqmlfrbkpjwpyyy\",\"aukagphzycvjtvwdhhxzagkevvucc\",\"hwkduzbxpdhtpvrzrfjndmsqfizmq\",\"ywnuzzmxeppokxksrfwrpuzqhjgqr\",\"qbajmepmmizaycwcgmjeopbivsyph\",\"uamscbxnqnfmmjyehvidnoimmxmtc\",\"nxvspywfggjrmxryybdltmsfykstm\",\"amrjbrsiovrxmqsyxhqmritjeauwq\",\"yorwboxdauhrkxehiwaputeouwxdf\",\"qkewycsdjglkiwaacdqterkixkgra\",\"ycngvlvpyvczfxvlwhjgicvempfob\",\"jgphsxzzqlvujkwwgiodbfjesnbsb\",\"mkxhemwbbclwdxwgngicplzgajmar\",\"mryvkeevlthvflsvognbxfjilwkdn\",\"mezrxffujeysplvavtjqjxsgujqsj\",\"rtotxqmzxvsqazajvrwsxyeyjteak\",\"sabctaegttffigupnwgakylngrrxu\",\"xccuoccdkbboymjtimdrmerspxpkt\",\"xusnnvngksbjabqjaohdvrniezhmx\",\"oyuejenqgjheulkxjnqkwvzznricl\",\"mxszcosgovisnbemrjlndqwkvhqso\",\"wsgnznrfmxjbdrkwjopylxezxgvet\",\"dxmisfskvevpcnujqxrqedleuyowk\",\"dhrgijeplijcvqbormrqglgmzsprt\",\"vuxchgerokejovrqonxxstibuniki\",\"lumyzmnzjzhzfpslwsukykwckvkts\",\"inwkbqmcobubjjpshucechrqrffqs\",\"ywtxruxokcubekzcrqengviwbtgnz\",\"ccpnmreqaqjrxwulpunagwxesbila\",\"pesxtpypenunfpjuyoevzztctecil\",\"sygfymdcjgvdxutlrhffhnpyjuxmx\",\"uisdfrvbxzxzhmuektssuktoknkfb\",\"cejvgynwouzhtfwuuukdbwpmkjrqx\",\"oudcoagcxjcuqvenznxxnprgvhasf\",\"sxnlkwgpbznzszyudpwrlgrdgwdyh\",\"qqbxkaqcyhiobvtqgqruumvvhxolb\",\"mkhleanvfpemuublnnyzfabtxsest\",\"bibaxwnriowoavosminabvfxastkc\",\"bcxgixgrhpfiofpwruzvpqyjzvoll\",\"lzccnsztxfyqhqyhkuppapvgvdtkm\",\"pdjkpshvrmqlhindhabubyokzdfrw\",\"qbbnhwpdokcpfpxinlfmkfrfqrtzk\",\"rnyelfschnagucguuqqqwitviynry\",\"qtrjwhrpisocwderqfiqxsdpkphjs\",\"vxttqosgpplkmxwgmsgtpantazppg\",\"tyisidnhlksfznubucqxwaheamndj\",\"kgaqzsckonjuhxdhqztjfxstjvikd\",\"jeuslzsdwvuoodipdpnlhdihaywzm\",\"vdzrwwkqvacxwgdhffyvjldgvchoi\",\"cftbefxgasawzagfugmuthjahylkh\",\"xraytcolbhkiiasaazkvqzvfxbaax\",\"oyqtzozufvvlktnvahvsseymtpeyf\",\"rnnujgyjugrzjoefmghjfhcrnbrtg\",\"rfzvgvptbgpwajgtysligupoqeoqx\",\"igbdclqtbikiacwpjrbxhmzejozpy\",\"dyzwwxgdbeqwlldyezmkopktzugxg\",\"hmetreydbcstkwoexwsfhfekfvfpl\",\"zcnftuzrvzjjudsgcqmmfpnmyrenu\",\"zzmvkskzeglxaqrrvmrgcwcnvkhwz\",\"vjswvekfyqhjnsusefdtakejxbejr\",\"rwwzwbcjwiqzkwzfuxfclmsxpdyvf\",\"fdbdychmupcsxvhazvrihhnxfyumo\",\"vdtevyducpdksntgyaqtkrrkwiyuh\",\"nbvqeyoghccxfuwacxzxqkezxefxa\",\"vpgbefpqpsjmdecmixmmbsjxzwvjd\",\"jwgqmsvhnykclexepxqxqzghwfxfd\",\"olyfxbvdrspxqnxnuoygkruczddgs\",\"qgmxtdfoiaakorebqpbbpegawrqym\",\"liaivbhcgvjjnxpggrewglalthmzv\",\"choncklguqgnyrcslwztbstmycjzi\",\"fpkdpenxlewyxxgrkmwrmshhzfnor\",\"hhhcaqxbqpthuaafwgrouaxonzocl\",\"ipahojoysepzhpljpaugrghgjimtd\",\"wosrmnouwpstgbrvhtlqcnmqbygbf\",\"nwyskffpxlragrnfffawqtgyfpmzx\",\"bcvvadhnssbvneecglnqxhavhvxpk\",\"hoavxqksjreddpmibbodtbhzfehgl\",\"lazxadnftllhmjslfbrtdlahkgwle\",\"uuukupjmbbvshzxyniaowdjamlfss\",\"tpqtazbphmfoluliznftodyguessh\",\"ychqumiscfkwmqqxunqrfbgqjdwmk\",\"rkdclgzjvqrjofjjvbyfragofeoaz\",\"pphhedxdepgfgrqerpuhgmaawhnhq\",\"cacrsvutylalqrykehjuofisdookj\",\"kyldfriuvjranikluqtjjcoiqffdx\",\"bnwvrwgoskzqkgffpsyhfmxhymqin\",\"uzmlliugckuljfkljoshjhlvvlnyw\",\"abfxqbdqnexvwsvzvcsbrmkbkuzsd\",\"xotbbyvxyovzxgtcyzgqnsvcfhczo\",\"bwtpqcqhvyyssvfknfhxvtodpzipu\",\"nsfbpjqkijvudpriqrfsrdfortimg\",\"tgwyqugeuahpuvvzmgarbsyuutmbx\",\"upnwqzbsazplkyaxqorqsshhlljjl\",\"edfyahijobxyhimebctobsjudkqst\",\"ialhfmgjohzoxvdaxuywfqrgmyahh\",\"jlhcpegmtrelbosyajljvwwedtxbd\",\"tpfppjzowoltyqijfoabgzejerpat\",\"mgogyhzpmsdemugqkspsmoppwbnwa\",\"nubmpwcdqkvhwfuvcahwibniohiqy\",\"ukfadjvdnrievszilfinxbyrvknfi\",\"dgnepdiimmkcxhattwglbkicvsfsw\",\"syqxmarjkshjhxobandwyzggjibjg\",\"bnwxjytnaejivivriamhgqsskqhnq\",\"hzyjdcbyuijjnkqluaczrnrbbwaee\",\"yscnqoohcsxenypyqhfklloudgmkl\",\"habidqszhxorzfypcjcnopzwigmbz\",\"wjdqxdrlsqvsxwxpqkljeyjpulbsw\",\"tytawuuyjrwxboogfessmltwdcssd\",\"pfixglatdvuogdoizdtsgsztsfcih\",\"apkvhvsqojyixaechvuoemmyqdlfk\",\"ouaehwnnxwkdplodpuqxdbemfwahp\",\"ixuckaralemvsnbgooorayceuedto\",\"ymxdjrqikughquwtenyucjdgrmipi\",\"smrwrlkvpnhqrvpdekmtpdfuxzjwp\",\"bhjakgajafgzxpqckmhdbbnqmcszp\",\"beqsmluixgsliatukrecgoldmzfhw\",\"greuevnjssjifvlqlhkllifxrxkdb\",\"yzsqcrdchhdqprtkkjsccowrjtyjj\",\"sviyovhitxeajqahshpejaqtcdkuv\",\"qtwomymjskujjtmxiueopwacrwfuq\",\"mzyjtctvtwgyiesxhynvogxnjdjph\",\"dyfbxcaypyquodcpwxkstbthuvjqg\",\"hfmflesfabvanxlrurjtigkjotftq\",\"mxydechlraajjmoqpcyoqmrjwoium\",\"nabesvshjmdbhyhirfrkqkmfwdguj\",\"bhrfxxemhgbkgmkhjtkzyzdqmxxwq\",\"gziobrjeanlvyukwlscexbkibvdjh\",\"mcwwubbnwenybmfqglaceuyqnoadz\",\"xyzvyblypeongzrttvwqzmrccwkzi\",\"ncfalqenfcswgerbfcqsapzdtscnz\",\"dtqpezboimeuyyujfjxkdmbjpizpq\",\"wmuhplfueqnvnhukgjarxlxvwmriq\",\"qwapdkoqswyclqyvbvpedzyoyedvu\",\"uoqbztnftzgahhxwxbgkilnmzfydy\",\"zsddaahohbszhqxxgripqlwlomjbw\",\"bwadkiavdswyuxdttoqaaykctprkw\",\"eixdbntdfcaeatyyainfpkclbgaaq\",\"nmjnpttflsmjifknezrneedvgzfmn\",\"avlzyhfmeasmgrjawongccgfbgoua\",\"kklimhhjqkmuaifnodtpredhqygme\",\"xzbwenvteifxuuefnimnadwxhruvo\",\"ugmwlmidtxkvqhbuaecevwhmwkfqm\",\"rhpyjfxbjjryslfpqoiphrwfjqqha\",\"eeaipxrokncholathupdetgaktmvm\",\"ltuimrnsphqslmgvmmojawwptghon\",\"azitvyhvlspvoaeipdsjhgyfjbxhi\",\"efrelxezcgikdliyhvpocvvpkvagv\",\"znxforctwzecxyrkwufpdxadrgzcz\",\"kcqgynjcpbylayvgdqfpbqmshksyf\",\"hrljvedsywrlyccpaowjaqyfaqioe\",\"cjmfyvfybxiuqtkdlzqedjxxbvdsf\",\"zeqljuypthkmywaffmcjkickqqsuh\",\"wnfzoyvkiogisdfyjmfomcazigukq\",\"zyaaqxorqxbkenscbveqbaociwmqx\",\"ahnpivdtlcnptnxjyiaafislqavam\",\"edtqirqmjtvsfhadhafktyrmkzmvi\",\"wponuefgdtcrgxswiddygeeflpjee\",\"xozgwhtbhlkvrzismnozqpfthajaf\",\"ptnfnojnlinbfmylhdlijcvcxzjhd\",\"uxekzlgigjpsukjvsdihrjzgovnre\",\"rbohxlytsmoeleqrjvievpjipsgdk\",\"fxtzaxpcfrcovwgrcwqptoekhmgpo\",\"tvxvvgjbyxpgwpganjiaumojpyhhy\",\"vqjjhfaupylefbvbsbhdncsshmrhx\",\"urhedneauccrkyjfiptjfxmpxlssr\",\"ltvgknnlodtbhnbhjkmuhwxvzgmkh\",\"ucztsneqttsuirmjriohhgunzatyf\",\"rbzryfaeuqkfxrbldyusoeoldpbwa\",\"atlgpnkuksuesrduxkodwjzgubpsm\",\"lrdniqbzxrbpcvmzpyqklsskpwctg\",\"qvnvgzkyhistydagsgnujiviyijdn\",\"uzatydzcnktnkeyztoqvogodxxznh\",\"ocbvphmtpwhcgjbnmxgidtlqcnnwt\",\"koudovxrjkusxdazxaawmvoostlvv\",\"ptruqmjtbaapgmkfnbwnlvzlxwdpz\",\"xdxtpbpoemekvxzrrakwjxcxqsdas\",\"gdpclnsguabtgbfwdmrmbvydorfrb\",\"htwxdbarwuxykgduxjlkxppwyruih\"]");
		List<Integer> ret = new Solution().findSubstring(s, words);

		String out = integerArrayListToString(ret);

		System.out.print(out);
		
	}
}