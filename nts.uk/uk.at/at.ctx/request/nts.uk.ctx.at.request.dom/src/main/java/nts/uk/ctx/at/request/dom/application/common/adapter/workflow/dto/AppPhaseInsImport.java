package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppPhaseInsImport {
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
	private List<AppFrameInsImport> listAppFrame;
}
