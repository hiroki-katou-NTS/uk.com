package nts.uk.ctx.at.record.dom.stamp.application;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * 打刻機能の利用設定	
 * Path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻機能の利用設定
 * @author chungnt
 *
 */

@AllArgsConstructor
public class SettingsUsingEmbossing implements DomainAggregate {

	/**
	 * 会社ID
	 */
	private final String companyId;
	
	/**
	 * 	氏名選択	
	 */
	private boolean name_selection;
	
	/**
	 * 	指認証打刻
	 */
	private boolean finger_authc;
	
	/**
	 * 	ICカード打刻
	 */
	private boolean ic_card;
	
	/**
	 * 	個人打刻	
	 */
	private boolean indivition;
	
	/**
	 * 	ポータル打刻	
	 */
	private boolean portal;
	
	/**
	 * 	スマホ打刻
	 */
	private boolean smart_phone;
	
	public boolean canUsedStamping (StampMeans stampMeans) {
		if (stampMeans.NAME_SELECTION != null) {
			return this.name_selection;
		}
		
		if (stampMeans.FINGER_AUTHC != null) {
			return this.finger_authc;
		}
		
		if (stampMeans.IC_CARD != null ) {
			return this.ic_card;
		}
		
		if (stampMeans.INDIVITION != null) {
			return this.indivition;
		}
		
		if (stampMeans.PORTAL != null) {
			return this.portal;
		}
		return this.smart_phone;
	}
	
}
 