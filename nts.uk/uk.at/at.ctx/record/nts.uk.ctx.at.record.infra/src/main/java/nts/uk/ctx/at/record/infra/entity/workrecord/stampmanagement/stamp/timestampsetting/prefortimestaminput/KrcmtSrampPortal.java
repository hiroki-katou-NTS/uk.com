package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Customizer;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 * UKDesign.データベース.ER図.就業.勤務実績.<<core>> 勤務実績.打刻管理.打刻設定.打刻入力の前準備.KRCMT_STAMP_PORTAL
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KRCMT_STAMP_PORTAL")
@Customizer(KrcmtSrampPortalCustomizer.class)
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
	public int resultDisplayTime;
	
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
	
	@OneToMany(mappedBy = "krcmtSrampPortal", cascade = CascadeType.ALL)
	public List<KrcmtStampLayoutDetail> krcmtStampLayoutDetail;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public static KrcmtSrampPortal toEntity(PortalStampSettings domain){
		return new KrcmtSrampPortal(
				domain.getCid(), 
				domain.getDisplaySettingsStampScreen().getCorrectionInterval().v(), 
				domain.getDisplaySettingsStampScreen().getResultDisplayTime().v(), 
				domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getTextColor().v(), 
				domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getBackGroundColor().v(), 
				domain.isButtonEmphasisArt() ? 1 : 0, 
				domain.isToppageLinkArt() ? 1 : 0, 
				domain.getButtonSettings().stream().map(c-> KrcmtStampLayoutDetail.toEntity(c, domain.getCid(), 1/*confirm with amid-mizutani, Vu Tuan is 1*/,4)).collect(Collectors.toList()));
	}
	
	public PortalStampSettings toDomain(){
		return new PortalStampSettings(
				this.cid, 
				new DisplaySettingsStampScreen(
						new CorrectionInterval(this.correctionInterval), 
						new SettingDateTimeColorOfStampScreen(
							new ColorCode(this.textColor),
							new ColorCode(this.backGroundColor)),
						new ResultDisplayTime(this.resultDisplayTime)), 
				this.krcmtStampLayoutDetail.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				this.buttonEmphasisArt == 1, 
				this.toppageLinkArt == 1);
	}

	public KrcmtSrampPortal(String cid, int correctionInterval, int resultDisplayTime, String textColor,
			String backGroundColor, int buttonEmphasisArt, int toppageLinkArt,
			List<KrcmtStampLayoutDetail> krcmtStampLayoutDetail) {
		super();
		this.cid = cid;
		this.correctionInterval = correctionInterval;
		this.resultDisplayTime = resultDisplayTime;
		this.textColor = textColor;
		this.backGroundColor = backGroundColor;
		this.buttonEmphasisArt = buttonEmphasisArt;
		this.toppageLinkArt = toppageLinkArt;
		this.krcmtStampLayoutDetail = krcmtStampLayoutDetail;
	}
	
}
