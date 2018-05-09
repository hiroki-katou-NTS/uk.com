package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 実績修正画面で利用するフォーマット
*/
@AllArgsConstructor
@Getter
public class FormatPerformance extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * フォーマット種類
    */
    private SettingUnitType settingUnitType;
    
    public static FormatPerformance createFromJavaType(String cid, int settingUnitType)
    {
        FormatPerformance  formatPerformance =  new FormatPerformance(cid,  EnumAdaptor.valueOf(settingUnitType,SettingUnitType.class ));
        return formatPerformance;
    }
    
}
