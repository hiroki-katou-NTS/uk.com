package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 60進数表記時分データの区切り文字
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TimeBase60Delimiter {
	
	/** 無し */
	NONE(0, "Enum_TimeBase60Delimiter_NO_DELIMITER", ""), 

	/** 小数点 */
	DECIMAL_POINT(1, "Enum_TimeBase60Delimiter_SEPARATE_BY_DECIMAL", "."), 

	/** コロン */
	COLON(2, "Enum_TimeBase60Delimiter_SEPARATE_BY_COLON", ":");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The character */
	public final String character;
	
	public static TimeBase60Delimiter valueOf(int value) {
		return EnumAdaptor.valueOf(value, TimeBase60Delimiter.class);
	}
	
	/**
	 * 区切り文字変換
	 * @param target
	 * @return
	 */
	public int toMinutes(String target) {
		switch(this) {
		case NONE:
			return convertNoDelimiter(target);
		case DECIMAL_POINT:
		case COLON:
			return convertDelimiter(target);
		default:
			throw new RuntimeException("区切り文字に対応する変換処理を実装してください。");
		}
		
	}
	
	// 区切り文字なし
	private int convertNoDelimiter(String target) {
		if (!target.matches("\\d+")) {
			// 整数でない場合
			throw new RuntimeException("整数でないので変換できません。");
		}
		
		return convert(
				target.substring(0, target.length() - 2),
				target.substring(target.length() - 2));
	}
	
	// 区切り文字あり
	private int convertDelimiter(String target) {
		// 区切り文字で文字列を2分割
		String[] strParts = target.split(character, 2);
		
		// 分割したそれぞれが整数であることを確認
		if (!strParts[0].matches("\\d+") || !strParts[1].matches("\\d+")) {
			// 整数でない場合
			throw new RuntimeException("「整数 + 区切り文字 + 整数」の形式でないので変換できません。");
		}
		
		return convert(strParts[0], strParts[1]);
	}

	private static int convert(String hourString, String minString) {
		int hour = Integer.parseInt(hourString);
		// 分部分の切り出し
		int min = Integer.parseInt(minString);
		
		// 分に当たる値が59以下であることを確認
		if(min >= 60) {
			throw new RuntimeException("分は59以下でなければ変換できません。");
		}
		
		// 時間を分に変換して加算
		return hour * 60 + min;
	}
}
