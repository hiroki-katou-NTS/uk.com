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
		if (cdConvertCd == null)
			this.cdConvertCd = Optional.empty();
		else
			this.cdConvertCd = Optional.of(new CodeConvertCode(cdConvertCd));
		if (cdEditMethod == null)
			this.cdEditMethod = Optional.empty();
		else
			this.cdEditMethod = Optional.of(EnumAdaptor.valueOf(cdEditMethod, FixedLengthEditingMethod.class));
		if (fixedVal == null)
			this.fixedVal = Optional.empty();
		else
			this.fixedVal = Optional.of(new DataSettingFixedValue(fixedVal));
		if (cdEditDigit == null)
			this.cdEditDigit = Optional.empty();
		else
			this.cdEditDigit = Optional.of(new DataFormatCharacterDigit(cdEditDigit));
		if (startDigit == null)
			this.startDigit = Optional.empty();
		else
			this.startDigit = Optional.of(new AcceptedDigit(startDigit));
		if (endDigit == null)
			this.endDigit = Optional.empty();
		else
			this.endDigit = Optional.of(new AcceptedDigit(endDigit));
	}

}
