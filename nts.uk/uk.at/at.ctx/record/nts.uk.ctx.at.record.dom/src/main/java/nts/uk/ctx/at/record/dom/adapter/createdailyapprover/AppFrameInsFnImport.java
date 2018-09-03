package nts.uk.ctx.at.record.dom.adapter.createdailyapprover;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppFrameInsFnImport {
	
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
