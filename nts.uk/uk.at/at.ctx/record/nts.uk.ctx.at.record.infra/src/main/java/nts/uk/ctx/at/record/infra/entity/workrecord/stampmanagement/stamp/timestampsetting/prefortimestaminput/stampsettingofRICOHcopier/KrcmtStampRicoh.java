package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.PasswordForRICOH;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampPageLayout;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author ThanhPV
 * @name RICOH複写機の打刻設定
 */

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_RICOH")
@Customizer(KrcmtStampRicohCustomizer.class)
public class KrcmtStampRicoh extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;

	/**
	 * 打刻画面の表示設定.打刻画面のサーバー時刻補正間隔
	 */
	@Column(name = "CORRECTION_INTERVAL")
	public int correctionInterval;

	/**
	 * 打刻画面の表示設定.打刻結果自動閉じる時間
	 */
	@Column(name = "RESULT_DISPLAY_TIME")
	public int resultDisplayTime;

	/**
	 * 打刻画面の表示設定.文字色
	 */
	@Column(name = "TEXT_COLOR")
	public String textColor;

	/**
	 * ICカード登録パスワード
	 */
	@Column(name = "IC_CARD_PASSWORD")
	public String icCardPassword;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "krcmtStampRicoh", orphanRemoval = true)
	public List<KrcmtStampPageLayout> listKrcmtStampPageLayout;

	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	@PreUpdate
    private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}
	
	public KrcmtStampRicoh(StampSettingOfRICOHCopier domain) {
		this.companyId = domain.getCid();
		this.correctionInterval = domain.getDisplaySettingsStampScreen().getCorrectionInterval().v();
		this.resultDisplayTime = domain.getDisplaySettingsStampScreen().getResultDisplayTime().v();
		this.textColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getTextColor().v();
		this.icCardPassword = domain.getIcCardPassword().v();
		this.listKrcmtStampPageLayout = domain.getPageLayoutSettings().stream().map(c->KrcmtStampPageLayout.toEntity(c, domain.getCid(), 5)).collect(Collectors.toList());
	}
	
	public StampSettingOfRICOHCopier toDomain() {
		return new StampSettingOfRICOHCopier(
				this.companyId, 
				new PasswordForRICOH(this.icCardPassword), 
				this.listKrcmtStampPageLayout.stream().map(c->c.toDomain()).collect(Collectors.toList()),
				new DisplaySettingsStampScreen(
					new CorrectionInterval(this.correctionInterval), 
					new SettingDateTimeColorOfStampScreen(
						new ColorCode(this.textColor)
					), 
					new ResultDisplayTime(this.resultDisplayTime)
				)
			);
	}

}
