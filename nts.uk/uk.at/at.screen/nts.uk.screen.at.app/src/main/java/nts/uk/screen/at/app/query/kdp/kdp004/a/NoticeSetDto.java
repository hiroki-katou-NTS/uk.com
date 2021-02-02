package nts.uk.screen.at.app.query.kdp.kdp004.a;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.stamp.management.ColorSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.NoticeSet;

/**
 * お知らせメッセージ設定
 * 
 * @author tutt
 *
 */
@Data
public class NoticeSetDto {
	
	/** 会社ID */
	private String cid;

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
		this.cid = domain.getCid();
		this.comMsgColor = new ColorSettingDto(domain.getCmpMsgColor().getTextColor().v(),
				domain.getCmpMsgColor().getBackGroundColor().v());
		this.companyTitle = domain.getCmpTitle().v();
		this.personMsgColor = new ColorSettingDto(domain.getPersonMsgColor().getTextColor().v(),
				domain.getPersonMsgColor().getBackGroundColor().v());
		this.wkpMsgColor = new ColorSettingDto(domain.getWkpMsgColor().getTextColor().v(),
				domain.getWkpMsgColor().getBackGroundColor().v());
		this.wkpTitle = domain.getWkpTitle().v();
		this.displayAtr = domain.getDisplayAtr().value;
	}

}
