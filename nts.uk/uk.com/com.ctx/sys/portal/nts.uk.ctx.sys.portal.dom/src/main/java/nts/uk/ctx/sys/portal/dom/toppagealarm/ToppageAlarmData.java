package nts.uk.ctx.sys.portal.dom.toppagealarm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページアラーム（ver4～）.トップページアラーム
 * AR_トップページアラームデータ
 */
@Builder
@AllArgsConstructor
@Getter
public class ToppageAlarmData extends AggregateRoot {

	/**
	 * 会社ID
	 */
	@NonNull
	private String cid;
	
	/**
	 * アラーム分類
	 */
	@NonNull
	private AlarmClassification alarmClassification;
	
	/**
	 * 識別キー
	 */
	@NonNull
	private IdentificationKey identificationKey;
	
	/**
	 * 表示社員ID
	 */
	@NonNull
	private String displaySId;
	
	/**
	 * 表示社員区分
	 */
	@NonNull
	private DisplayAtr displayAtr;
	
	/**
	 * 解消済である
	 */
	private Boolean isResolved;
	
	/**
	 * 発生日時
	 */
	@NonNull
	private GeneralDateTime occurrenceDateTime;
	
	/**
	 * 表示メッセージ
	 */
	@NonNull
	private DisplayMessage displayMessage;

	/**
	 * リンクURL
	 */
	@NonNull
	private Optional<LinkURL> linkUrl;
	
	@SuppressWarnings("unused")
	private ToppageAlarmData() {}
	
	public void updateIsResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}
}
