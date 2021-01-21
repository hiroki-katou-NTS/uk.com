package nts.uk.screen.at.app.ksc001.d;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeaveHolidayAdapter;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeavePeriodAdapter;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.WorkPlaceHistAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.*;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ScreenQuery: 設定した要件によると対象社員を取得する
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class GetEligibleEmployeesScreenQuery {
    @Inject
    private SWorkTimeHistoryRepository sWorkTimeHistoryRepository;
    @Inject
    private WorkingConditionItemRepository workingConditionItemRepository;
    @Inject
    private WorkPlaceHistAdapter syWorkplaceAdapter;
    @Inject
    private LeaveHolidayAdapter leaveHolidayAdapter;
    @Inject
    private LeavePeriodAdapter leavePeriodAdapter;
    @Inject
    private ManagedParallelWithContext parallel;
    public List<String> getListEmployeeId(ConditionDto conditionDto){
        List<String> rs = new ArrayList<>();
        ImplRequire require = new ImplRequire(sWorkTimeHistoryRepository,workingConditionItemRepository,syWorkplaceAdapter
                ,leaveHolidayAdapter,leavePeriodAdapter);
        if(conditionDto!= null){
            val checkEmployee = new ConditionEmployee(conditionDto.isTransfer(),conditionDto.isLeaveOfAbsence(),
                    conditionDto.isShortWorkingHours(),conditionDto.isChangedWorkingConditions());
            val listId = conditionDto.getListEmployeeId();
            val period = new DatePeriod(conditionDto.getStartDate(),conditionDto.getEndDate());
            this.parallel.forEach(listId,e ->{
                if(checkEmployee.CheckEmployeesAreEligible(require,e,period)){
                    rs.add(e);
                }
            });
        }
        return rs;
    }
    @AllArgsConstructor
    private static class ImplRequire implements ConditionEmployee.Require{
        private SWorkTimeHistoryRepository sWorkTimeHistoryRepository;
        private WorkingConditionItemRepository workingConditionItemRepository;
        private WorkPlaceHistAdapter workPlaceHistAdapter;
        private LeaveHolidayAdapter leaveHolidayAdapter;
        private LeavePeriodAdapter leavePeriodAdapter;
        //[R-1]
        @Override
        public Optional<ShortWorkTimeHistory> GetShortWorkHistory(String sid, DatePeriod datePeriod) {
            val rs = sWorkTimeHistoryRepository.findLstByEmpAndPeriod(Arrays.asList(sid),datePeriod);
            if(!rs.isEmpty()){
              return   Optional.of(rs.get(0));
            }
            return Optional.empty();
        }
        //[R-2]
        @Override
        public List<WorkingConditionItem> GetHistoryItemByPeriod(List<String> sids, DatePeriod datePeriod) {
            val listItem = workingConditionItemRepository.getBySidsAndDatePeriod(sids,datePeriod);
            return listItem;
        }
        //[R-3]
        @Override
        public List<LeavePeriod> GetLeavePeriod(List<String> sids, DatePeriod datePeriod) {
            return leavePeriodAdapter.GetLeavePeriod(sids,datePeriod);
        }
        //[R-4]
        @Override
        public List<LeaveHolidayPeriod> GetLeaveHolidayPeriod(List<String> sids, DatePeriod datePeriod) {

            return leaveHolidayAdapter.GetLeaveHolidayPeriod(sids,datePeriod);
        }
        //[R-5]-[RQL-189]-SyWorkplacePub=>WorkplacePub
        @Override
        public List<WorkPlaceHist> GetWorkHistory(List<String> sids, DatePeriod datePeriod) {
            return workPlaceHistAdapter.GetWorkHistory(sids,datePeriod);
        }
    }
}
