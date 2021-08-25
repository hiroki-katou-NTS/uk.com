package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.budget.laborcost;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaLaborCostBudgetRepository extends JpaRepository implements LaborCostBudgetRepository {
    @Override
    public void insert(String companyId, LaborCostBudget domain) {

    }

    @Override
    public void delete(String companyId, TargetOrgIdenInfor targetOrg, GeneralDate ymd) {

    }

    @Override
    public List<LaborCostBudget> get(String companyId, TargetOrgIdenInfor targetOrg, DatePeriod period) {
        return new ArrayList<>();
    }
}
