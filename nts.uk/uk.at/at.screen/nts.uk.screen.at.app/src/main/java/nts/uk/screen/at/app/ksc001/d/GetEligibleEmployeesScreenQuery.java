package nts.uk.screen.at.app.ksc001.d;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.ConditionEmployee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * ScreenQuery: 設定した要件によると対象社員を取得する
 */
@Stateless
public class GetEligibleEmployeesScreenQuery {
    @Inject
    private ConditionEmployee.Require require;
    public List<String> getListEmployeeId(ConditionDto conditionDto){
        List<String> rs = new ArrayList<>();
        if(conditionDto!= null){
            val check = new ConditionEmployee(conditionDto.isTransfer(),conditionDto.isLeaveOfAbsence(),
                    conditionDto.isShortWorkingHours(),conditionDto.isChangedWorkingConditions());
            val listId = conditionDto.getListEmployeeId();
            val period = new DatePeriod(conditionDto.getStartDate(),conditionDto.getEndDate());
            listId.forEach(e ->{
                if(check.CheckEmployeesIsEligible(require,e,period)){
                    rs.add(e);
                }
            });
        }
        return rs;
    }
}
