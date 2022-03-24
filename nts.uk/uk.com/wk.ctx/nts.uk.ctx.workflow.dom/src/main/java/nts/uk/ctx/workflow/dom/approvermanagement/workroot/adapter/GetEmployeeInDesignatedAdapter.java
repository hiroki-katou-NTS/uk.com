package nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * IF_指定職場の指定在籍状態の社員を取得Adapter
 */
public interface GetEmployeeInDesignatedAdapter {

	/**
	 * [1]取得する
	 * 
	 * @param workplaceIds 職場IDList
	 * @param baseDate     基準日
	 * @param empStatus    在籍状態
	 * @return List<指定職場の指定在籍状態の社員>
	 */
	public List<EmployeeInDesignatedImport> findEmployees(List<String> workplaceIds, GeneralDate baseDate,
			List<Integer> empStatus);
}
