package nts.uk.ctx.hr.shared.dom.adapter;

import java.util.List;

public interface EmployeeInforAdapter {
	//<<Public>> 社員の情報を取得する
	List<EmployeeInformationImport> find(EmployeeInfoQueryImport param);

}
