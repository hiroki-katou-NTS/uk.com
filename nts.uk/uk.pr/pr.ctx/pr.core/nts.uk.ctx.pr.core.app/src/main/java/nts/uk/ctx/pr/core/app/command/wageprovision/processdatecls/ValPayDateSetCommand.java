package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.AdvancedSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.BasicSettingDto;

@Value
public class ValPayDateSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    private BasicSettingDto basicSetting;

    private AdvancedSettingDto advancedSetting;



}
