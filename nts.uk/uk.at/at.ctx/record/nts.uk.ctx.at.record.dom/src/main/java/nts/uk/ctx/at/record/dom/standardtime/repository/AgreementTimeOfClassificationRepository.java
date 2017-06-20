package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

public interface AgreementTimeOfClassificationRepository {

	void add(AgreementTimeOfClassification agreementTimeOfClassification);
	
	void remove(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
	
	List<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr);
}
