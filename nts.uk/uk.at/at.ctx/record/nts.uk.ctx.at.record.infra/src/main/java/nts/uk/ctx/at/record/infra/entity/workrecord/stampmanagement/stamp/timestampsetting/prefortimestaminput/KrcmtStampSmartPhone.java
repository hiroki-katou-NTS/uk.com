package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Customizer;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author Laitv
 * スマホ打刻の打刻設定
 */
@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_SMART_PHONE")
@Customizer(KrcmtStampSmartPhoneCustomizer.class)
public class KrcmtStampSmartPhone extends ContractUkJpaEntity implements Serializable {
	 
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID									
	 */
	@Id
	@Column(name = "CID")
	public String cid;

	/**
	 * 打刻画面のサーバー時刻補正間隔									
	 */
	@Basic(optional = false)
	@Column(name = "CORRECTION_INTERVAL")
	public Integer correctionInterval;
	
	/**
	 * 	打刻結果自動閉じる時間									
	 */
	@Basic(optional = false)
	@Column(name = "RESULT_DISPLAY_TIME")
	public Integer resultDisplayTime;
	
	/**
	 * 	文字色									
	 */
	@Basic(optional = false)
	@Column(name = "TEXT_COLOR")
	public String textColor;
	
	/**	 
	 * 出退勤ボタンを強調する  0:利用しない  1:利用する									
	 */
	@Basic(optional = false)
	@Column(name = "BUTTON_EMPHASIS_ART")
	public Boolean buttonEmphasisArt;
	
	/**
	 * 	位置情報を利用する
	 */
	@Column(name = "LOCATION_INFO_USE")
	public Integer locationInfoUse;
	
	/**
	 * 	打刻エリア制限する							
	 */
	@Column(name = "AREA_LIMIT_ATR")
	public Integer areaLimitAtr;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "krcmtStampSmartPhone", orphanRemoval = true)
	public List<KrcmtStampPageLayout> listKrcmtStampPageLayout;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	@PreUpdate
    private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}
	
	public KrcmtStampSmartPhone(SettingsSmartphoneStamp domain) {
		this.cid = domain.getCid();
		this.correctionInterval = domain.getDisplaySettingsStampScreen().getCorrectionInterval().v();
		this.resultDisplayTime = domain.getDisplaySettingsStampScreen().getResultDisplayTime().v();
		this.textColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getTextColor().v();
		this.buttonEmphasisArt = domain.isButtonEmphasisArt();
		this.listKrcmtStampPageLayout = domain.getPageLayoutSettings().stream().map(c->KrcmtStampPageLayout.toEntity(c, domain.getCid(), 3)).collect(Collectors.toList());
	}
	
	public SettingsSmartphoneStamp toDomain() {
		return new SettingsSmartphoneStamp(this.cid,
				new DisplaySettingsStampScreen(new CorrectionInterval(this.correctionInterval),
						new SettingDateTimeColorOfStampScreen(new ColorCode(this.textColor)),
						new ResultDisplayTime(this.resultDisplayTime)),
				this.listKrcmtStampPageLayout.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
				this.buttonEmphasisArt, null);
	}
}
