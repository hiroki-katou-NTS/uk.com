package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 文字型データ形式設定
*/
@AllArgsConstructor
@Getter
@Setter
public class ChrDataFormatSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String conditionSetCd;
    
    /**
    * 受入項目番号
    */
    private int acceptItemNum;
    
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
    private Integer cdConvertCd;
    
    /**
    * コード編集方法
    */
    private Optional<FixedLengthEditingMethod> cdEditMethod;
    
    /**
    * コード編集桁
    */
    private Optional<CodeEditDigit> cdEditDigit;
    
    /**
    * 固定値の値
    */
    private Optional<ValueOfFixed> fixedVal;
    
    /**
    * 有効桁数開始桁
    */
    private Optional<AcceptedDigit> startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private Optional<AcceptedDigit> endDigit;

	public ChrDataFormatSet(String cid, String conditionSetCd, int acceptItemNum, Integer cdEditing,
			Integer fixedValue, Integer effectiveDigitLength, Integer cdConvertCd,
			Integer cdEditMethod, Integer cdEditDigit, String fixedVal,
			Integer startDigit, Integer endDigit) {
		super();
		this.cid = cid;
		this.conditionSetCd = conditionSetCd;
		this.acceptItemNum = acceptItemNum;
		this.cdEditing = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
//		if (null == cdConvertCd) {
//			this.cdConvertCd = Optional.empty();
//		} else {
//			this.cdConvertCd = Optional.of(cdConvertCd);
//		}
		if (null == cdEditMethod) {
			this.cdEditMethod = Optional.empty();
		} else {
			this.cdEditMethod = Optional.of(EnumAdaptor.valueOf(cdEditMethod, FixedLengthEditingMethod.class));
		}
		if (null == fixedVal) {
			this.fixedVal = Optional.empty();
		} else {
			this.fixedVal = Optional.of(new ValueOfFixed(fixedVal));
		}
		if (null == cdEditDigit) {
			this.cdEditDigit = Optional.empty();
		} else {
			this.cdEditDigit = Optional.of(new CodeEditDigit(cdEditDigit));
		}
		if (null == startDigit) {
			this.startDigit = Optional.empty();
		} else {
			this.startDigit = Optional.of(new AcceptedDigit(startDigit));
		}
		if (null == endDigit) {
			this.endDigit = Optional.empty();
		} else {
			this.endDigit = Optional.of(new AcceptedDigit(endDigit));
		}
	}

}
