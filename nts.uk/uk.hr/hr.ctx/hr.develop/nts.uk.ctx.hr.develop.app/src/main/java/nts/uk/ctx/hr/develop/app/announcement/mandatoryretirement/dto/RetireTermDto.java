package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.List;

import lombok.Data;

@Data
public class RetireTermDto {
	
	/** 共通項目ID  */
	private String empCommonMasterItemId;
	
	/** 定年有無 */
	private boolean usageFlg;
	
	/** 選択可能定年退職コース */
	private List<Long> enableRetirePlanCourse;

	public RetireTermDto(String empCommonMasterItemId, boolean usageFlg, List<Long> enableRetirePlanCourse) {
		super();
		this.empCommonMasterItemId = empCommonMasterItemId;
		this.usageFlg = usageFlg;
		this.enableRetirePlanCourse = enableRetirePlanCourse;
	}
}
