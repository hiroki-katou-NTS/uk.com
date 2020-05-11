package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;

/**
 * 打刻入力の共通設定Repository
 * @author chungnt
 *
 */

public interface CommonSettingsStampInputRepository {
	
	/**
	 * 	[1] insert(打刻入力の共通設定)
	 */
	public void insert(CommonSettingsStampInput domain);

	/**
	 *  update(打刻入力の共通設定)
	 * 
	 * @param 打刻機能の利用設定
	 *            domain
	 */
	public void update(CommonSettingsStampInput domain);

	/**
	 * [3] get
	 * 
	 * @param 会社ID
	 *            comppanyID
	 * @return 打刻入力の共通設定 Optional<打刻入力の共通設定>
	 * 
	 */
	public Optional<CommonSettingsStampInput> get(String comppanyID);
}
