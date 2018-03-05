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
    
    
    private Long version;

	public static DateDataFormSetDto fromDomain(DateDataFormSet domain) {
		return new DateDataFormSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(),
				domain.getFixedValue().value, domain.getValueOfFixedValue().get().v(),
				domain.getFormatSelection().value, domain.getVersion());
	}
    
}
