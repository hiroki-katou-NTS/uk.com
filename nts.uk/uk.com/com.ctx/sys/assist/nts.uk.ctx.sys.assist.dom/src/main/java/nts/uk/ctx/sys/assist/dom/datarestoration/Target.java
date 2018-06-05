package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 対象者
*/
@AllArgsConstructor
@Getter
public class Target extends AggregateRoot
{
    
    /**
    * データ復旧処理ID
    */
    private String dataRecoveryProcessId;
    
    /**
    * 社員ID
    */
    private String sid;
    
    /**
    * 社員コード
    */
    private String scd;
    
    /**
    * ビジネスネーム
    */
    private String bussinessName;
    
    public static Target createFromJavaType(String dataRecoveryProcessId, String sid, String scd, String bussinessName)
    {
        Target  target =  new Target(dataRecoveryProcessId, sid, scd,  bussinessName);
        return target;
    }
    
}
