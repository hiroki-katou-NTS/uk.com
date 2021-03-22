package nts.uk.ctx.sys.portal.dom.toppagealarm;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページアラーム（ver4～）.トップページアラーム
 * AR_トップページアラームデータ
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ToppageAlarmData extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * アラーム分類
	 */
	private AlarmClassification alarmClassification;
	
	
	/**
	 * 表示社員ID
	 */
	@Default
	private String displaySId = "";
	
	/**
	 * 表示社員区分
	 */
	@Default
	private DisplayAtr displayAtr = DisplayAtr.PIC;
	
	/**
	 * 解消済である
	 */
	private Boolean isResolved;
	
	/**
	 * 発生日時
	 */
	@Setter
	private GeneralDateTime occurrenceDateTime;
	
	/**
	 * 表示メッセージ
	 */
	private DisplayMessage displayMessage;

	/**
	 * リンクURL
	 */
	private Optional<LinkURL> linkUrl;
	
	//既読日時
	private Optional<GeneralDateTime> readDateTime;
	
	//パターンコード
	private Optional<AlarmListPatternCode> patternCode;
	
	//通知ID
	private Optional<NotificationId> notificationId;
	
	/**
	 * [1] 解消済みに状態を変更する																									
	 */
	public void changeResolvedStatus() {
		this.isResolved = true;
	}
	
	/**
	 * [2] 発生日時を更新する
	 */
	public void updateOccurrenceDateTime(GeneralDateTime dateTime) {
		this.isResolved = false;
		this.occurrenceDateTime = dateTime;
	}
}
