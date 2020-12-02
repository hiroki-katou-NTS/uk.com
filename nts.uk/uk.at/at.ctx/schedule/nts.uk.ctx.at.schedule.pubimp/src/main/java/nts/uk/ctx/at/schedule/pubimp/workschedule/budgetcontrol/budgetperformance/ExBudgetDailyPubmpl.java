package nts.uk.ctx.at.schedule.pubimp.workschedule.budgetcontrol.budgetperformance;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyExport;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyPub;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.TargetOrgIdenInforExport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ExBudgetDailyPubmpl implements ExBudgetDailyPub {

    @Inject
    private ExtBudgetDailyRepository repository;

    @Override
    public List<ExBudgetDailyExport> getAllExtBudgetDailyByPeriod(int unit, String workplaceId, String workplaceGroupId, DatePeriod datePeriod) {

        List<ExtBudgetDaily> data = repository.getAllExtBudgetDailyByPeriod(
            new TargetOrgIdenInfor(EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
                workplaceId == null ? Optional.empty() : Optional.of(workplaceId),
                workplaceGroupId == null ? Optional.empty() : Optional.of(workplaceGroupId)
                ),datePeriod);

        return data.stream().map(x -> new ExBudgetDailyExport(
            new TargetOrgIdenInforExport(x.getTargetOrg().getUnit().value,x.getTargetOrg().getWorkplaceId(),x.getTargetOrg().getWorkplaceGroupId()),
            x.getItemCode().v(),
            x.getYmd(),
            x.getActualValue() instanceof ExtBudgetMoney ? ((ExtBudgetMoney) x.getActualValue()).v() :
            x.getActualValue() instanceof ExtBudgetNumberPerson ? ((ExtBudgetNumberPerson) x.getActualValue()).v() :
            x.getActualValue() instanceof ExtBudgetNumericalVal ? ((ExtBudgetNumericalVal) x.getActualValue()).v() :
            x.getActualValue() instanceof ExtBudgetTime ? ((ExtBudgetTime) x.getActualValue()).v() :
            x.getActualValue() instanceof ExtBudgetUnitPrice ? ((ExtBudgetUnitPrice) x.getActualValue()).v() : 0
        )).collect(Collectors.toList());
    }
}
