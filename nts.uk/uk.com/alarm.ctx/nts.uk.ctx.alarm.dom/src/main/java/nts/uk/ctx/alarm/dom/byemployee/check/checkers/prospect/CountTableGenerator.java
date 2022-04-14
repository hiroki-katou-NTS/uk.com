/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect;

import java.util.Optional;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
   カウント表ジェネレータ
 * @author raiki_asada
 */
public interface CountTableGenerator {
    
    WorkTypeDaysCountTable generate(WorkType workType);
    
    public static CountTableGenerator initialize(Require require, String cid) {
        return (workType) -> {
            return new WorkTypeDaysCountTable(workType,
                   //休暇加算設定
                   GetVacationAddSet.get(require, cid), 
                   //月別実績の集計方法
                   require.getAggregateMethodOfMonthly(cid));
        };
    }
    
    public interface Require extends GetVacationAddSet.RequireM1 {
        Optional<AggregateMethodOfMonthly> getAggregateMethodOfMonthly(String cid);
    }
}
