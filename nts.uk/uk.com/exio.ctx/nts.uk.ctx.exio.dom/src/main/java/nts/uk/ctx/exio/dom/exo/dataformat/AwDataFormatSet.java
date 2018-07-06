package nts.uk.ctx.exio.dom.exo.dataformat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 在職区分型データ形式設定
*/
@Getter
public class AwDataFormatSet extends DataFormatSetting
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 休業時出力
    */
    private Optional<DataTypeFixedValue> closedOutput;
    
    /**
    * 休職時出力
    */
    private Optional<DataTypeFixedValue> absenceOutput;
    
    /**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
    /**
    * 固定値の値
    */
    private Optional<DataTypeFixedValue> valueOfFixedValue;
    
    /**
    * 在職時出力
    */
    private Optional<DataTypeFixedValue> atWorkOutput;
    
    /**
    * 退職時出力
    */
    private Optional<DataTypeFixedValue> retirementOutput;

	public AwDataFormatSet(int itemType ,String cid, String closedOutput,
			String absenceOutput, int fixedValue,
			String valueOfFixedValue, String atWorkOutput,
			String retirementOutput) {
		super(itemType);
		this.cid = cid;
		this.closedOutput = Optional.of(new DataTypeFixedValue(closedOutput));
		this.absenceOutput = Optional.of(new DataTypeFixedValue(absenceOutput));
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue(valueOfFixedValue));
		this.atWorkOutput = Optional.of(new DataTypeFixedValue(atWorkOutput));
		this.retirementOutput = Optional.of(new DataTypeFixedValue(retirementOutput));
	}
	
	


    
    
    
}
