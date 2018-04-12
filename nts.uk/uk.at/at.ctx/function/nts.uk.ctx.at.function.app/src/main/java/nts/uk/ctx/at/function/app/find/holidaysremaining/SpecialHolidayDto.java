package nts.uk.ctx.at.function.app.find.holidaysremaining;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
* 出力する特別休暇
*/
@AllArgsConstructor
@Value
public class SpecialHolidayDto
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
    
    
   
}
