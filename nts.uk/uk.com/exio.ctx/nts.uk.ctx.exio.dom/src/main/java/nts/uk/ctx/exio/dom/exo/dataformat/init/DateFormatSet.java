package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 日付型データ形式設定
*/
@Getter
public class DateFormatSet extends DataFormatSetting
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 形式選択
    */
    private DateOutputFormat formatSelection;
    

	public DateFormatSet(int itemType, String cid, int nullValueSubstitution, int fixedValue, String valueOfFixedValue,
			String valueOfNullValueSubs, int formatSelection) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueSubstitution, valueOfNullValueSubs);
		this.cid = cid;
		this.formatSelection = EnumAdaptor.valueOf(formatSelection, DateOutputFormat.class);
	}

	public DateFormatSet(ItemType itemType, String cid, NotUseAtr nullValueSubstitution, NotUseAtr fixedValue,
			Optional<DataTypeFixedValue> valueOfFixedValue, Optional<DataFormatNullReplacement> valueOfNullValueSubs,
			DateOutputFormat formatSelection) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueSubstitution, valueOfNullValueSubs);
		this.cid = cid;
		this.formatSelection = formatSelection;
		
	}

}
