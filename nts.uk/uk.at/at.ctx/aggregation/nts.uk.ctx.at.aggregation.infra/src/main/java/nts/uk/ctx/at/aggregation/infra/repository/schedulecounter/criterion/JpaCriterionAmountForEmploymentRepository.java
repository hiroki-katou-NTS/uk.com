package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaCriterionAmountForEmploymentRepository extends JpaRepository implements CriterionAmountForEmploymentRepository {
    @Override
    public void insert(String cid, CriterionAmountForEmployment criterion) {

    }

    @Override
    public void update(String cid, CriterionAmountForEmployment criterion) {

    }

    @Override
    public void delete(String cid, EmploymentCode employmentCd) {

    }

    @Override
    public boolean exist(String cid, EmploymentCode employmentCd) {
        return false;
    }

    @Override
    public Optional<CriterionAmountForEmployment> get(String cid, EmploymentCode employmentCd) {
        return Optional.empty();
    }

    @Override
    public List<CriterionAmountForEmployment> getAll(String cid) {
        return new ArrayList<>();
    }
}
