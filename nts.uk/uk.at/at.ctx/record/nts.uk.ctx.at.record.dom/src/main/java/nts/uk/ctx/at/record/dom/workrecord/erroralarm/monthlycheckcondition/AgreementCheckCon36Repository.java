package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;

public interface AgreementCheckCon36Repository {

	List<AgreementCheckCon36> getAgreementCheckCon36ById(String errorAlarmCheckID);
	
	void addAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36);
	
	void updateAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36);
	
	void deleteAgreementCheckCon36(String errorAlarmCheckID);
	
}
