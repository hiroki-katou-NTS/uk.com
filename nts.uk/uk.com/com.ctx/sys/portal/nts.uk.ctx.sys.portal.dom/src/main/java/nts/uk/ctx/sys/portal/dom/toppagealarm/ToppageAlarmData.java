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
	 * 識別キー
	 */
	private IdentificationKey identificationKey;
	
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
	
	public void updateIsResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}
	
	ToppageAlarmData() {
		this.displaySId = "";
		this.displayAtr = DisplayAtr.PIC;
	}
}
