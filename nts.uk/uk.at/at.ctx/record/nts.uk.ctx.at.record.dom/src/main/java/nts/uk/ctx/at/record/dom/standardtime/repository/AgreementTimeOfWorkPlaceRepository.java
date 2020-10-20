package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

public interface AgreementTimeOfWorkPlaceRepository {
	
	void add(AgreementTimeOfWorkPlace agreementTimeOfWorkPlace);
	
	void update(AgreementTimeOfWorkPlace agreementTimeOfWorkPlace);
	
	void remove(String workplaceId, LaborSystemtAtr laborSystemAtr);
	
	Optional<String> find(String workplaceId , LaborSystemtAtr laborSystemAtr);
	
	List<String> findWorkPlaceSetting(LaborSystemtAtr laborSystemAtr);
	
	Optional<AgreementTimeOfWorkPlace> findAgreementTimeOfWorkPlace(String workplaceId,
			LaborSystemtAtr laborSystemAtr);

	List<AgreementTimeOfWorkPlace> findWorkPlaceSetting(List<String> workplaceId);
}
