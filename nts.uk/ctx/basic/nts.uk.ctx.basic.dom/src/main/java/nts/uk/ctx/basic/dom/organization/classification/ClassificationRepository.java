package nts.uk.ctx.basic.dom.organization.classification;

import java.util.List;
import java.util.Optional;

public interface ClassificationRepository {
	
	void add(Classification classification);
	
	void update(Classification classification);
	
	void remove(int companyCode, ClassificationCode classificationCode);
	
	Optional<Classification> findSingleClassification(int companyCode, ClassificationCode classificationCode);
	
	List<Classification> findAll(int companyCode);

}
