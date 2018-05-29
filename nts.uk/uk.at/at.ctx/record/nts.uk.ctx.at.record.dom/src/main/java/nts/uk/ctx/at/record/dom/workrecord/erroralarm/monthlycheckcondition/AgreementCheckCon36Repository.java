package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

public interface AgreementCheckCon36Repository {

	Optional<AgreementCheckCon36> getAgreementCheckCon36ById(String errorAlarmCheckID);
	
	void addAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36);
	
	void updateAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36);
	
	void deleteAgreementCheckCon36(String errorAlarmCheckID);
	
}
