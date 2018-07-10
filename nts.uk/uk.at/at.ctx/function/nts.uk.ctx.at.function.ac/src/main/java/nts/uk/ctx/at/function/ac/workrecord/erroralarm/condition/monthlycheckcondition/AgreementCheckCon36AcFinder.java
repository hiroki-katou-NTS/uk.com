package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.AgreementCheckCon36Pub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.AgreementCheckCon36PubEx;

@Stateless
public class AgreementCheckCon36AcFinder implements AgreementCheckCon36FunAdapter {
	
	@Inject
	private AgreementCheckCon36Pub repo;

	@Override
	public AgreementCheckCon36FunImport getAgreementCheckCon36ById(String errorAlarmCheckID) {
		AgreementCheckCon36PubEx data = repo.getAgreementCheckCon36ById(errorAlarmCheckID);
		if(data ==null)
			return null;
		return convertToExport(data);
	} 
	
	private AgreementCheckCon36FunImport convertToExport (AgreementCheckCon36PubEx export) {
		return new AgreementCheckCon36FunImport(
				export.getErrorAlarmCheckID(),
				export.getClassification(),
				export.getCompareOperator(),
				export.getEralBeforeTime()
				);
	}



}
