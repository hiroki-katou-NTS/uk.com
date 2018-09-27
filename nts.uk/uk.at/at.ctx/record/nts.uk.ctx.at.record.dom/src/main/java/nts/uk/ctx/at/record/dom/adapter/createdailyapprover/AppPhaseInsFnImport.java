package nts.uk.ctx.at.record.dom.adapter.createdailyapprover;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppPhaseInsFnImport {
	
	/**
	 * 順序
	 */
	private Integer phaseOrder;
	
	/**
	 * 承認形態
	 */
	private Integer approvalForm;
	
	/**
	 * 承認枠
	 */
	private List<AppFrameInsFnImport> listAppFrame;
	
}
