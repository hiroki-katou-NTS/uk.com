/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype;

import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.table.CountTableGenerator;

/**
 * 勤務種類カウントプロスペクタベース
 * @author raiki_asada
 */
public abstract class WorkTypeCountProspectorBase {
    protected final AggregateIntegrationOfDaily aggregateIntegrationOfDaily;
    protected final CountTableGenerator countTableGenerator;

    protected WorkTypeCountProspectorBase(RequireOfCreate require, String companyId, AggregateIntegrationOfDaily aggregateIntegrationOfDaily) {
        this.aggregateIntegrationOfDaily = aggregateIntegrationOfDaily;
        this.countTableGenerator = CountTableGenerator.initialize(require, companyId);
    }

    public interface RequireOfCreate extends CountTableGenerator.Require {
    }
}
