package nts.uk.ctx.at.record.dom.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * 打刻機能の利用設定	
 * Path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻機能の利用設定
 * @author chungnt
 *
 */

@Getter
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
	
	/**
	 *  RICOH複写機打刻
	 */
	private boolean ricohStamp;
	
	//"[1] 打刻利用できるか	"
	public boolean canUsedStamping (StampMeans stampMeans) {
		switch (stampMeans) {
		case FINGER_AUTHC:
			return this.finger_authc;
		case IC_CARD:
			return this.ic_card;
		case INDIVITION:
			return this.indivition;
		case NAME_SELECTION:
			return this.name_selection;
		case PORTAL:
			return this.portal;
		case SMART_PHONE:
			return this.smart_phone;
		case RICOH_COPIER:
			return this.ricohStamp;
		default:
			return true;
		}
	}
}
