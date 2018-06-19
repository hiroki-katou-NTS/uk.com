package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author sonnlb
 * 基準日時点年休残数
 */
@Value
@AllArgsConstructor
public class ReNumAnnLeaReferenceDateImport {

	/**
	 * 年休残数(仮)
	 */
	private AnnualLeaveRemainingNumberImport annualLeaveRemainNumberExport;

	/**
	 * 年休付与情報(仮)
	 */
	private List<AnnualLeaveGrantImport> annualLeaveGrantExports;

	/**
	 * 年休管理情報(仮)
	 */
	private List<AnnualLeaveManageInforImport> annualLeaveManageInforExports;

}
