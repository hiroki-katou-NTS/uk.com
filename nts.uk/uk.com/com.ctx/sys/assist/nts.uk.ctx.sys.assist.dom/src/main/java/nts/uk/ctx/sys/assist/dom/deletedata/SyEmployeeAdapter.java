package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;

/**
 * The Interface SystemTypeAdapter.
 */
public interface SyEmployeeAdapter {
	/**
	 * Get list of employees by company id
	 * @return SystemTypeImport
	 */
	List<EmployeeDeletion> getListEmployeeByCompanyId(String cid);
}
