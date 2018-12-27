package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 勤怠項目名とIDの紐づけ
 * @author keisuke_hoshina
 *
 */
public class AttendanceItemDictionaryForCalc {

	private Map<String,Integer> dictionary = new HashMap<>();

	/**
	 * Constructor 
	 */
	private AttendanceItemDictionaryForCalc() {
		super();
		createDictionary();
	}
	
	private void createDictionary() {
		//残業系の追加
		putOverTime();
		//休出系の追加
		putHolidayTime();
		//深夜系の追加
		putMidNightTime();
		//遅刻・早退系の追加
		putLateEarly();
	}
	
	//遅刻・早退系の追加
	private void putLateEarly() {
		//遅刻時間
		this.dictionary.put("遅刻時間1", 592);
		this.dictionary.put("遅刻時間2", 598);
		//早退時間
		this.dictionary.put("早退時間1", 604);
		this.dictionary.put("早退時間2", 610);
	}

	//深夜系の追加
	private void putMidNightTime() {
		//所定内深夜
		this.dictionary.put("内深夜時間", 561);
		
		//外深夜
		this.dictionary.put("外深夜時間", 563);
		
		//就外残業深夜
		this.dictionary.put("就外残業深夜時間", 566);
		
		//休出法内深夜
		this.dictionary.put("法内休出外深夜", 568);
		//休出法外深夜
		this.dictionary.put("法外休出外深夜", 570);
		//休出祝日深夜
		this.dictionary.put("就外法外祝日深夜", 573);
	}

	//残業系を辞書へ追加
	private void putOverTime() {
		//残業時間
		this.dictionary.put("残業時間1", 216);
		this.dictionary.put("残業時間2", 221);
		this.dictionary.put("残業時間3", 226);
		this.dictionary.put("残業時間4", 231);
		this.dictionary.put("残業時間5", 236);
		this.dictionary.put("残業時間6", 241);
		this.dictionary.put("残業時間7", 246);
		this.dictionary.put("残業時間8", 251);
		this.dictionary.put("残業時間9", 256);
		this.dictionary.put("残業時間10", 261);
		
		//残業時間
		this.dictionary.put("振替残業時間1", 217);
		this.dictionary.put("振替残業時間2", 222);
		this.dictionary.put("振替残業時間3", 227);
		this.dictionary.put("振替残業時間4", 232);
		this.dictionary.put("振替残業時間5", 237);
		this.dictionary.put("振替残業時間6", 242);
		this.dictionary.put("振替残業時間7", 247);
		this.dictionary.put("振替残業時間8", 252);
		this.dictionary.put("振替残業時間9", 257);
		this.dictionary.put("振替残業時間10", 262);
		
		//フレックス時間
		this.dictionary.put("フレックス時間", 556);
	}

	//休出系の追加
	private void putHolidayTime() {
		//休出時間
		this.dictionary.put("休出時間1", 266);
		this.dictionary.put("休出時間2", 271);
		this.dictionary.put("休出時間3", 276);
		this.dictionary.put("休出時間4", 281);
		this.dictionary.put("休出時間5", 286);
		this.dictionary.put("休出時間6", 291);
		this.dictionary.put("休出時間7", 296);
		this.dictionary.put("休出時間8", 301);
		this.dictionary.put("休出時間9", 306);
		this.dictionary.put("休出時間10", 311);
		
		//休出の振替時間
		this.dictionary.put("振替時間1", 267);
		this.dictionary.put("振替時間2", 272);
		this.dictionary.put("振替時間3", 277);
		this.dictionary.put("振替時間4", 282);
		this.dictionary.put("振替時間5", 287);
		this.dictionary.put("振替時間6", 292);
		this.dictionary.put("振替時間7", 297);
		this.dictionary.put("振替時間8", 302);
		this.dictionary.put("振替時間9", 307);
		this.dictionary.put("振替時間10", 312);
		
	}
	
	/**
	 * 辞書の作成
	 * @return
	 */
	public static AttendanceItemDictionaryForCalc setDictionaryValue() {
		return new AttendanceItemDictionaryForCalc();
	}
	
	/**
	 * 勤怠項目IDの検索
	 * @param itemName　勤怠項目名
	 * @return 勤怠項目ID
	 */
	public Optional<Integer> findId(String itemName) {
		return dictionary.containsKey(itemName)?Optional.of(dictionary.get(itemName)):Optional.empty();
	}
	
}
