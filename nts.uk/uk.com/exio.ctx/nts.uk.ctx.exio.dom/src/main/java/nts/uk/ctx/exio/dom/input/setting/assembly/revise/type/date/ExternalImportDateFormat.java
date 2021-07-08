package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date;

import nts.arc.time.GeneralDate;

/**
 * 受入日付書式
 */
public enum ExternalImportDateFormat {
	
	YYYY_MM_DD(0, "YYYY/MM/DD", "yyyy/MM/dd"),
	YYYYMMDD(1, "YYYYMMDD", "yyyyMMdd"),
	YY_MM_DD(2, "YY/MM/DD", "yy/MM/dd"),
	YYMMDD(3, "YYMMDD", "yyMMdd"),
	JJYY_MM_DD(4, "JJYY/MM/DD", ""),
	JJYYMMDD(5, "JJYYMMDD", ""),
	;
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	public final String format;

	private ExternalImportDateFormat(int value, String nameId, String format) {
		this.value = value;
		this.nameId = nameId;
		this.format = format;
	}
	
	/**
	 * 文字列から変換する
	 * @param target
	 * @return
	 */
	public GeneralDate fromString(String target) {
		try {
			//指定の書式に変換
			return GeneralDate.fromString(target, this.nameId);
		} catch (Exception e) {
			throw new RuntimeException("指定の日付形式に変換することができませんでした：" + this.nameId);
		}
	}
}
