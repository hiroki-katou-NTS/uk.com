package nts.uk.ctx.at.shared.pubimp.worktype.algorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.algorithm.AcquireWorkTypeNameService;
import nts.uk.ctx.at.shared.pub.worktype.algorithm.AcquireWorkTypeNamePub;

@Stateless
public class AcquireWorkTypeNamePubImpl implements AcquireWorkTypeNamePub {

	@Inject
	AcquireWorkTypeNameService acquireWorkTypeNameService;
	
	@Override
	public String acquireWorkTypeName(String workTypeCode) {
		return acquireWorkTypeNameService.acquireWorkTypeName(workTypeCode);
	}

}
