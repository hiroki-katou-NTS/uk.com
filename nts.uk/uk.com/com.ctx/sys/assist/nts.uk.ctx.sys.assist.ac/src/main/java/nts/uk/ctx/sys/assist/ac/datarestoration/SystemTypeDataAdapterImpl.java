package nts.uk.ctx.sys.assist.ac.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.OperableSystemImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.SystemTypeDataAdapter;
import nts.uk.ctx.sys.auth.pub.role.OperableSystemExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
@Stateless
public class SystemTypeDataAdapterImpl implements SystemTypeDataAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public OperableSystemImport getOperableSystem() {
		OperableSystemExport export = roleExportRepo.getOperableSystem();
		return new OperableSystemImport(export.isOfficeHelper(), export.isHumanResource(), export.isAttendance(),
				export.isSalary());
	}
}
