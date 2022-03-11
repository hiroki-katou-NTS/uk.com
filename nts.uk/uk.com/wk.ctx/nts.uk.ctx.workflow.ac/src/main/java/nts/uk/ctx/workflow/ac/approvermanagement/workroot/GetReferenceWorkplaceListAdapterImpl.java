package nts.uk.ctx.workflow.ac.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.GetReferenceWorkplaceListAdapter;

@Stateless
public class GetReferenceWorkplaceListAdapterImpl implements GetReferenceWorkplaceListAdapter {

	// 就業
	private static final int SYSTEM_TYPE = 2;

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public List<String> findByBaseDate(GeneralDate baseDate) {
		return this.roleExportRepo.findWorkPlaceIdByRoleId(SYSTEM_TYPE, baseDate, Optional.empty())
				.getListWorkplaceIds();
	}
}
