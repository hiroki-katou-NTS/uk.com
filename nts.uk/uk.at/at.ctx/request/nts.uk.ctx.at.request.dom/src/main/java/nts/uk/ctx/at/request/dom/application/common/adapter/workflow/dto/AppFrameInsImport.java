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
public class AppFrameInsImport {
	/**
	 * 順序
	 */
	private Integer frameOrder;
	
	/**
	 * 確定区分
	 */
	private boolean confirmAtr;
	
	/**
	 * 承認者リスト
	 */
	private List<String> listApprover;
}
