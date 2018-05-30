package nts.uk.ctx.sys.env.dom.contact;

import java.util.List;

public interface EmployeeContactAdapter {
	/**
	 * RequestList 378
	 * 社員ID（List）から社員連絡先を取得
	 * @return
	 */
	List<EmployeeContactObjectImport> getList(List<String> employeeIds);
}
