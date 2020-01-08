package nts.uk.ctx.hr.develop.app.jmm018retire.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmmMastItImport;

@Data
public class RelateMasterDto {
	
	// 処理結果 ket qua xu ly
	private Boolean processingResult;
	
	// 共通マスタ名  CommonMasterName
	private String commonMasterName;
	
	// 共通マスタ項目リスト CommonMasterItemList
	private List<GrpCmmMastItImport> commonMasterItemList;
	
	// 定年退職コースリスト RetirePlanCourseList
	private List<RetirePlanCourceDto> retirePlanCourseList;

	public RelateMasterDto(Boolean processingResult, String commonMasterName,
			List<GrpCmmMastItImport> commonMasterItemList, List<RetirePlanCourceDto> retirePlanCourseList) {
		super();
		this.processingResult = processingResult;
		this.commonMasterName = commonMasterName;
		this.commonMasterItemList = commonMasterItemList;
		this.retirePlanCourseList = retirePlanCourseList;
	}
}
