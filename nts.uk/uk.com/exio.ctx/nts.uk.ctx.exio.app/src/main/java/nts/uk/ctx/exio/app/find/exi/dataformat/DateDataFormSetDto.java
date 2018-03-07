package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;

/**
* 日付型データ形式設定
*/
@AllArgsConstructor
@Value
public class DateDataFormSetDto
{
    
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 形式選択
    */
    private int formatSelection;
    

	public static DateDataFormSetDto fromDomain(DateDataFormSet domain) {
		return new DateDataFormSetDto(domain.getFixedValue().value, domain.getValueOfFixedValue().get().v(),
				domain.getFormatSelection().value);
	}
    
}
