package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.stampsettingfunction;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ColorSetting;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.MessageTitle;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.NoticeSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * UKDesign.データベース.ER図.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定.KRCMT_STAMP_NOTICE_MESSAGE
 * 
 * @author tutt
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_STAMP_NOTICE_MESSAGE")
public class KrcmtStampNoticeMessage extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/** 表示区分 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_ATR")
	public int displayAtr;

	/** 会社宛タイトル */
	@Basic(optional = false)
	@Column(name = "CMP_TITLE")
	public String cmpTitle;

	/** 会社メッセージ文字色 */
	@Basic(optional = false)
	@Column(name = "CMP_LETTER_COLOR")
	public String cmpTextColor;

	/** 会社メッセージ背景色 */
	@Basic(optional = false)
	@Column(name = "CMP_BACKGROUND_COLOR")
	public String cmpBackgroundColor;

	/** 職場宛タイトル */
	@Basic(optional = false)
	@Column(name = "WKP_TITLE")
	public String wkpTitle;

	/** 職場メッセージ文字色 */
	@Basic(optional = false)
	@Column(name = "WKP_LETTER_COLOR")
	public String wkpTextColor;

	/** 職場メッセージ文字色 */
	@Basic(optional = false)
	@Column(name = "WKP_BACKGROUND_COLOR")
	public String wkpBackgroundColor;

	/** 個人メッセージ文字色 */
	@Basic(optional = false)
	@Column(name = "PS_LETTER_COLOR")
	public String personTextColor;

	/** 個人メッセージ背景色 */
	@Basic(optional = false)
	@Column(name = "PS_BACKGROUND_COLOR")
	public String personBackgroundColor;

	public static KrcmtStampNoticeMessage toEntity(NoticeSet domain) {
		return new KrcmtStampNoticeMessage(
				domain.getCid(), 
				domain.getDisplayAtr().value, 
				domain.getCmpTitle().v(),
				domain.getCmpMsgColor().getTextColor().v(), domain.getCmpMsgColor().getBackGroundColor().v(),
				domain.getWkpTitle().v(), domain.getWkpMsgColor().getTextColor().v(),
				domain.getWkpMsgColor().getBackGroundColor().v(), domain.getPersonMsgColor().getTextColor().v(),
				domain.getPersonMsgColor().getBackGroundColor().v());
	}

	public NoticeSet toDomain() {
		return new NoticeSet(this.cid,
				new ColorSetting(new ColorCode(this.cmpTextColor), new ColorCode(this.cmpBackgroundColor)),
				new MessageTitle(this.cmpTitle),
				new ColorSetting(new ColorCode(this.personTextColor), new ColorCode(this.personBackgroundColor)),
				new ColorSetting(new ColorCode(this.wkpTextColor), new ColorCode(this.wkpBackgroundColor)),
				new MessageTitle(this.wkpTitle), 
				NotUseAtr.valueOf(this.displayAtr)
				);
	}

	@Override
	protected Object getKey() {
		return this.cid;
	}

	@PreUpdate
	private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}

}
