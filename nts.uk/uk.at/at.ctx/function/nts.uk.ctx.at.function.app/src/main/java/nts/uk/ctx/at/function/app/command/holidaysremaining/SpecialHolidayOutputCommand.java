package nts.uk.ctx.at.function.app.command.holidaysremaining;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SpecialHolidayOutputCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String cd;
    
    /**
    * コード
    */
    private String specialCd;
    
    private Long version;

}
