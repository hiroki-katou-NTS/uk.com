package nts.uk.ctx.sys.portal.dom.toppagealarm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページアラーム（ver4～）.トップページアラーム
 * 既読日時
 */
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToppageAlarmLog extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * アラーム分類
	 */
	private AlarmClassification alarmClassification;
	
	/**
	 * 識別キー
	 */
	private IdentificationKey identificationKey;
	
	/**
	 * 表示社員ID
	 */
	private String displaySId;
	
	/**
	 * 表示社員区分
	 */
	private DisplayAtr displayAtr;
	
	/**
	 * 既読日時
	 */
	private GeneralDateTime alreadyDatetime;
	
	/**
	 * [1] 既読日時を更新する
	 */
	public void updateAlreadyDatetime() {
		this.alreadyDatetime = GeneralDateTime.now();
	}
	
	public void changeToUnread(GeneralDateTime occurrenceDateTime) {
		GeneralDateTime unreadDateTime = occurrenceDateTime.addMinutes(-1);
		this.alreadyDatetime = unreadDateTime;
	}
	
}
