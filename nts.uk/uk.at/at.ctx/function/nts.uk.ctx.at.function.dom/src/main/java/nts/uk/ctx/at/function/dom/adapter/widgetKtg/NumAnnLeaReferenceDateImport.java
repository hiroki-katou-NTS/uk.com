package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NumAnnLeaReferenceDateImport {

	/**
	 * 年休残数(仮)
	 */
	private AnnualLeaveRemainingNumberImport annualLeaveRemainNumberImport;
	
	/**
	 * 年休付与情報(仮)
	 */
	private List<AnnualLeaveGrantImport> annualLeaveGrantImport;
	
	/**
	 * 年休管理情報(仮)
	 */
	private List<AnnualLeaveManageInforImport> annualLeaveManageInforImport;
}
