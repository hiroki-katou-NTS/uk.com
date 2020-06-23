package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 * UKDesign.データベース.ER図.就業.勤務実績.<<core>> 勤務実績.打刻管理.打刻設定.打刻入力の前準備.KRCMT_STAMP_PORTAL
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KRCMT_STAMP_PORTAL")
public class KrcmtSrampPortal extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID									
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 打刻画面のサーバー時刻補正間隔
	 */
	@Basic(optional = false)
	@Column(name = "CORRECTION_INTERVAL")
	public int correctionInterval;
	
	/**
	 * 打刻結果自動閉じる時間
	 */
	@Basic(optional = false)
	@Column(name = "RESULT_DISPLAY_TIME")
	public int resultDispayTime;
	
	/**
	 * 文字色
	 */
	@Basic(optional = false)
	@Column(name = "TEXT_COLOR")
	public String textColor;
	
	/**
	 * 背景色
	 */
	@Basic(optional = false)
	@Column(name = "BACK_GROUND_COLOR")
	public String backGroundColor;
	
	/**
	 * 出退勤ボタンを強調する
	 */
	@Basic(optional = false)
	@Column(name = "BUTTON_EMPHASIS_ART")
	public int buttonEmphasisArt;
	
	/**
	 * トップメニューリンク利用する
	 */
	@Basic(optional = false)
	@Column(name = "TOPPAGE_LINK_ART")
	public int toppageLinkArt;
	
	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public void update(PortalStampSettings domain) {
		this.correctionInterval = domain.getDisplaySettingsStampScreen().getCorrectionInterval().v();
		this.resultDispayTime = domain.getDisplaySettingsStampScreen().getResultDisplayTime().v();
		this.textColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getTextColor().v();
		this.backGroundColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getBackGroundColor().v();
		this.buttonEmphasisArt = domain.getSuppressStampBtn() ? 1 : 0;
		this.toppageLinkArt = domain.getUseTopMenuLink() ? 1 : 0;
	}

}
