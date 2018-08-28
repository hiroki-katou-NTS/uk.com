package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認枠中間データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppFrameInsExport {
	
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
