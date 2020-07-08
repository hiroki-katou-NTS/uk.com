package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

/**
 * ポータルの打刻設定Repository
 * 
 * @author chungnt
 *
 */

public interface PortalStampSettingsRepository {
	
	/**
	 * 	[1] insert(ポータルの打刻設定)
	 */
	public void insert(PortalStampSettings domain);

	/**
	 *  update(ポータルの打刻設定)
	 * 
	 * @param ポータルの打刻設定
	 *            domain
	 */
	public void update(PortalStampSettings domain);

	/**
	 * [3] get
	 * 
	 * @param 会社ID
	 *            comppanyID
	 * @return スポータルの打刻設定 Optional<ポータルの打刻設定>
	 * 
	 */
	public Optional<PortalStampSettings> get(String comppanyID);

}
