package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

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
		this.cdConvertCd = Optional.ofNullable(cdConvertCd == null ? null : new CodeConvertCode(cdConvertCd));
		this.cdEditMethod = Optional.ofNullable(cdEditMethod == null ? null : EnumAdaptor.valueOf(cdEditMethod, FixedLengthEditingMethod.class));
		this.fixedVal = Optional.ofNullable(fixedVal == null ? null : new DataSettingFixedValue(fixedVal));
		this.cdEditDigit = Optional.ofNullable(cdEditDigit == null ? null : new DataFormatCharacterDigit(cdEditDigit));
		this.startDigit = Optional.ofNullable(startDigit == null ? null : new AcceptedDigit(startDigit));
		this.endDigit = Optional.ofNullable(endDigit == null ? null : new AcceptedDigit(endDigit));		
	}
	
	public String editStringValue(String itemValue) {
		//固定値使用する/しないを判別
		if(this.fixedValue == NotUseAtr.USE) {
			return this.fixedVal.get().v();
		}
		//有効桁長あり/なしを判別
		if(this.effectiveDigitLength == NotUseAtr.USE) {
			itemValue = itemValue.substring(this.startDigit.get().v(), this.endDigit.get().v());
		}
		//コード編集使用する/使用しないを判別
		if(this.cdEditing == NotUseAtr.USE) {
			int cdEdit = this.cdEditDigit.get().v();
			//「編集値」の桁数と「コード編集桁」を判別			
			int digitNumber = itemValue.length();
			//「編集値」の桁数≧コード編集桁数
			if(digitNumber >= cdEdit) {
				//「編集値」の前部から「コード編集桁」分を切り出して「編集値」とする
				itemValue = itemValue.substring(cdEdit);
			} else {
				//コード編集方法を判別
				switch (this.cdEditMethod.get()) {
				case ZERO_BEFORE:
					itemValue = StringUtils.leftPad(itemValue, cdEdit, "0");
					break;
				case ZERO_AFTER:
					itemValue = StringUtils.rightPad(itemValue, cdEdit, "0");
					break;
				case SPACE_BEFORE:
					itemValue = StringUtils.leftPad(itemValue, cdEdit, " ");
					break;
				case SPACE_AFTER:
					itemValue = StringUtils.rightPad(itemValue, cdEdit, " ");
					break;
				default:
					break;
				}
			}
		}
		//コード変換に選択があるか判別
		if(this.cdConvertCd.isPresent()) {
			//TODO
		}
		
		return itemValue;
	}

}
