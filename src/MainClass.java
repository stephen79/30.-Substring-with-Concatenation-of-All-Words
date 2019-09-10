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

		int[] counts = new int[s.length()];
		// long time = 0;
		List<Integer> list = new ArrayList<Integer>();
		if (s == null || s == "" || words.length == 0) {
			return list;
		}
		int word_len = words[0].length();
		for (int i = 0 ; i <= s.length() - (word_len * words.length); i ++) {
			List<String> wordList = new ArrayList<String>(Arrays. asList(words));
			pn("i:" + i);
			int init_j = 0;
			pn("counts[i]:" + counts[i]);

			for (int j = init_j; j < words.length; j++) {
				
				String sub = s.substring(i + j*word_len, i + (j + 1)*word_len);
				pn("sub:" + sub);
				if (wordList.contains(sub)) {
					pn("has sub:" + sub);
					wordList.remove(sub);
				}
				else {
					break;
				}
			}
			pn("wordList:" + wordList.size());
			if (wordList.size() == init_j) {
				list.add(i);
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
//		String s = "barfoothefoobarman";
////		String[] words = stringToStringArray("		[\"foo\",\"bar\"]      ");
//		 String s = "wordgoodgoodgoodbestword";
//		 String[] words = stringToStringArray(" [\"word\",\"good\",\"best\",\"good\"]  ");
		// String s = "aaaaaaaa";
		// String[] words = stringToStringArray(" [\"aa\",\"aa\",\"aa\"] ");
		// String s = "ejwwmybnorgshugzmoxopwuvshlcwasclobxmckcvtxfndeztdqiakfusswqsovdfwatanwxgtctyjvsmlcoxijrahivwfybbbudosawnfpmomgczirzscqvlaqhfqkithlhbodptvdhjljltckogcjsdbbktotnxgwyuapnxuwgfirbmdrvgapldsvwgqjfxggtixjhshnzphcemtzsvodygbxpriwqockyavfscvtsewyqpxlnnqnvrkmjtjbjllilinflkbfoxdhocsbpirmcbznuioevcojkdqvoraeqdlhffkwqbjsdkfxstdpxryixrdligpzldgtiqryuasxmxwgtcwsvwasngdwovxzafuixmjrobqbbnhwpdokcpfpxinlfmkfrfqrtzkhabidqszhxorzfypcjcnopzwigmbznmjnpttflsmjifknezrneedvgzfmnhoavxqksjreddpmibbodtbhzfehgluuukupjmbbvshzxyniaowdjamlfssndojyyephstlonsplrettspwepipwcjmfyvfybxiuqtkdlzqedjxxbvdsfurhedneauccrkyjfiptjfxmpxlssrkyldfriuvjranikluqtjjcoiqffdxaukagphzycvjtvwdhhxzagkevvuccxccuoccdkbboymjtimdrmerspxpktsmrwrlkvpnhqrvpdekmtpdfuxzjwpvqjjhfaupylefbvbsbhdncsshmrhxoyuejenqgjheulkxjnqkwvzznriclrbzryfaeuqkfxrbldyusoeoldpbwadhrgijeplijcvqbormrqglgmzsprtmryvkeevlthvflsvognbxfjilwkdndyzwwxgdbeqwlldyezmkopktzugxgkklimhhjqkmuaifnodtpredhqygmedtqpezboimeuyyujfjxkdmbjpizpqltvgknnlodtbhnbhjkmuhwxvzgmkhbcvvadhnssbvneecglnqxhavhvxpkjxlluilzpysjcnwguyofnhfvhaceztoiscumkhociglkvispihvyoatxcxbeqsmluixgsliatukrecgoldmzfhwkgaqzsckonjuhxdhqztjfxstjvikdrhpyjfxbjjryslfpqoiphrwfjqqhaamrjbrsiovrxmqsyxhqmritjeauwqbwtpqcqhvyyssvfknfhxvtodpzipueixdbntdfcaeatyyainfpkclbgaaqrwwzwbcjwiqzkwzfuxfclmsxpdyvfbnwxjytnaejivivriamhgqsskqhnqeurttrfrmstrbeokzhuzvbfmwywohmgogyhzpmsdemugqkspsmoppwbnwabdmiruibwznqcuczculujfiavzwynsyqxmarjkshjhxobandwyzggjibjgzyaaqxorqxbkenscbveqbaociwmqxxyzvyblypeongzrttvwqzmrccwkzidyfbxcaypyquodcpwxkstbthuvjqgialhfmgjohzoxvdaxuywfqrgmyahhtpqtazbphmfoluliznftodyguesshcacrsvutylalqrykehjuofisdookjhrljvedsywrlyccpaowjaqyfaqioesxnlkwgpbznzszyudpwrlgrdgwdyhucztsneqttsuirmjriohhgunzatyfrfzvgvptbgpwajgtysligupoqeoqxoyqtzozufvvlktnvahvsseymtpeyfvxttqosgpplkmxwgmsgtpantazppgnubmpwcdqkvhwfuvcahwibniohiqyywnuzzmxeppokxksrfwrpuzqhjgqryorwboxdauhrkxehiwaputeouwxdfoudcoagcxjcuqvenznxxnprgvhasffxtzaxpcfrcovwgrcwqptoekhmgpoywtxruxokcubekzcrqengviwbtgnzvdzrwwkqvacxwgdhffyvjldgvchoiwnfzoyvkiogisdfyjmfomcazigukqlumyzmnzjzhzfpslwsukykwckvktswjdqxdrlsqvsxwxpqkljeyjpulbswwmuhplfueqnvnhukgjarxlxvwmriqjgmxawmndhsvwnjdjvjtxcsjapfogpesxtpypenunfpjuyoevzztctecilqqbxkaqcyhiobvtqgqruumvvhxolbyzsqcrdchhdqprtkkjsccowrjtyjjmkhleanvfpemuublnnyzfabtxsestncfalqenfcswgerbfcqsapzdtscnzugmwlmidtxkvqhbuaecevwhmwkfqmvpgbefpqpsjmdecmixmmbsjxzwvjdmxydechlraajjmoqpcyoqmrjwoiumuzatydzcnktnkeyztoqvogodxxznhvzduzxudwwqhpftwdspuimioanlzobhjakgajafgzxpqckmhdbbnqmcszpuoqbztnftzgahhxwxbgkilnmzfydyxusnnvngksbjabqjaohdvrniezhmxmkxhemwbbclwdxwgngicplzgajmaryzfkyoqlkrmmfirchzrphveuwmvgaxzbwenvteifxuuefnimnadwxhruvoavlzyhfmeasmgrjawongccgfbgoualiaivbhcgvjjnxpggrewglalthmzvgziobrjeanlvyukwlscexbkibvdjhdgnepdiimmkcxhattwglbkicvsfswocbvphmtpwhcgjbnmxgidtlqcnnwtfujhvgzdussqbwynylzvtjapvqtidpdjkpshvrmqlhindhabubyokzdfrwqvnvgzkyhistydagsgnujiviyijdnabfxqbdqnexvwsvzvcsbrmkbkuzsdehghndyqjodnnblfwmaygdstotfkvxozgwhtbhlkvrzismnozqpfthajafuxekzlgigjpsukjvsdihrjzgovnreqwapdkoqswyclqyvbvpedzyoyedvuuamscbxnqnfmmjyehvidnoimmxmtcinwkbqmcobubjjpshucechrqrffqsyscnqoohcsxenypyqhfklloudgmklcejvgynwouzhtfwuuukdbwpmkjrqxeeaipxrokncholathupdetgaktmvmftqjvzyssocftjwemroghrncynmtchhhcaqxbqpthuaafwgrouaxonzocljeuslzsdwvuoodipdpnlhdihaywzmymxdjrqikughquwtenyucjdgrmipiidiwclhuepgyynoslhzahtdqwliktzsddaahohbszhqxxgripqlwlomjbwtuynydoakejmwkvojuwbfltqjfgxqhwkduzbxpdhtpvrzrfjndmsqfizmqxdxtpbpoemekvxzrrakwjxcxqsdasptruqmjtbaapgmkfnbwnlvzlxwdpzfjryanrmzmpzoefapmnsjdgecrdywsabctaegttffigupnwgakylngrrxurtotxqmzxvsqazajvrwsxyeyjteakeudzjxwbjvagnsjntskmocmpgkybqbnwvrwgoskzqkgffpsyhfmxhymqinrbohxlytsmoeleqrjvievpjipsgdkrqeuglrsjnmvdsihicsgkybcjltcswolpsfxdypmlbjotuxewskisnmczfgreuevnjssjifvlqlhkllifxrxkdbjlhcpegmtrelbosyajljvwwedtxbdccpnmreqaqjrxwulpunagwxesbilalrdniqbzxrbpcvmzpyqklsskpwctgqtrjwhrpisocwderqfiqxsdpkphjsapkvhvsqojyixaechvuoemmyqdlfkuzmlliugckuljfkljoshjhlvvlnywvjswvekfyqhjnsusefdtakejxbejrchoncklguqgnyrcslwztbstmycjziuskegagtlonducdogwbevugppsptdqbajmepmmizaycwcgmjeopbivsyphtvxvvgjbyxpgwpganjiaumojpyhhywosrmnouwpstgbrvhtlqcnmqbygbfnabesvshjmdbhyhirfrkqkmfwdgujhzyjdcbyuijjnkqluaczrnrbbwaeeupnwqzbsazplkyaxqorqsshhlljjlpphhedxdepgfgrqerpuhgmaawhnhqwsgnznrfmxjbdrkwjopylxezxgvetcvrwdewsxdeumhzfrvoilmvksuhyqltuimrnsphqslmgvmmojawwptghonigbdclqtbikiacwpjrbxhmzejozpypfixglatdvuogdoizdtsgsztsfcihtgwyqugeuahpuvvzmgarbsyuutmbxuisdfrvbxzxzhmuektssuktoknkfbmcwwubbnwenybmfqglaceuyqnoadzfenjcjfdlvcpiatuhjdujhaffqsvqvuxchgerokejovrqonxxstibunikiedfyahijobxyhimebctobsjudkqstbcxgixgrhpfiofpwruzvpqyjzvollheoldutddnksutjakhtghpxxnjykxjwgqmsvhnykclexepxqxqzghwfxfdhfmflesfabvanxlrurjtigkjotftqnwyskffpxlragrnfffawqtgyfpmzxfpkdpenxlewyxxgrkmwrmshhzfnorolyfxbvdrspxqnxnuoygkruczddgssygfymdcjgvdxutlrhffhnpyjuxmxefrelxezcgikdliyhvpocvvpkvagvmezrxffujeysplvavtjqjxsgujqsjznxforctwzecxyrkwufpdxadrgzczrnyelfschnagucguuqqqwitviynrypsrdswqxqsegulcwrwsjnihxedfcqychqumiscfkwmqqxunqrfbgqjdwmkyelbldxympctbzfupeocwhkypchuyvhybsbmvymjppfrqmlfrbkpjwpyyytytawuuyjrwxboogfessmltwdcssdqtwomymjskujjtmxiueopwacrwfuqazitvyhvlspvoaeipdsjhgyfjbxhityisidnhlksfznubucqxwaheamndjxmcxwufajmnveuwuoyosqnoqwvtjkwuhkzghvmjhawcfszbhzrbpgsidnbmxxihihnrfbamcyojqpkzodbejtmmipahojoysepzhpljpaugrghgjimtdahnpivdtlcnptnxjyiaafislqavamqgmxtdfoiaakorebqpbbpegawrqymqkewycsdjglkiwaacdqterkixkgraedtqirqmjtvsfhadhafktyrmkzmvidxmisfskvevpcnujqxrqedleuyowkjgphsxzzqlvujkwwgiodbfjesnbsbzcnftuzrvzjjudsgcqmmfpnmyrenuxotbbyvxyovzxgtcyzgqnsvcfhczoptnfnojnlinbfmylhdlijcvcxzjhdixuckaralemvsnbgooorayceuedtomzyjtctvtwgyiesxhynvogxnjdjphcftbefxgasawzagfugmuthjahylkhatlgpnkuksuesrduxkodwjzgubpsmzzmvkskzeglxaqrrvmrgcwcnvkhwzbibaxwnriowoavosminabvfxastkcrkdclgzjvqrjofjjvbyfragofeoazzeqljuypthkmywaffmcjkickqqsuhsviyovhitxeajqahshpejaqtcdkuvgdpclnsguabtgbfwdmrmbvydorfrbcokfdmtsgboidkpgpnmdeyhawkqqshtwxdbarwuxykgduxjlkxppwyruihkcqgynjcpbylayvgdqfpbqmshksyfbhrfxxemhgbkgmkhjtkzyzdqmxxwqvdtevyducpdksntgyaqtkrrkwiyuhukfadjvdnrievszilfinxbyrvknfihmetreydbcstkwoexwsfhfekfvfplmxszcosgovisnbemrjlndqwkvhqsofdbdychmupcsxvhazvrihhnxfyumonbvqeyoghccxfuwacxzxqkezxefxarnnujgyjugrzjoefmghjfhcrnbrtgouaehwnnxwkdplodpuqxdbemfwahptpfppjzowoltyqijfoabgzejerpatwponuefgdtcrgxswiddygeeflpjeelzccnsztxfyqhqyhkuppapvgvdtkmxraytcolbhkiiasaazkvqzvfxbaaxkoudovxrjkusxdazxaawmvoostlvvnsfbpjqkijvudpriqrfsrdfortimgdhtypunakzituezjyhbrpuksbamuiycngvlvpyvczfxvlwhjgicvempfobbwadkiavdswyuxdttoqaaykctprkwfmyeodowglzyjzuhencufcwdobydslazxadnftllhmjslfbrtdlahkgwlebdpdeofidldoymakfnpgekmsltcrrnxvspywfggjrmxryybdltmsfykstmlnzjitaipfoyohkmzimcozxardydxtpjgquoluzbznzqvlewtqyhryjldjoadgjlyfckzbnbootlzxhupieggntjxilcqxnocpyesnhjbauaxcvmkzusmodlyonoldequfunsbwudquaurogsiyhydswsimflrvfwruouskxjfzfynmrymyyqsvkajpnanvyepnzixyteyafnmwnbwmtojdpsucthxtopgpxgnsmnsrdhpskledapiricvdmtwaifrhnebzuttzckroywranbrvgmashxurelyrrbslxnmzyeowchwpjplrdnjlkfcoqdhheavbnhdlltjpahflwscafnnsspikuqszqpcdyfrkaabdigogatgiitadlinfyhgowjuvqlhrniuvrketfmboibttkgakohbmsvhigqztbvrsgxlnjndrqwmcdnntwofojpyrhamivfcdcotodwhvtuyyjlthbaxmrvfzxrhvzkydartfqbalxyjilepmemawjfxhzecyqcdswxxmaaxxyifmouauibstgpcfwgfmjlfhketkeshfcorqirmssfnbuqiqwqfhbmol";
		// String[] words = stringToStringArray("[\"toiscumkhociglkvispihvyoatxcx\",\"ndojyyephstlonsplrettspwepipw\",\"yzfkyoqlkrmmfirchzrphveuwmvga\",\"mxxihihnrfbamcyojqpkzodbejtmm\",\"fenjcjfdlvcpiatuhjdujhaffqsvq\",\"ehghndyqjodnnblfwmaygdstotfkv\",\"heoldutddnksutjakhtghpxxnjykx\",\"cvrwdewsxdeumhzfrvoilmvksuhyq\",\"ftqjvzyssocftjwemroghrncynmtc\",\"idiwclhuepgyynoslhzahtdqwlikt\",\"eurttrfrmstrbeokzhuzvbfmwywoh\",\"jxlluilzpysjcnwguyofnhfvhacez\",\"uskegagtlonducdogwbevugppsptd\",\"xmcxwufajmnveuwuoyosqnoqwvtjk\",\"wolpsfxdypmlbjotuxewskisnmczf\",\"fjryanrmzmpzoefapmnsjdgecrdyw\",\"jgmxawmndhsvwnjdjvjtxcsjapfog\",\"wuhkzghvmjhawcfszbhzrbpgsidnb\",\"yelbldxympctbzfupeocwhkypchuy\",\"vzduzxudwwqhpftwdspuimioanlzo\",\"bdpdeofidldoymakfnpgekmsltcrr\",\"fmyeodowglzyjzuhencufcwdobyds\",\"dhtypunakzituezjyhbrpuksbamui\",\"bdmiruibwznqcuczculujfiavzwyn\",\"eudzjxwbjvagnsjntskmocmpgkybq\",\"tuynydoakejmwkvojuwbfltqjfgxq\",\"psrdswqxqsegulcwrwsjnihxedfcq\",\"cokfdmtsgboidkpgpnmdeyhawkqqs\",\"fujhvgzdussqbwynylzvtjapvqtid\",\"rqeuglrsjnmvdsihicsgkybcjltcs\",\"vhybsbmvymjppfrqmlfrbkpjwpyyy\",\"aukagphzycvjtvwdhhxzagkevvucc\",\"hwkduzbxpdhtpvrzrfjndmsqfizmq\",\"ywnuzzmxeppokxksrfwrpuzqhjgqr\",\"qbajmepmmizaycwcgmjeopbivsyph\",\"uamscbxnqnfmmjyehvidnoimmxmtc\",\"nxvspywfggjrmxryybdltmsfykstm\",\"amrjbrsiovrxmqsyxhqmritjeauwq\",\"yorwboxdauhrkxehiwaputeouwxdf\",\"qkewycsdjglkiwaacdqterkixkgra\",\"ycngvlvpyvczfxvlwhjgicvempfob\",\"jgphsxzzqlvujkwwgiodbfjesnbsb\",\"mkxhemwbbclwdxwgngicplzgajmar\",\"mryvkeevlthvflsvognbxfjilwkdn\",\"mezrxffujeysplvavtjqjxsgujqsj\",\"rtotxqmzxvsqazajvrwsxyeyjteak\",\"sabctaegttffigupnwgakylngrrxu\",\"xccuoccdkbboymjtimdrmerspxpkt\",\"xusnnvngksbjabqjaohdvrniezhmx\",\"oyuejenqgjheulkxjnqkwvzznricl\",\"mxszcosgovisnbemrjlndqwkvhqso\",\"wsgnznrfmxjbdrkwjopylxezxgvet\",\"dxmisfskvevpcnujqxrqedleuyowk\",\"dhrgijeplijcvqbormrqglgmzsprt\",\"vuxchgerokejovrqonxxstibuniki\",\"lumyzmnzjzhzfpslwsukykwckvkts\",\"inwkbqmcobubjjpshucechrqrffqs\",\"ywtxruxokcubekzcrqengviwbtgnz\",\"ccpnmreqaqjrxwulpunagwxesbila\",\"pesxtpypenunfpjuyoevzztctecil\",\"sygfymdcjgvdxutlrhffhnpyjuxmx\",\"uisdfrvbxzxzhmuektssuktoknkfb\",\"cejvgynwouzhtfwuuukdbwpmkjrqx\",\"oudcoagcxjcuqvenznxxnprgvhasf\",\"sxnlkwgpbznzszyudpwrlgrdgwdyh\",\"qqbxkaqcyhiobvtqgqruumvvhxolb\",\"mkhleanvfpemuublnnyzfabtxsest\",\"bibaxwnriowoavosminabvfxastkc\",\"bcxgixgrhpfiofpwruzvpqyjzvoll\",\"lzccnsztxfyqhqyhkuppapvgvdtkm\",\"pdjkpshvrmqlhindhabubyokzdfrw\",\"qbbnhwpdokcpfpxinlfmkfrfqrtzk\",\"rnyelfschnagucguuqqqwitviynry\",\"qtrjwhrpisocwderqfiqxsdpkphjs\",\"vxttqosgpplkmxwgmsgtpantazppg\",\"tyisidnhlksfznubucqxwaheamndj\",\"kgaqzsckonjuhxdhqztjfxstjvikd\",\"jeuslzsdwvuoodipdpnlhdihaywzm\",\"vdzrwwkqvacxwgdhffyvjldgvchoi\",\"cftbefxgasawzagfugmuthjahylkh\",\"xraytcolbhkiiasaazkvqzvfxbaax\",\"oyqtzozufvvlktnvahvsseymtpeyf\",\"rnnujgyjugrzjoefmghjfhcrnbrtg\",\"rfzvgvptbgpwajgtysligupoqeoqx\",\"igbdclqtbikiacwpjrbxhmzejozpy\",\"dyzwwxgdbeqwlldyezmkopktzugxg\",\"hmetreydbcstkwoexwsfhfekfvfpl\",\"zcnftuzrvzjjudsgcqmmfpnmyrenu\",\"zzmvkskzeglxaqrrvmrgcwcnvkhwz\",\"vjswvekfyqhjnsusefdtakejxbejr\",\"rwwzwbcjwiqzkwzfuxfclmsxpdyvf\",\"fdbdychmupcsxvhazvrihhnxfyumo\",\"vdtevyducpdksntgyaqtkrrkwiyuh\",\"nbvqeyoghccxfuwacxzxqkezxefxa\",\"vpgbefpqpsjmdecmixmmbsjxzwvjd\",\"jwgqmsvhnykclexepxqxqzghwfxfd\",\"olyfxbvdrspxqnxnuoygkruczddgs\",\"qgmxtdfoiaakorebqpbbpegawrqym\",\"liaivbhcgvjjnxpggrewglalthmzv\",\"choncklguqgnyrcslwztbstmycjzi\",\"fpkdpenxlewyxxgrkmwrmshhzfnor\",\"hhhcaqxbqpthuaafwgrouaxonzocl\",\"ipahojoysepzhpljpaugrghgjimtd\",\"wosrmnouwpstgbrvhtlqcnmqbygbf\",\"nwyskffpxlragrnfffawqtgyfpmzx\",\"bcvvadhnssbvneecglnqxhavhvxpk\",\"hoavxqksjreddpmibbodtbhzfehgl\",\"lazxadnftllhmjslfbrtdlahkgwle\",\"uuukupjmbbvshzxyniaowdjamlfss\",\"tpqtazbphmfoluliznftodyguessh\",\"ychqumiscfkwmqqxunqrfbgqjdwmk\",\"rkdclgzjvqrjofjjvbyfragofeoaz\",\"pphhedxdepgfgrqerpuhgmaawhnhq\",\"cacrsvutylalqrykehjuofisdookj\",\"kyldfriuvjranikluqtjjcoiqffdx\",\"bnwvrwgoskzqkgffpsyhfmxhymqin\",\"uzmlliugckuljfkljoshjhlvvlnyw\",\"abfxqbdqnexvwsvzvcsbrmkbkuzsd\",\"xotbbyvxyovzxgtcyzgqnsvcfhczo\",\"bwtpqcqhvyyssvfknfhxvtodpzipu\",\"nsfbpjqkijvudpriqrfsrdfortimg\",\"tgwyqugeuahpuvvzmgarbsyuutmbx\",\"upnwqzbsazplkyaxqorqsshhlljjl\",\"edfyahijobxyhimebctobsjudkqst\",\"ialhfmgjohzoxvdaxuywfqrgmyahh\",\"jlhcpegmtrelbosyajljvwwedtxbd\",\"tpfppjzowoltyqijfoabgzejerpat\",\"mgogyhzpmsdemugqkspsmoppwbnwa\",\"nubmpwcdqkvhwfuvcahwibniohiqy\",\"ukfadjvdnrievszilfinxbyrvknfi\",\"dgnepdiimmkcxhattwglbkicvsfsw\",\"syqxmarjkshjhxobandwyzggjibjg\",\"bnwxjytnaejivivriamhgqsskqhnq\",\"hzyjdcbyuijjnkqluaczrnrbbwaee\",\"yscnqoohcsxenypyqhfklloudgmkl\",\"habidqszhxorzfypcjcnopzwigmbz\",\"wjdqxdrlsqvsxwxpqkljeyjpulbsw\",\"tytawuuyjrwxboogfessmltwdcssd\",\"pfixglatdvuogdoizdtsgsztsfcih\",\"apkvhvsqojyixaechvuoemmyqdlfk\",\"ouaehwnnxwkdplodpuqxdbemfwahp\",\"ixuckaralemvsnbgooorayceuedto\",\"ymxdjrqikughquwtenyucjdgrmipi\",\"smrwrlkvpnhqrvpdekmtpdfuxzjwp\",\"bhjakgajafgzxpqckmhdbbnqmcszp\",\"beqsmluixgsliatukrecgoldmzfhw\",\"greuevnjssjifvlqlhkllifxrxkdb\",\"yzsqcrdchhdqprtkkjsccowrjtyjj\",\"sviyovhitxeajqahshpejaqtcdkuv\",\"qtwomymjskujjtmxiueopwacrwfuq\",\"mzyjtctvtwgyiesxhynvogxnjdjph\",\"dyfbxcaypyquodcpwxkstbthuvjqg\",\"hfmflesfabvanxlrurjtigkjotftq\",\"mxydechlraajjmoqpcyoqmrjwoium\",\"nabesvshjmdbhyhirfrkqkmfwdguj\",\"bhrfxxemhgbkgmkhjtkzyzdqmxxwq\",\"gziobrjeanlvyukwlscexbkibvdjh\",\"mcwwubbnwenybmfqglaceuyqnoadz\",\"xyzvyblypeongzrttvwqzmrccwkzi\",\"ncfalqenfcswgerbfcqsapzdtscnz\",\"dtqpezboimeuyyujfjxkdmbjpizpq\",\"wmuhplfueqnvnhukgjarxlxvwmriq\",\"qwapdkoqswyclqyvbvpedzyoyedvu\",\"uoqbztnftzgahhxwxbgkilnmzfydy\",\"zsddaahohbszhqxxgripqlwlomjbw\",\"bwadkiavdswyuxdttoqaaykctprkw\",\"eixdbntdfcaeatyyainfpkclbgaaq\",\"nmjnpttflsmjifknezrneedvgzfmn\",\"avlzyhfmeasmgrjawongccgfbgoua\",\"kklimhhjqkmuaifnodtpredhqygme\",\"xzbwenvteifxuuefnimnadwxhruvo\",\"ugmwlmidtxkvqhbuaecevwhmwkfqm\",\"rhpyjfxbjjryslfpqoiphrwfjqqha\",\"eeaipxrokncholathupdetgaktmvm\",\"ltuimrnsphqslmgvmmojawwptghon\",\"azitvyhvlspvoaeipdsjhgyfjbxhi\",\"efrelxezcgikdliyhvpocvvpkvagv\",\"znxforctwzecxyrkwufpdxadrgzcz\",\"kcqgynjcpbylayvgdqfpbqmshksyf\",\"hrljvedsywrlyccpaowjaqyfaqioe\",\"cjmfyvfybxiuqtkdlzqedjxxbvdsf\",\"zeqljuypthkmywaffmcjkickqqsuh\",\"wnfzoyvkiogisdfyjmfomcazigukq\",\"zyaaqxorqxbkenscbveqbaociwmqx\",\"ahnpivdtlcnptnxjyiaafislqavam\",\"edtqirqmjtvsfhadhafktyrmkzmvi\",\"wponuefgdtcrgxswiddygeeflpjee\",\"xozgwhtbhlkvrzismnozqpfthajaf\",\"ptnfnojnlinbfmylhdlijcvcxzjhd\",\"uxekzlgigjpsukjvsdihrjzgovnre\",\"rbohxlytsmoeleqrjvievpjipsgdk\",\"fxtzaxpcfrcovwgrcwqptoekhmgpo\",\"tvxvvgjbyxpgwpganjiaumojpyhhy\",\"vqjjhfaupylefbvbsbhdncsshmrhx\",\"urhedneauccrkyjfiptjfxmpxlssr\",\"ltvgknnlodtbhnbhjkmuhwxvzgmkh\",\"ucztsneqttsuirmjriohhgunzatyf\",\"rbzryfaeuqkfxrbldyusoeoldpbwa\",\"atlgpnkuksuesrduxkodwjzgubpsm\",\"lrdniqbzxrbpcvmzpyqklsskpwctg\",\"qvnvgzkyhistydagsgnujiviyijdn\",\"uzatydzcnktnkeyztoqvogodxxznh\",\"ocbvphmtpwhcgjbnmxgidtlqcnnwt\",\"koudovxrjkusxdazxaawmvoostlvv\",\"ptruqmjtbaapgmkfnbwnlvzlxwdpz\",\"xdxtpbpoemekvxzrrakwjxcxqsdas\",\"gdpclnsguabtgbfwdmrmbvydorfrb\",\"htwxdbarwuxykgduxjlkxppwyruih\"]");
		String s = "barfoofoobarthefoobarman";
				
				String[] words = stringToStringArray(" [\"bar\",\"foo\",\"the\"] ");
		List<Integer> ret = new Solution().findSubstring(s, words);

		String out = integerArrayListToString(ret);

		System.out.print(out);
		
	}
}