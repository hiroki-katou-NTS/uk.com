package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

public interface AgreementTimeOfWorkPlaceRepository {
	
	void add(AgreementTimeOfWorkPlace agreementTimeOfWorkPlace);
	
	void remove(String workplaceId, LaborSystemtAtr laborSystemAtr);
	
	List<AgreementTimeOfWorkPlace> find(String workplaceId , LaborSystemtAtr laborSystemAtr);
}
