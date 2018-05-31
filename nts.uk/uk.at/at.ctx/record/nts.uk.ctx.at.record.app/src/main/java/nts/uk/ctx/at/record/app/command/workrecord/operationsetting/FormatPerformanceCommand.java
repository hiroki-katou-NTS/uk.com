package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class FormatPerformanceCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * フォーマット種類
    */
    private int settingUnitType;
    
    private Long version;

}
