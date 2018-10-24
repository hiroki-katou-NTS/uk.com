package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認フェーズ中間データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppPhaseInsExport {
	
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
	private List<AppFrameInsExport> listAppFrame;
	
}
