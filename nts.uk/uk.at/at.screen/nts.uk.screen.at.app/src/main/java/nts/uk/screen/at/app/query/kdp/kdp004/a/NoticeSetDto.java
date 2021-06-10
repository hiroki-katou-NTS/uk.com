package nts.uk.screen.at.app.query.kdp.kdp004.a;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.stamp.management.ColorSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;

/**
 * お知らせメッセージ設定
 * 
 * @author tutt
 *
 */
@Data
public class NoticeSetDto {
	
	/** 会社メッセージ色 */
	private ColorSettingDto comMsgColor;

	/** 会社宛タイトル */
	private String companyTitle;

	/** 個人メッセージ色 */
	private ColorSettingDto personMsgColor;

	/** 職場メッセージ色 */
	private ColorSettingDto wkpMsgColor;

	/** 職場宛タイトル */
	private String wkpTitle;

	/** 表示区分 */
	private int displayAtr;

	public NoticeSetDto(NoticeSet domain) {
		this.comMsgColor = new ColorSettingDto(domain.getCompanyColor().getTextColor().v(),
				domain.getCompanyColor().getBackGroundColor().v());
		this.companyTitle = domain.getCompanyTitle().v();
		this.personMsgColor = new ColorSettingDto(domain.getPersonalColor().getTextColor().v(),
				domain.getPersonalColor().getBackGroundColor().v());
		this.wkpMsgColor = new ColorSettingDto(domain.getWorkplaceColor().getTextColor().v(),
				domain.getWorkplaceColor().getBackGroundColor().v());
		this.wkpTitle = domain.getWorkplaceTitle().v();
		this.displayAtr = domain.getDisplayAtr().value;
	}

}
