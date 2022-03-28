package nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.imported.指定職場の指定在籍状態の社員
 */
@Value
@AllArgsConstructor
public class EmployeeInDesignatedImport {

	/**
	 * 社員ID									
	 */
	private String sid;
	
	/**
	 * 在休退状態
	 */
	private int statusOfEmp;
}
