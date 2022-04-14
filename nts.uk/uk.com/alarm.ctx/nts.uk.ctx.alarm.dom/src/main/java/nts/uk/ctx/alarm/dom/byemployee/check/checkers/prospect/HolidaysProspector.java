/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect;

import java.util.Optional;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.HolidayDaysOfMonthly;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休日日数プロスペクター
 * @author raiki_asada
 */
public class HolidaysProspector extends WorkDaysProspectorBase {
    
    public HolidaysProspector(RequireOfCreate require, String companyId, AggregateIntegrationOfDaily aggregateIntegrationOfDaily) {
        super(require, companyId, aggregateIntegrationOfDaily);
    }
    
    public double prospect(Require require, String companyId, String employeeId) {
       return super.aggregateIntegrationOfDaily.aggregate(require, iod -> {
            return require.getWorkType(iod.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).map(workType -> {
                //勤務種類のカウント表
                WorkTypeDaysCountTable table = super.countTableGenerator.generate(workType);

                HolidayDaysOfMonthly result = new HolidayDaysOfMonthly();
                result.aggregate(table);
                return result.getDays().v();
            }).orElse(0.0);
        });
    }
    
    public interface Require extends AggregateIntegrationOfDaily.AggregationRequire {
        Optional<WorkType> getWorkType(String workTypeCode);
    }
}
