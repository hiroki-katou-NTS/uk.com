package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

import java.util.List;

@Value
public class StateCorrelationHisPositionCommand {

    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始年月
    */
    private Integer startYearMonth;
    
    /**
    * 終了年月
    */
    private Integer endYearMonth;
    
    private int mode;

    List<StateLinkSettingMasterCommand> stateLinkSettingMaster;
}
