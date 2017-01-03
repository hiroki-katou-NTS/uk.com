package nts.uk.ctx.basic.infra.repository.classification;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.classification.Classification;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationRepository;

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
	public void remove(String companyCode, ClassificationCode classificationCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Classification> findSingleClassification(String companyCode,
			ClassificationCode classificationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Classification> findAll(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
