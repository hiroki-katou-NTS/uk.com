package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCriterionAmountForCompanyRepository extends JpaRepository
		implements CriterionAmountForCompanyRepository {

	@Override
	public void insert(String cid, CriterionAmountForCompany criterion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String cid, CriterionAmountForCompany criterion) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exist(String cid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<CriterionAmountForCompany> get(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
