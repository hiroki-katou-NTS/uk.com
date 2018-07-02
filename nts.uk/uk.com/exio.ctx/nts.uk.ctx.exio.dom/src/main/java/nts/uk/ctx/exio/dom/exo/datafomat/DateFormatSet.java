package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 日付型データ形式設定
*/
@AllArgsConstructor
@Getter
public class DateFormatSet extends AggregateRoot
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
    * 小数桁
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueSubs;
    
    /**
    * 形式選択
    */
    private DateOutputFormat formatSelection;

	public DateFormatSet(String cid, int nullValueSubstitution, int fixedValue,
			String valueOfFixedValue, String valueOfNullValueSubs,
			int formatSelection) {
		super();
		this.cid = cid;
		this.nullValueSubstitution = EnumAdaptor.valueOf(nullValueSubstitution,NotUseAtr.class) ;
		this.fixedValue = EnumAdaptor.valueOf(fixedValue,NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue(valueOfFixedValue)) ;
		this.valueOfNullValueSubs = Optional.of(new DataFormatNullReplacement (valueOfNullValueSubs));
		this.formatSelection = EnumAdaptor.valueOf(formatSelection,DateOutputFormat.class) ;
	}
    
    
}
