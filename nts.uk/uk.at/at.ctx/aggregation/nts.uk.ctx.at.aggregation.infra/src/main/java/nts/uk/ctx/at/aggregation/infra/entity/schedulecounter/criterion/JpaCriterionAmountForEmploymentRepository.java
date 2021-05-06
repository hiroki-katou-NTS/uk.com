package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

public class JpaCriterionAmountForEmploymentRepository extends JpaRepository
		implements CriterionAmountForEmploymentRepository {

	@Override
	public void insert(String cid, CriterionAmountForEmployment criterion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String cid, CriterionAmountForEmployment criterion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String cid, EmploymentCode employmentCd) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exist(String cid, EmploymentCode employmentCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<CriterionAmountForEmployment> get(String cid, EmploymentCode employmentCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CriterionAmountForEmployment> getAll(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
