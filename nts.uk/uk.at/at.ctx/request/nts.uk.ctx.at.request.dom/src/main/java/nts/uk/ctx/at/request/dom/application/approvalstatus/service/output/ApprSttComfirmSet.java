package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttComfirmSet {
	/**
	 * 本人確認を利用する
	 */
	private boolean usePersonConfirm;
	/**
	 * 上司確認を利用する
	 */
	private boolean useBossConfirm;
	/**
	 * 月別本人確認を利用する
	 */
	private boolean monthlyIdentityConfirm;
	/**
	 * 月別確認を利用する
	 */
	private boolean monthlyConfirm;
	/**
	 * 就業確定を利用する
	 */
	private boolean employmentConfirm;
}
