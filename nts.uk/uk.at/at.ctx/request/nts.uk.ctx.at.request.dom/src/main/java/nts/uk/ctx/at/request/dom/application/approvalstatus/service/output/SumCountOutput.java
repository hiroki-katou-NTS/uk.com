package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.NoArgsConstructor;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
public class SumCountOutput {
	/**
	 * 月別確認件数
	 */
	public int monthConfirm;
	/**
	 * 月別未確認件数
	 */
	public int monthUnconfirm;
	/**
	 * 本人確認件数
	 */
	public int personConfirm;
	/**
	 * 本人未確認件数
	 */
	public int personUnconfirm;
	/**
	 * 上司確認件数
	 */
	public int bossConfirm;
	/**
	 * 上司未確認件数
	 */
	public int bossUnconfirm;
}
