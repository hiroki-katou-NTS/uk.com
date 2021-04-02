package nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata;

import java.util.Optional;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmListPatternCode;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayMessage;
import nts.uk.ctx.sys.portal.dom.toppagealarm.LinkURL;

@Data
public class ToppageAlarmParam {
	/**
	 * アラーム分類
	 */
	private AlarmClassification alarmClassification;

	/**
	 * 発生日時
	 */
	private GeneralDateTime occurrenceDateTime;

	/**
	 * 表示社員ID
	 */
	private String displaySId;

	/**
	 * 表示社員区分
	 */
	private DisplayAtr displayEmpClassfication;

	/*
	 * パターンコード
	 */
	private Optional<AlarmListPatternCode> patternCode;

	/**
	 * 表示メッセージ
	 */
	private Optional<DisplayMessage> displayMessage;

	/**
	 * リンクURL
	 */
	private Optional<LinkURL> linkUrl;

	/*
	 * パターン名
	 */
	private Optional<String> patternName;
}
