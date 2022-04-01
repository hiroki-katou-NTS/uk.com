package nts.uk.ctx.exio.app.command.exo.charegister;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;


@Setter
@Getter
@NoArgsConstructor
public class ChacDataFmSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private int nullValueReplace;
    
    /**
    * NULL値置換の値
    */
    private String valueOfNullValueReplace;
    
    /**
    * コード編集
    */
    private int cdEditting;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * コード編集方法
    */
    private int cdEdittingMethod;
    
    /**
    * コード編集桁
    */
    private Integer cdEditDigit;
    
    /**
    * コード変換コード
    */
    private String cdConvertCd;
    
    /**
    * スペース編集
    */
    private int spaceEditting;
    
    /**
    * 有効桁数
    */
    private int effectDigitLength;
    
    /**
    * 有効桁数開始桁
    */
    private Integer startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private Integer endDigit;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    private Long version;

	public ChacDataFmSetCommand(String cid, int nullValueReplace, String valueOfNullValueReplace, int cdEditting,
			int fixedValue, int cdEdittingMethod, Integer cdEditDigit, String cdConvertCd, int spaceEditting,
			int effectDigitLength, Integer startDigit, Integer endDigit, String valueOfFixedValue, Long version) {
		super();
		this.cid = cid;
		this.nullValueReplace = nullValueReplace;
		this.valueOfNullValueReplace = valueOfNullValueReplace;
		this.cdEditting = cdEditting;
		this.fixedValue = fixedValue;
		this.cdEdittingMethod = cdEdittingMethod;
		this.cdEditDigit = cdEditDigit;
		this.cdConvertCd = cdConvertCd;
		this.spaceEditting = spaceEditting;
		this.effectDigitLength = effectDigitLength;
		this.startDigit = startDigit;
		this.endDigit = endDigit;
		this.valueOfFixedValue = valueOfFixedValue;
		this.version = version;
	}

}
