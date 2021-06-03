package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.cdconvert.ConvertCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 文字型データ形式設定
 */
@Getter
public class ChacDataFmSet extends DataFormatSetting {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード編集
	 */
	private NotUseAtr cdEditting;

	/**
	 * コード編集方法
	 */
	private FixedLengthEditingMethod cdEdittingMethod;

	/**
	 * コード編集桁
	 */
	private Optional<DataFormatCharacterDigit> cdEditDigit;

	/**
	 * コード変換コード
	 */
	private Optional<ConvertCode> convertCode;

	/**
	 * スペース編集
	 */
	private EditSpace spaceEditting;

	/**
	 * 有効桁数
	 */
	private NotUseAtr effectDigitLength;

	/**
	 * 有効桁数開始桁
	 */
	private Optional<DataFormatCharacterDigit> startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Optional<DataFormatCharacterDigit> endDigit;

	public ChacDataFmSet(int itemType, String cid, int nullValueReplace, String valueOfNullValueReplace, int cdEditting,
			int fixedValue, int cdEdittingMethod, Integer cdEditDigit, String convertCode, int spaceEditting,
			int effectDigitLength, Integer startDigit, Integer endDigit, String valueOfFixedValue) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueReplace, valueOfNullValueReplace);
		this.cid = cid;
		this.cdEditting = EnumAdaptor.valueOf(cdEditting, NotUseAtr.class);
		this.cdEdittingMethod = EnumAdaptor.valueOf(cdEdittingMethod, FixedLengthEditingMethod.class);
		this.cdEditDigit = Optional.ofNullable(cdEditDigit != null ? new DataFormatCharacterDigit(cdEditDigit) : null);
		this.convertCode = Optional.ofNullable(convertCode != null ? new ConvertCode(convertCode) : null);
		this.spaceEditting = EnumAdaptor.valueOf(spaceEditting, EditSpace.class);
		this.effectDigitLength = EnumAdaptor.valueOf(effectDigitLength, NotUseAtr.class);
		this.startDigit = Optional.ofNullable(startDigit != null ? new DataFormatCharacterDigit(startDigit) : null);
		this.endDigit = Optional.ofNullable(endDigit != null ?new DataFormatCharacterDigit(endDigit) : null);
	} 

	public ChacDataFmSet(ItemType itemType, String cid, NotUseAtr nullValueReplace,
			Optional<DataFormatNullReplacement> valueOfNullValueReplace, NotUseAtr cdEditting, NotUseAtr fixedValue,
			FixedLengthEditingMethod cdEdittingMethod, Optional<DataFormatCharacterDigit> cdEditDigit,
			Optional<ConvertCode> convertCode, EditSpace spaceEditting, NotUseAtr effectDigitLength,
			Optional<DataFormatCharacterDigit> startDigit, Optional<DataFormatCharacterDigit> endDigit,
			Optional<DataTypeFixedValue> valueOfFixedValue) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueReplace, valueOfNullValueReplace);
		this.cid = cid;
		this.cdEditting = cdEditting;
		this.cdEdittingMethod = cdEdittingMethod;
		this.cdEditDigit = cdEditDigit;
		this.convertCode = convertCode;
		this.spaceEditting = spaceEditting;
		this.effectDigitLength = effectDigitLength;
		this.startDigit = startDigit;
		this.endDigit = endDigit;
	}

}
