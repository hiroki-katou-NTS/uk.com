package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

/**
 * 60進数表記時間の区切り文字
 */
public enum TimeBase60Delimiter {
	
	//区切り文字なし
	NO_DELIMITER(0, "Enum_TimeBase60Delimiter_NO_DELIMITER", ""), 
	//小数点で区切る
	SEPARATE_BY_DECIMAL(1, "Enum_TimeBase60Delimiter_SEPARATE_BY_DECIMAL", "."), 
	//コロンで区切る
	SEPARATE_BY_COLON(2, "Enum_TimeBase60Delimiter_SEPARATE_BY_COLON", ":");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The character */
	public final String character;
	
	private TimeBase60Delimiter(int value, String nameId, String character) {
		this.value = value;
		this.nameId = nameId;
		this.character = character;
	}
	
	/**
	 * 区切り文字変換
	 * @param target
	 * @return
	 */
	public Long convert(String target) {
		switch(this) {
		case NO_DELIMITER:
			return convertNoDelimiter(target);
		case SEPARATE_BY_DECIMAL:
		case SEPARATE_BY_COLON:
			return convertDelimiter(target);
		default:
			throw new RuntimeException("区切り文字に対応する変換処理を実装してください。");
		}
		
	}
	
	// 区切り文字なし
	private Long convertNoDelimiter(String target) {
		if (!target.matches("\\d+")) {
			// 整数でない場合
			throw new RuntimeException("整数でないので変換できません。");
		}
		
		return convert(
				target.substring(0, target.length() - 2),
				target.substring(target.length() - 2));
	}
	
	// 区切り文字あり
	private Long convertDelimiter(String target) {
		// 区切り文字で文字列を2分割
		String[] strParts = target.split(character, 2);
		
		// 分割したそれぞれが整数であることを確認
		if (!strParts[0].matches("\\d+") || !strParts[1].matches("\\d+")) {
			// 整数でない場合
			throw new RuntimeException("「整数 + 区切り文字 + 整数」の形式でないので変換できません。");
		}
		
		return convert(strParts[0], strParts[1]);
	}

	private static Long convert(String hourString, String minString) {
		Long hour = Long.parseLong(hourString);
		// 分部分の切り出し
		Long min = Long.parseLong(minString);
		
		// 分に当たる値が59以下であることを確認
		if(min >= 60) {
			throw new RuntimeException("分は59以下でなければ変換できません。");
		}
		
		// 時間を分に変換して加算
		return hour * 60 + min;
	}
}
