package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 復旧対象
*/
@AllArgsConstructor
@Getter
public class RestorationTarget extends AggregateRoot
{
    
    /**
    * データ復旧処理ID
    */
    private String dataRecoveryProcessId;
    
    /**
    * 復旧カテゴリ
    */
    private String recoveryCategory;
    
    /**
    * 復旧対象開始日
    */
    private GeneralDate recoveryTargetStartDate;
    
    /**
    * 復旧対象終了日
    */
    private GeneralDate recoveryTargetEndDate;
    
    public static RestorationTarget createFromJavaType(String dataRecoveryProcessId, String recoveryCategory, GeneralDate recoveryTargetStartDate, GeneralDate recoveryTargetEndDate)
    {
        RestorationTarget  restorationTarget =  new RestorationTarget(dataRecoveryProcessId, recoveryCategory, recoveryTargetStartDate,  recoveryTargetEndDate);
        return restorationTarget;
    }
    
}
