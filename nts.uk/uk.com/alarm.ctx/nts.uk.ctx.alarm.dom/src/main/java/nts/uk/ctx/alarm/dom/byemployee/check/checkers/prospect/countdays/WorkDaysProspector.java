/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.countdays;

import java.util.Optional;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.countdays.attendance.CheckAttendanceForIntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.WorkDaysDetailOfMonthly;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務日数プロスペクター
 * @author raiki_asada
 */
public class WorkDaysProspector extends WorkTypeCountProspectorBase {
    
    public WorkDaysProspector(RequireOfCreate require, String companyId, AggregateIntegrationOfDaily aggregateIntegrationOfDaily) {
        super(require, companyId, aggregateIntegrationOfDaily);
    }
    
    public double prospect(Require require, String cid, String employeeId) {
        return super.aggregateIntegrationOfDaily.aggregate(require, iod -> {
            return require.getWorkType(iod.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).map(workType -> {
                return WorkingConditionService.findWorkConditionByEmployee(require, cid, employeeId, iod.getYmd()).map(item -> {
                    //勤務種類のカウント表
                    WorkTypeDaysCountTable table = super.countTableGenerator.generate(workType);
                    boolean isAttendanceDay = CheckAttendanceForIntegrationOfDaily.check(iod);
                    
                    WorkDaysDetailOfMonthly result = new WorkDaysDetailOfMonthly();
                    // 会社ID, 勤務種類, 労働制, 勤務種類のカウント表、出勤してるか
                    result.aggregate(item.getLaborSystem(), table, isAttendanceDay);
                    return result.getDays().v();
                }).orElse(0.0);
            }).orElse(0.0);
        });
    }
    
    public interface Require extends WorkingConditionService.RequireM1, AggregateIntegrationOfDaily.AggregationRequire {
        Optional<WorkType> getWorkType(String workTypeCode);
    }
}
