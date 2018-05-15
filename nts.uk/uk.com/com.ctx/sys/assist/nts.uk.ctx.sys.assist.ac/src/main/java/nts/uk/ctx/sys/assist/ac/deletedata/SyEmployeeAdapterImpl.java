package nts.uk.ctx.sys.assist.ac.deletedata;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.SyEmployeeAdapter;

@Stateless
public class SyEmployeeAdapterImpl implements SyEmployeeAdapter {

	/** The RoleExportRepo pub. */
//	@Inject
//	private SyEmployeePub syEmployeePub;


	@Override
	public List<EmployeeDeletion> getListEmployeeByCompanyId(String cid) {
		
		return null;
	}
	
	

}
