package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaCriterionAmountForCompanyRepository extends JpaRepository implements CriterionAmountForCompanyRepository {
    @Override
    public void insert(String cid, CriterionAmountForCompany criterion) {

    }

    @Override
    public void update(String cid, CriterionAmountForCompany criterion) {

    }

    @Override
    public boolean exist(String cid) {
        return false;
    }

    @Override
    public Optional<CriterionAmountForCompany> get(String cid) {
        return Optional.empty();
    }
}
