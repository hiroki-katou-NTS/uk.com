package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;

/**
 * 本人による承認
 * @author dudt
 *
 */
@AllArgsConstructor
public enum PrincipalApprovalFlg {
	/**未本人	 */
	NOT_PRINCIPAL(0),
	/**本人	 */
	PRINCIPAL(1);
	
	public final int value;

}
