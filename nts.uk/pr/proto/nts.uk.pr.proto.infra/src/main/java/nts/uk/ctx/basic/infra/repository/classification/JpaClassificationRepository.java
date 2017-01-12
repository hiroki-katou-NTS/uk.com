package nts.uk.ctx.basic.infra.repository.classification;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.classification.Classification;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;

@Stateless
public class JpaClassificationRepository extends JpaRepository implements ClassificationRepository {

	@Override
	public void add(Classification classification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Classification classification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(CompanyCode companyCode, ClassificationCode classificationCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Classification> findSingleClassification(CompanyCode companyCode,
			ClassificationCode classificationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Classification> findAll(CompanyCode code) {
		// TODO Auto-generated method stub
		return null;
	}

}
