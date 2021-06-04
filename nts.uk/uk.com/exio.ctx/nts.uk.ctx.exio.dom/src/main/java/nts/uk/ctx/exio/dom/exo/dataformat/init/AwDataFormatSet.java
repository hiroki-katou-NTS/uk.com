package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
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
    * 在職時出力
    */
    private Optional<DataTypeFixedValue> atWorkOutput;
    
    /**
    * 退職時出力
    */
    private Optional<DataTypeFixedValue> retirementOutput;

	public AwDataFormatSet(int itemType, String cid, String closedOutput, String absenceOutput, int fixedValue,
			String valueOfFixedValue, String atWorkOutput, String retirementOutput) {
		super(itemType, fixedValue, valueOfFixedValue, NotUseAtr.NOT_USE.value, null);
		this.cid = cid;
		this.closedOutput = (closedOutput == null) ? Optional.ofNullable(null) : Optional.of(new DataTypeFixedValue(closedOutput));
		this.absenceOutput = (absenceOutput == null) ? Optional.ofNullable(null) : Optional.of(new DataTypeFixedValue(absenceOutput));
		this.atWorkOutput = (atWorkOutput == null) ? Optional.ofNullable(null) : Optional.of(new DataTypeFixedValue(atWorkOutput));
		this.retirementOutput = (retirementOutput == null) ? Optional.ofNullable(null) : Optional.of(new DataTypeFixedValue(retirementOutput));
	}

	public AwDataFormatSet(ItemType itemType, String cid, Optional<DataTypeFixedValue> closedOutput,
			Optional<DataTypeFixedValue> absenceOutput, NotUseAtr fixedValue,
			Optional<DataTypeFixedValue> valueOfFixedValue, Optional<DataTypeFixedValue> atWorkOutput,
			Optional<DataTypeFixedValue> retirementOutput) {
		super(itemType, fixedValue, valueOfFixedValue, NotUseAtr.NOT_USE, Optional.empty());
		this.cid = cid;
		this.closedOutput = closedOutput;
		this.absenceOutput = absenceOutput;
		this.atWorkOutput = atWorkOutput;
		this.retirementOutput = retirementOutput;
	}

}
