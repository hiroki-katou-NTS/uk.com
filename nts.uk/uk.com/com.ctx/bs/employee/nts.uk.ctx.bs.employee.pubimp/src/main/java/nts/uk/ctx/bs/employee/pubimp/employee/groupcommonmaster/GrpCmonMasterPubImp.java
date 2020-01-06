package nts.uk.ctx.bs.employee.pubimp.employee.groupcommonmaster;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterRepository;
import nts.uk.ctx.bs.employee.pub.groupcommonmaster.IGrpCmonMasterPub;
import nts.uk.ctx.bs.employee.pub.groupcommonmaster.export.GrpCmmMastItExport;
import nts.uk.ctx.bs.employee.pub.groupcommonmaster.export.GrpCmonMasterExprort;

public class GrpCmonMasterPubImp implements IGrpCmonMasterPub {

	@Inject
	private GroupCommonMasterRepository groupComMasterRep;

	@Override
	public Optional<GrpCmonMasterExprort> findCommonMasterByContract(String contractCd, String cmMasterId) {

		Optional<GroupCommonMaster> found = groupComMasterRep.getBasicInfo(contractCd, cmMasterId);

		if (found.isPresent()) {
			GroupCommonMaster getFound = found.get();
			GrpCmonMasterExprort result = new GrpCmonMasterExprort(
					getFound.getCommonMasterName().v(), getFound
							.getCommonMasterItems().stream().map(x -> new GrpCmmMastItExport(x.getCommonMasterItemId(),
									x.getCommonMasterItemName().v(), x.getDisplayNumber()))
							.collect(Collectors.toList()));
			return Optional.ofNullable(result);
		} else {
			return Optional.empty();
		}
	}

}
