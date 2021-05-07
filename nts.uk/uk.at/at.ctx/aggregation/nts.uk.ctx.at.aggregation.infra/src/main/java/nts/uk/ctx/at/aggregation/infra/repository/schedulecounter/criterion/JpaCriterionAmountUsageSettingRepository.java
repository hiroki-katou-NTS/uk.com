package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCriterionAmountUsageSettingRepository extends JpaRepository
		implements CriterionAmountUsageSettingRepository {

	@Override
	public void insert(String cid, CriterionAmountUsageSetting usageSetting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String cid, CriterionAmountUsageSetting usageSetting) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exist(String cid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<CriterionAmountUsageSetting> get(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
