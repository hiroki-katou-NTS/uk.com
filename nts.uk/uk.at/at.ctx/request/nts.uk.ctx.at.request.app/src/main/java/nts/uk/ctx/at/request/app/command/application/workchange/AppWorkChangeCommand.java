package nts.uk.ctx.at.request.app.command.application.workchange;

import lombok.Value;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Value
public class AppWorkChangeCommand {
    
    /**
    * 勤務種類コード
    */
    private String workTypeCD;
    
    /**
    * 就業時間帯コード
    */
    private String workTimeCD;
    
    private Integer startTime1;
    
    private Integer endTime1;
    
}
