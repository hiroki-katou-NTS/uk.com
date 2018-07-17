package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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
    * NULL値置換
    */
    private NotUseAtr nullValueSubstitution;
    
    /**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
    /**
    * 固定値の値
    */
    private Optional<DataTypeFixedValue> valueOfFixedValue;
    
    /**
    * NULL値置換の値
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueSubs;
    
    /**
    * 形式選択
    */
    private DateOutputFormat formatSelection;

	public DateFormatSet(int itemType, String cid, int nullValueSubstitution, int fixedValue,
			String valueOfFixedValue, String valueOfNullValueSubs,
			int formatSelection) {
		super(itemType);
		this.cid = cid;
		this.nullValueSubstitution = EnumAdaptor.valueOf(nullValueSubstitution,NotUseAtr.class) ;
		this.fixedValue = EnumAdaptor.valueOf(fixedValue,NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue(valueOfFixedValue)) ;
		this.valueOfNullValueSubs = Optional.of(new DataFormatNullReplacement (valueOfNullValueSubs));
		this.formatSelection = EnumAdaptor.valueOf(formatSelection,DateOutputFormat.class) ;
	}
    
    
}
