package nts.uk.ctx.exio.ac.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exi.adapter.role.OperableSystemImport;
import nts.uk.ctx.exio.dom.exi.adapter.role.ExRoleAdapter;
import nts.uk.ctx.sys.auth.pub.role.OperableSystemExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class ExRoleAcFinder implements ExRoleAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public OperableSystemImport getOperableSystem() {
		OperableSystemExport ex = roleExportRepo.getOperableSystem();
		OperableSystemImport im = new OperableSystemImport(ex.isOfficeHelper(), ex.isHumanResource(), ex.isAttendance(), ex.isSalary());
		return im;
	}

}
