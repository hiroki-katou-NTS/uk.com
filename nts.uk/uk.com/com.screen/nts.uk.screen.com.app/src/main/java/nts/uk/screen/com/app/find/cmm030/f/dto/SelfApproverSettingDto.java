package nts.uk.screen.com.app.find.cmm030.f.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.PersonApprovalRootDto;

@Data
@AllArgsConstructor
public class SelfApproverSettingDto {

	/**
	 * 個人別承認ルート<List>
	 */
	private List<PersonApprovalRootDto> personApprovalRoots;
	
	/**
	 * 承認フェーズ<List>
	 */
	private List<ApprovalPhaseDto> approvalPhases;
	
	/**
	 * 社員ID（List）から社員コードと表示名を取得
	 */
	private List<ResultRequest600Export> employees;
}
