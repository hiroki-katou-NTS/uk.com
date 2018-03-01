package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.CollapseTargetPerson;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.CollapseTargetPersonPub;

@Stateless
public class CollapseTargetPersonPubImpl implements CollapseTargetPersonPub {
	@Inject
	private CollapseTargetPerson collapseTargetPerson;
	@Override
	public List<String> getListEmployeeID(GeneralDate date) {
		List<String> data = collapseTargetPerson.getListEmployeeID(date);
		if(data.isEmpty())
			return data;
		return Collections.emptyList();
	}

}
