package nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 社員の絞り込み条件.
 */
@Getter
public class ConditionEmployee extends ValueObject {

    /** 異動者. reTargetTransfer*/
    private final boolean transfer;

    /** 休職休業者.reTargetLeave */
    private final boolean leaveOfAbsence;

    /** 短時間勤務者.reTargetShortWork */
    private final boolean shortWorkingHours;

    /** 労働条件変更者. reTargetLaborChange*/
    private final boolean changedWorkingConditions;

    /** [C-0] 社員の絞り込み条件 */
    public ConditionEmployee(boolean transfer, boolean leaveOfAbsence, boolean shortWorkingHours, boolean changedWorkingConditions) {
        this.transfer = transfer;
        this.leaveOfAbsence = leaveOfAbsence;
        this.shortWorkingHours = shortWorkingHours;
        this.changedWorkingConditions = changedWorkingConditions;
    }

    //[1] 社員が対象となるかどうかチェックする
    public boolean CheckEmployeesAreEligible(Require require, String sid, DatePeriod datePeriod){

        return isShortTimeWork(require, sid, datePeriod) ||
                isConditionChanger(require, sid, datePeriod) ||
                isLeave(require, sid, datePeriod) ||
                isTransferPerson(require, sid, datePeriod);
    }

    //[prv-1] 短時間勤務者か
    private boolean isShortTimeWork(Require require, String sid, DatePeriod datePeriod){
        if (!shortWorkingHours)
        return false;

        Optional<ShortWorkTimeHistory> shortTimeWorkHistory = require.GetShortWorkHistory(sid,datePeriod);

        return shortTimeWorkHistory.isPresent();
    }

    //[prv-2] 労働条件変更者か
    private boolean isConditionChanger(Require require, String sid, DatePeriod datePeriod){
        if (!changedWorkingConditions)
            return false;

        List<WorkingConditionItem> conditionItems = require.GetHistoryItemByPeriod(Arrays.asList(sid),datePeriod);

        return conditionItems.size()>=2;
    }

    //[prv-3] 休職休業者か
    private boolean isLeave(Require require, String sid, DatePeriod datePeriod){
        if (!leaveOfAbsence)
            return false;

        List<LeavePeriod> leavePeriods = require.GetLeavePeriod(Arrays.asList(sid),datePeriod);
        List<LeaveHolidayPeriod> leaveHolidayPeriods = require.GetLeaveHolidayPeriod(Arrays.asList(sid),datePeriod);

        return leavePeriods.size()>=1 || leaveHolidayPeriods.size()>=1;
    }

    //[prv-4] 異動者か
    private boolean isTransferPerson(Require require, String sid, DatePeriod datePeriod){
        if (!transfer)
            return false;
        List<WorkPlaceHist> workPlaceHists = require.GetWorkHistory(Arrays.asList(sid),datePeriod);
        return workPlaceHists.stream()
                .map(WorkPlaceHist::getLstWkpIdAndPeriod)
                .flatMap(List::stream)
                .collect(Collectors.toList()).size() >= 2;

    }

    public static interface Require {

        //[R-1] 期間を指定して短時間勤務履歴を取得する: Optional
        Optional<ShortWorkTimeHistory> GetShortWorkHistory(String sid,DatePeriod datePeriod);

        //[R-2] 期間を指定して社員の労働条件項目リストを取得する
        List<WorkingConditionItem> GetHistoryItemByPeriod(List<String> sids, DatePeriod datePeriod);

        //[R-3] 期間を指定して休職期間を取得する
        List<LeavePeriod> GetLeavePeriod(List<String> sids, DatePeriod datePeriod);

        //[R-4] 期間を指定して休業期間を取得する
        List<LeaveHolidayPeriod> GetLeaveHolidayPeriod(List<String> sids, DatePeriod datePeriod);

        //[R-5] 期間を指定して所属職場履歴を取得する
        List<WorkPlaceHist> GetWorkHistory(List<String> sids, DatePeriod datePeriod);
    }

}
