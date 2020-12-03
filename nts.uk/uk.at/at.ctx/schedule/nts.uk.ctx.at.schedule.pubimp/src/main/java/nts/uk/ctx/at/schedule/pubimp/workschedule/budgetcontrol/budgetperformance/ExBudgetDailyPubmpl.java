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
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyExport;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyPub;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.TargetOrgIdenInforExport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ExBudgetDailyPubmpl implements ExtBudgetDailyPub {

    @Inject
    private ExtBudgetDailyRepository repository;

    @Override
    public List<ExtBudgetDailyExport> getAllExtBudgetDailyByPeriod(TargetOrgIdenInforExport targetOrg, DatePeriod datePeriod) {

        List<ExtBudgetDaily> data = repository.getAllExtBudgetDailyByPeriod(
            new TargetOrgIdenInfor(EnumAdaptor.valueOf(targetOrg.getUnit(), TargetOrganizationUnit.class),
                targetOrg.getWorkplaceId(),
                targetOrg.getWorkplaceGroupId()
            ), datePeriod
        );

        return data.stream().map(this::convertdata).collect(Collectors.toList());
    }

    @Override
    public List<ExtBudgetDailyExport> getByLstTargetOrgAndPeriod(List<TargetOrgIdenInforExport> lstTargetOrg, DatePeriod datePeriod) {

        List<TargetOrgIdenInfor> v = lstTargetOrg.stream().map(x -> new TargetOrgIdenInfor(
            EnumAdaptor.valueOf(x.getUnit(), TargetOrganizationUnit.class),
            x.getWorkplaceId(),
            x.getWorkplaceGroupId()
        )).collect(Collectors.toList());
        List<ExtBudgetDaily> extBudget = repository.getAllExtBudgetDailyByPeriod(
            lstTargetOrg.stream().map(x -> new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(x.getUnit(), TargetOrganizationUnit.class),
                x.getWorkplaceId(),
                x.getWorkplaceGroupId()
            )).collect(Collectors.toList()),
            datePeriod
        );
        return extBudget.stream().map(this::convertdata).collect(Collectors.toList());
    }

    private ExtBudgetDailyExport convertdata(ExtBudgetDaily domain){
        return new ExtBudgetDailyExport(
            new TargetOrgIdenInforExport(domain.getTargetOrg().getUnit().value,domain.getTargetOrg().getWorkplaceId(),domain.getTargetOrg().getWorkplaceGroupId()),
            domain.getItemCode().v(),
            domain.getYmd(),
            domain.getActualValue() instanceof ExtBudgetMoney ? ((ExtBudgetMoney) domain.getActualValue()).v() :
            domain.getActualValue() instanceof ExtBudgetNumberPerson ? ((ExtBudgetNumberPerson) domain.getActualValue()).v() :
            domain.getActualValue() instanceof ExtBudgetNumericalVal ? ((ExtBudgetNumericalVal) domain.getActualValue()).v() :
            domain.getActualValue() instanceof ExtBudgetTime ? ((ExtBudgetTime) domain.getActualValue()).v() :
            domain.getActualValue() instanceof ExtBudgetUnitPrice ? ((ExtBudgetUnitPrice) domain.getActualValue()).v() : 0
        );
    }
}
