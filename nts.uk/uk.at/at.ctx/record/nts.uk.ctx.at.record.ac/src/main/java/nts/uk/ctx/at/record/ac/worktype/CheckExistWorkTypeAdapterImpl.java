package nts.uk.ctx.at.record.ac.worktype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.worktype.CheckExistWorkTypeAdapter;
import nts.uk.ctx.at.shared.pub.worktype.CheckExistWorkTypePub;

@Stateless
public class CheckExistWorkTypeAdapterImpl implements CheckExistWorkTypeAdapter  {

	@Inject
	private CheckExistWorkTypePub checkExistWorkTypePub;
	
	@Override
	public boolean checkExistWorkType(String workTypeCD) {
		return checkExistWorkTypePub.checkExitsWorkType(workTypeCD);
	}

}
