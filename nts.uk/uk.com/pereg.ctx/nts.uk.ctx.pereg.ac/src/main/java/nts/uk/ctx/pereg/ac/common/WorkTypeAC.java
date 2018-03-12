package nts.uk.ctx.pereg.ac.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.worktype.algorithm.AcquireWorkTypeNamePub;
import nts.uk.ctx.pereg.dom.common.WorkTypeRepo;

@Stateless
public class WorkTypeAC implements WorkTypeRepo{
	
	@Inject
	private AcquireWorkTypeNamePub wtName;

	@Override
	public String acquireWorkTypeName(String workTypeCode) {
		return wtName.acquireWorkTypeName(workTypeCode);
	}

}
