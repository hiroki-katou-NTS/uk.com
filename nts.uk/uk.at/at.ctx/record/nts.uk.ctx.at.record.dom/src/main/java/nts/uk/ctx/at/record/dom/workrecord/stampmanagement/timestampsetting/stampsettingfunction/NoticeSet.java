package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ColorSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * AR: お知らせメッセージ設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定
 * 
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class NoticeSet implements DomainAggregate {
	
	/** 会社ID */
	private final String cid;

	/** 会社メッセージ色 */
	private ColorSetting cmpMsgColor;

	/** 会社宛タイトル */
	private MessageTitle cmpTitle;

	/** 個人メッセージ色 */
	private ColorSetting personMsgColor;

	/** 職場メッセージ色 */
	private ColorSetting wkpMsgColor;

	/** 職場宛タイトル */
	private MessageTitle wkpTitle;

	/** 表示区分 */
	private NotUseAtr displayAtr;
	
}
