package nts.uk.ctx.basic.dom.organization.payclassification;

import java.util.List;
import java.util.Optional;


public interface PayClassificationRepository {



	//add pay class
	void add(PayClassification payClassification);

	//update pay class
	void update(PayClassification payClassification);

	//check existed
	boolean isExisted(String companyCode, PayClassificationCode payClassificationCode);

	//find all pay class
	List<PayClassification> findAll(String companyCode);

	Optional<PayClassification> findSinglePayClassification(String companyCode,
			String payClassificationCode);

	//delete payclass
	void remove(String companyCode, PayClassificationCode payClassificationCode);
}
