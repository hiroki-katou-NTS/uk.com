package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;

/**
* 実績修正画面で利用するフォーマット
*/
@AllArgsConstructor
@Value
public class FormatPerformanceDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * フォーマット種類
    */
    private int settingUnitType;
    
    
    public static FormatPerformanceDto fromDomain(FormatPerformance domain)
    {
        return new FormatPerformanceDto(domain.getCid(), domain.getSettingUnitType().value);
    }
    
}
