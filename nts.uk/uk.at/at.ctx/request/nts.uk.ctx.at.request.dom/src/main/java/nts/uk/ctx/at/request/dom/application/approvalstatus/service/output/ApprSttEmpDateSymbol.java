package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApprSttEmpDateSymbol {
	/**
	 * 反映済みの場合
	 */
	REFLECTED(0, "◎"),
	
	/**
	 * 反映待ちの場合
	 */
	WAITREFLECTION(1, "〇"),
	
	/**
	 * 否認の場合
	 */
	DENIAL(2, "×"),
	
	/**
	 * 未反映、差し戻しの場合
	 */
	NOTREFLECTED_REMAND(3, "－");
	
	public final int value;
	
	public final String name;
}
