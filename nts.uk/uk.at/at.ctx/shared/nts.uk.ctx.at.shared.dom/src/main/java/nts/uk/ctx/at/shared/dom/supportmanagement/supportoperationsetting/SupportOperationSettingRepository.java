package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;
/**
 * 応援の運用設定Repository
 * @author lan_lt
 *
 */
public interface SupportOperationSettingRepository {
	
	/**
	 * Update
	 * @param cid 会社ID
	 * @param supportOperationSetting 応援の運用設定
	 */
	void update( String cid, SupportOperationSetting supportOperationSetting );
	
	/**
	 * Get
	 * @param cid 会社ID
	 * @return 応援の運用設定
	 */
	SupportOperationSetting get( String cid );

}
