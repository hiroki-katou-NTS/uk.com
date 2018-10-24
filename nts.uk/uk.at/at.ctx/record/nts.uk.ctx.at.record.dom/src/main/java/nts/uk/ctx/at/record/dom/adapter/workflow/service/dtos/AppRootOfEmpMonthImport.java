package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppRootOfEmpMonthImport {
	/**
	 * 基準社員
	 */
	private String employeeStandard;
	
	/**
	 * ルート状況
	 */
	private List<AppRootSituationMonth> approvalRootSituations;
	
}
