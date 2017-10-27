package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
/**
 * 上位職場にサーチ設定
 * @author dudt
 *
 */
@AllArgsConstructor
public enum SearchSetFlg {
	/** しない*/
	TODO(0),
	/** する*/
	NOT_TODO(1);
	public final int value;
}
