package nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLongTermCareErrorsParam {

 // 社員ID
    String sID;
    
    // 年月
    YearMonth yearMonth;
    
    // 締めID
    ClosureId closureId;
    
    // 締め日
    ClosureDate closureDate;
    
    // エラー情報(List)
    List<ChildCareNurseErrors> childCareNurseErrors;
}
