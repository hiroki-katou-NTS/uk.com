package nts.uk.ctx.basic.dom.organization.payclassification;

import java.util.List;


public interface PayClassificationRepository {

	//add pay classification
	void add(PayClassification payClassification);

	//update pay classification
	void update(PayClassification payClassification);

	//check existed
	boolean isExisted(String companyCode, String payClassificationCode);

	//find all pay classification
	List<PayClassification> findAll(String companyCode);

	//delete pay classification
	void remove(String companyCode, String payClassificationCode);
}
