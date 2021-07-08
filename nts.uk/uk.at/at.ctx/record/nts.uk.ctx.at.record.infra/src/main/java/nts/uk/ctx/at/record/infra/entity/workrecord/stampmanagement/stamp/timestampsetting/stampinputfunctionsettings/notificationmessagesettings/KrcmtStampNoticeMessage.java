package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.ColorSetting;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.MessageTitle;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author ThanhPV
 * @name お知らせメッセージ設定
 */

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_NOTICE_MESSAGE")
public class KrcmtStampNoticeMessage extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String companyId;

	//表示区分
	@Column(name = "DISPLAY_ATR")
	public int displayAtr;

	//会社宛タイトル
	@Column(name = "CMP_TITLE")
	public String cmpTitle;

	//会社メッセージ色.文字色
	@Column(name = "CMP_LETTER_COLOR")
	public String cmpLetterColor;

	//会社メッセージ色.背景色
	@Column(name = "CMP_BACKGROUND_COLOR")
	public String cmpBackgroundColor;
	
	//職場宛タイトル
	@Column(name = "WKP_TITLE")
	public String wkpTitle;

	//職場メッセージ色.文字色
	@Column(name = "WKP_LETTER_COLOR")
	public String wkpLetterColor;

	//職場メッセージ色.背景色
	@Column(name = "WKP_BACKGROUND_COLOR")
	public String wkpBackgroundColor;
	
	//個人メッセージ色.文字色
	@Column(name = "PS_LETTER_COLOR")
	public String psLetterColor;

	//個人メッセージ色.背景色
	@Column(name = "PS_BACKGROUND_COLOR")
	public String psBackgroundColor;

	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	@PreUpdate
    private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}
	
	public KrcmtStampNoticeMessage(NoticeSet domain) {
		this.companyId = AppContexts.user().companyId();
		this.displayAtr = domain.getDisplayAtr().value;
		this.cmpTitle = domain.getCompanyTitle().v();
		this.cmpLetterColor = domain.getCompanyColor().getTextColor().v();
		this.cmpBackgroundColor = domain.getCompanyColor().getBackGroundColor().v();
		this.wkpTitle = domain.getWorkplaceTitle().v();
		this.wkpLetterColor = domain.getWorkplaceColor().getTextColor().v();
		this.wkpBackgroundColor = domain.getWorkplaceColor().getBackGroundColor().v();
		this.psLetterColor = domain.getPersonalColor().getTextColor().v();
		this.psBackgroundColor = domain.getPersonalColor().getBackGroundColor().v();
	}
	
	public NoticeSet toDomain() {
		return new NoticeSet(
				new ColorSetting(new ColorCode(this.cmpLetterColor), new ColorCode(this.cmpBackgroundColor)), 
				new MessageTitle(this.cmpTitle), 
				new ColorSetting(new ColorCode(this.psLetterColor), new ColorCode(this.psBackgroundColor)), 
				new ColorSetting(new ColorCode(this.wkpLetterColor), new ColorCode(this.wkpBackgroundColor)), 
				new MessageTitle(this.wkpTitle), 
				DoWork.valueOf(this.displayAtr)
			);
	}

}
