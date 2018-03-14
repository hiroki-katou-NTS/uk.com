package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exi.codeconvert.CodeConvertCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 文字型データ形式設定
 */
@Getter
public class ChrDataFormatSet extends DataFormatSetting {

	/**
	 * コード編集
	 */
	private NotUseAtr cdEditing;

	/**
	 * 固定値
	 */
	private NotUseAtr fixedValue;

	/**
	 * 有効桁長
	 */
	private NotUseAtr effectiveDigitLength;

	/**
	 * コード変換コード
	 */
	private Optional<CodeConvertCode> cdConvertCd;

	/**
	 * コード編集方法
	 */
	private Optional<FixedLengthEditingMethod> cdEditMethod;

	/**
	 * コード編集桁
	 */
	private Optional<DataFormatCharacterDigit> cdEditDigit;

	/**
	 * 固定値の値
	 */
	private Optional<DataSettingFixedValue> fixedVal;

	/**
	 * 有効桁数開始桁
	 */
	private Optional<AcceptedDigit> startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Optional<AcceptedDigit> endDigit;

	public ChrDataFormatSet(int itemType, int cdEditing, int fixedValue, int effectiveDigitLength, String cdConvertCd,
			Integer cdEditMethod, Integer cdEditDigit, String fixedVal, Integer startDigit, Integer endDigit) {
		super(itemType);
		this.cdEditing = EnumAdaptor.valueOf(cdEditing, NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);
		this.cdConvertCd = Optional.ofNullable(new CodeConvertCode(cdConvertCd));
		this.cdEditMethod = Optional.ofNullable(EnumAdaptor.valueOf(cdEditMethod, FixedLengthEditingMethod.class));
		this.fixedVal = Optional.ofNullable(new DataSettingFixedValue(fixedVal));
		this.cdEditDigit = Optional.ofNullable(new DataFormatCharacterDigit(cdEditDigit));
		this.startDigit = Optional.ofNullable(new AcceptedDigit(startDigit));
		this.endDigit = Optional.of(new AcceptedDigit(endDigit));
	}

}
