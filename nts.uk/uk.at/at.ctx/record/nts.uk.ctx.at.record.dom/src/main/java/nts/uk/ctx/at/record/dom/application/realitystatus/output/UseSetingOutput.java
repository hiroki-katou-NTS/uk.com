package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UseSetingOutput {
	/**
	 * 月別本人確認を利用する
	 */
	private boolean monthlyIdentityConfirm;
	/**
	 * 月別確認を利用する
	 */
	private boolean monthlyConfirm;
	/**
	 * 上司確認を利用する
	 */
	private boolean useBossConfirm;
	/**
	 * 本人確認を利用する
	 */
	private boolean usePersonConfirm;
	/**
	 * 就業確定を利用する
	 */
	private boolean employmentConfirm;
}
