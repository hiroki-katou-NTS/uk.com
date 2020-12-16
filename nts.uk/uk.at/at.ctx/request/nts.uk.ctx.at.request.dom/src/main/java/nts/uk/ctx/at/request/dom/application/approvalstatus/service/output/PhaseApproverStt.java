package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PhaseApproverStt {
	/**
	 * フェーズ
	 */
	private int phaseOrder;
	/**
	 * 社員名
	 */
	private String empName;
	/**
	 * 人数
	 */
	private Integer countRemainApprover;
	
	private int approvalAtr;
}
