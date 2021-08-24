package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date;

import java.time.format.DateTimeParseException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;

/**
 * 受入日付書式
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExternalImportDateFormat {
	
	YYYY_MM_DD(0, "Enum_ExternalImportDateFormat_YYYY_MM_DD", "yyyy/MM/dd"),
	YYYYMMDD(1, "Enum_ExternalImportDateFormat_YYYYMMDD", "yyyyMMdd"),
	YY_MM_DD(2, "Enum_ExternalImportDateFormat_YY_MM_DD", "yy/MM/dd"),
	YYMMDD(3, "Enum_ExternalImportDateFormat_YYMMDD", "yyMMdd"),
	;
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	public final String format;

	public static ExternalImportDateFormat valueOf(int value) {
		return EnumAdaptor.valueOf(value, ExternalImportDateFormat.class);
	}
	
	/**
	 * 文字列から変換する
	 * @param target
	 * @return
	 */
	public GeneralDate fromString(String target) {
		try {
			//指定の書式に変換
			return GeneralDate.fromString(target, this.format);
		} catch (DateTimeParseException e) {
			String formatName = I18NText.getText(this.nameId);
			throw new BusinessException(new RawErrorMessage("日付データの形式が不正です（設定された形式：" + formatName + "）"));
		}
	}
}
