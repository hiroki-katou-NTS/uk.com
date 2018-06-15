package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36Repository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.AgreementCheckCon36Pub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.AgreementCheckCon36PubEx;

@Stateless
public class AgreementCheckCon36PubImpl implements AgreementCheckCon36Pub {

	@Inject
	private AgreementCheckCon36Repository repo;
	
	@Override
	public AgreementCheckCon36PubEx getAgreementCheckCon36ById(String errorAlarmCheckID) {
		Optional<AgreementCheckCon36> data = repo.getAgreementCheckCon36ById(errorAlarmCheckID);
		if(data.isPresent())
			return fromDomain(data.get());
		return null;
	}
	
	private AgreementCheckCon36PubEx fromDomain(AgreementCheckCon36 domain) {
		return new AgreementCheckCon36PubEx(
				domain.getErrorAlarmCheckID(),
				domain.getClassification().value,
				domain.getCompareOperator().value,
				domain.getEralBeforeTime()
				);
	}

}
