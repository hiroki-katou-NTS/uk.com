package nts.uk.ctx.at.schedule.pubimp.workschedule.budgetcontrol.budgetperformance;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResult;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResultRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetMoneyValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetTimeValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetValues;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValuesExport;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyExport;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyPub;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.ExtBudgetType;
import nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance.TargetOrgIdenInforExport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@Stateless
public class ExBudgetDailyPubmpl implements ExtBudgetDailyPub {

    @Inject
    private ExternalBudgetActualResultRepository repository;

    @Override
    public List<ExtBudgetDailyExport> getAllExtBudgetDailyByPeriod(TargetOrgIdenInforExport targetOrg, DatePeriod datePeriod) {

        List<ExternalBudgetActualResult> data = repository.getAllByPeriod(
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
        List<ExternalBudgetActualResult> extBudget = repository.getAllByPeriod(
                lstTargetOrg.stream().map(x -> new TargetOrgIdenInfor(
                        EnumAdaptor.valueOf(x.getUnit(), TargetOrganizationUnit.class),
                        x.getWorkplaceId(),
                        x.getWorkplaceGroupId()
                )).collect(Collectors.toList()),
                datePeriod
        );
        return extBudget.stream().map(this::convertdata).collect(Collectors.toList());
    }

    private ExtBudgetDailyExport convertdata(ExternalBudgetActualResult domain) {
        return new ExtBudgetDailyExport(
                new TargetOrgIdenInforExport(domain.getTargetOrg().getUnit().value, domain.getTargetOrg().getWorkplaceId(), domain.getTargetOrg().getWorkplaceGroupId()),
                domain.getItemCode().v(),
                domain.getYmd(),
                getActualValue(domain.getActualValue())
        );
    }

    private ExtBudgetActualValuesExport getActualValue(ExternalBudgetValues actualValue) {
        if (actualValue instanceof ExternalBudgetMoneyValue) {
            return new ExtBudgetActualValuesExport(ExtBudgetType.MONEY, ((ExternalBudgetMoneyValue) actualValue).v());
        } else if (actualValue instanceof ExtBudgetNumberPerson) {
            return new ExtBudgetActualValuesExport(ExtBudgetType.NUM_PERSON, ((ExtBudgetNumberPerson) actualValue).v());
        } else if (actualValue instanceof ExtBudgetNumericalVal) {
            return new ExtBudgetActualValuesExport(ExtBudgetType.NUM_VAL, ((ExtBudgetNumericalVal) actualValue).v());
        } else if (actualValue instanceof ExternalBudgetTimeValue) {
            return new ExtBudgetActualValuesExport(ExtBudgetType.TIME, ((ExternalBudgetTimeValue) actualValue).v());
        } else if (actualValue instanceof ExtBudgetUnitPrice) {
            return new ExtBudgetActualValuesExport(ExtBudgetType.UNIT_PRICE, ((ExtBudgetUnitPrice) actualValue).v());
        }
        return null;
    }
}
