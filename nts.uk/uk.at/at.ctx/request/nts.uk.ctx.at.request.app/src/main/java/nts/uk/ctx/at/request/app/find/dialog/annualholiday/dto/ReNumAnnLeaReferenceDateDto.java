package nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnualLeaveManageInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnualLeaveRemainingNumberImport;

@Data
@Builder
public class ReNumAnnLeaReferenceDateDto {

	/**
	 * 年休残数(仮)
	 */
	private AnnualLeaveRemainingNumberImport annualLeaveRemainNumberExport;

	/**
	 * 年休付与情報(仮)
	 */
	private List<AnnualLeaveGrantDto> annualLeaveGrantExports;

	/**
	 * 年休管理情報(仮)
	 */
	private List<AnnualLeaveManageInforImport> annualLeaveManageInforExports;
}