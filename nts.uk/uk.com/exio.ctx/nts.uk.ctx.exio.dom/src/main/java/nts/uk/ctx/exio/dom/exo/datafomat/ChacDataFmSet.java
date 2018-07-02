package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.cdconvert.CodeConvertCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
* 文字型データ形式設定
*/
@Getter
public class ChacDataFmSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private NotUseAtr nullValueReplace;
    
    /**
    * NULL値置換の値
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueReplace;
    
    /**
    * コード編集
    */
    private NotUseAtr cdEditting;
    
    /**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
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
    private Optional<CodeConvertCode> cdConvertCd;
    
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
    
    /**
    * 固定値の値
    */
    private Optional<DataTypeFixedValue> valueOfFixedValue;

	public ChacDataFmSet(String cid, int nullValueReplace, String valueOfNullValueReplace, int cdEditting,
			int fixedValue, int cdEdittingMethod, int cdEditDigit, String cdConvertCd, int spaceEditting,
			int effectDigitLength, int startDigit, int endDigit, String valueOfFixedValue) {
		this.cid = cid;
		this.nullValueReplace = EnumAdaptor.valueOf(nullValueReplace, NotUseAtr.class);
		this.valueOfNullValueReplace = Optional.of(new DataFormatNullReplacement(valueOfNullValueReplace));
		this.cdEditting = EnumAdaptor.valueOf(cdEditting, NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.cdEdittingMethod = EnumAdaptor.valueOf(cdEdittingMethod,FixedLengthEditingMethod.class) ;
		this.cdEditDigit = Optional.of(new DataFormatCharacterDigit(cdEditDigit));
		this.cdConvertCd = Optional.of(new CodeConvertCode(cdConvertCd));
		this.spaceEditting = EnumAdaptor.valueOf(spaceEditting,EditSpace.class );
		this.effectDigitLength = EnumAdaptor.valueOf(effectDigitLength, NotUseAtr.class);
		this.startDigit = Optional.of(new DataFormatCharacterDigit(startDigit));
		this.endDigit = Optional.of(new DataFormatCharacterDigit(endDigit));
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue (valueOfFixedValue));
	}
    
    
}
