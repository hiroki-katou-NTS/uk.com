package nts.uk.ctx.at.record.pub.application.realitystatus;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UseSetingExport {
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
}
