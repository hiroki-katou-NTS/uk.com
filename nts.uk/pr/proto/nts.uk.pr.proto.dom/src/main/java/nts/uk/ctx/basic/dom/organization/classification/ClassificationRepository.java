package nts.uk.ctx.basic.dom.organization.classification;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;

public interface ClassificationRepository {
	
	void add(Classification classification);
	
	void update(Classification classification);
	
	void remove(CompanyCode companyCode, ClassificationCode classificationCode);
	
	Optional<Classification> findSingleClassification(CompanyCode companyCode, ClassificationCode classificationCode);
	
	List<Classification> findAll(CompanyCode code);

}
