package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<AgreementCheckCon36FunImport> getAgreementCheckCon36ById(String errorAlarmCheckID) {
		List<AgreementCheckCon36PubEx> data = repo.getAgreementCheckCon36ById(errorAlarmCheckID);
		if(data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c->convertToExport(c)).collect(Collectors.toList());
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
