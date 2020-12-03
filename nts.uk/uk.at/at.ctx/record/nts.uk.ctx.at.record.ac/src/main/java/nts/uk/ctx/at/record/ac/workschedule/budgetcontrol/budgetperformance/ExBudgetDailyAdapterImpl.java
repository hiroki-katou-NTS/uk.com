package nts.uk.ctx.at.record.ac.workschedule.budgetcontrol.budgetperformance;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.TargetOrgIdenInforImport;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyPub;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.TargetOrgIdenInforExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ExBudgetDailyAdapterImpl implements ExBudgetDailyAdapter {

    @Inject
    private ExtBudgetDailyPub exBudgetDailyPub;

    @Override
    public List<ExBudgetDailyImport> getByLstTargetOrgAndPeriod(List<TargetOrgIdenInforImport> targetOrgs, DatePeriod datePeriod) {
        List<TargetOrgIdenInforExport> targetOrgExports = targetOrgs.stream().map(x ->
                new TargetOrgIdenInforExport(
                        x.getUnit(),
                        x.getWorkplaceId(),
                        x.getWorkplaceGroupId()))
                .collect(Collectors.toList());
        return exBudgetDailyPub.getByLstTargetOrgAndPeriod(targetOrgExports, datePeriod).stream().map(x ->
                new ExBudgetDailyImport(
                        new TargetOrgIdenInforImport(
                                x.getTargetOrg().getUnit(),
                                x.getTargetOrg().getWorkplaceId(),
                                x.getTargetOrg().getWorkplaceGroupId()),
                        x.getItemCode(),
                        x.getYmd(),
                        x.getActualValue()))
                .collect(Collectors.toList());
    }
}
