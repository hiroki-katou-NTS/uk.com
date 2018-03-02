package nts.uk.ctx.at.function.ac.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.CollapseTargetPersonAdapter;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.CollapseTargetPersonPub;

@Stateless
public class CollapseTargetPersonAcFinder implements CollapseTargetPersonAdapter {
	@Inject
	private CollapseTargetPersonPub collapseTargetPersonPub;

	@Override
	public List<String> getListEmployeeID(GeneralDate date) {
		return this.collapseTargetPersonPub.getListEmployeeID(date);
	}
	

}
