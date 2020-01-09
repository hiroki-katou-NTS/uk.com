package nts.uk.ctx.hr.shared.ac.employee;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.groupcommonmaster.IGrpCmonMasterPub;
import nts.uk.ctx.bs.employee.pub.groupcommonmaster.export.GrpCmonMasterExprort;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmmMastItImport;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmonMasterImport;
import nts.uk.ctx.hr.shared.dom.employee.GrpCommonMasterAdaptor;

@Stateless
public class GrpCommonMasterAdaptorImpl implements GrpCommonMasterAdaptor{
	
	@Inject
	private IGrpCmonMasterPub grpCmonMasPub;

	@Override
	public Optional<GrpCmonMasterImport> findCommonMasterByContract(String contractCd, String cmMasterId) {
		Optional<GrpCmonMasterExprort> export = this.grpCmonMasPub.findCommonMasterByContract(contractCd, cmMasterId);
		if(export.isPresent()) {
			GrpCmonMasterExprort getExport = export.get();
			GrpCmonMasterImport result = new GrpCmonMasterImport(getExport.getCommonMasterName(), getExport.getCommonMasterItems().stream().map(x -> new GrpCmmMastItImport(x.getCommonMasterItemId(), x.getCommonMasterItemName(), x.getDisplayNumber())).collect(Collectors.toList()));
			return Optional.ofNullable(result);
		}else {
			return Optional.empty();
		}
	}

}
