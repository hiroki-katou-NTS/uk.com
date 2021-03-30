package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.HistoryDisplayMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 打刻画面の表示設定
 * update thành 個人利用の打刻設定
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCMT_STAMP_PERSON")
public class KrcmtStampPerson extends ContractUkJpaEntity{

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	@Column(name ="CORRECTION_INTERVAL")
	public int correctionInterval;
	
	/** 打刻履歴表示方法 0:表示しない 1:打刻一覧を表示 2:タイムカードを表示 */
	@Column(name ="HIST_DISPLAY_METHOD")
	public int histDisplayMethod;
	
	/** 打刻結果自動閉じる時間 */
	@Column(name ="RESULT_DISPLAY_TIME")
	public int resultDisplayTime;
	
	/** 文字色 */
	@Column(name ="TEXT_COLOR")
	public String textColor;
	
	/** 出退勤ボタンを強調する 0:利用しない 1:利用する */
	@Column(name ="BUTTON_EMPHASIS_ART")
	public boolean buttonEmphasisArt;
	
	
	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	public KrcmtStampPerson(String companyId, int correctionInterval, int histDisplayMethod,
			int resultDisplayTime, String textColor, boolean buttonEmphasisArt) {
		super();
		this.companyId = companyId;
		this.correctionInterval = correctionInterval;
		this.histDisplayMethod = histDisplayMethod;
		this.resultDisplayTime = resultDisplayTime;
		this.textColor = textColor;
		this.buttonEmphasisArt = buttonEmphasisArt;
	}
	
	public StampSettingPerson toDomain(){
		return new StampSettingPerson(
				companyId, 
				buttonEmphasisArt, 
				new DisplaySettingsStampScreen(
						new CorrectionInterval(this.correctionInterval), 
						new SettingDateTimeColorOfStampScreen(
								new ColorCode(this.textColor)), 
						new ResultDisplayTime(this.resultDisplayTime))
				,Collections.emptyList()
				,EnumAdaptor.valueOf(this.histDisplayMethod, HistoryDisplayMethod.class));
	}
	
	//TODO: Chungnt
	public StampSettingPerson toDomain(List<StampPageLayout> layouts){
		return new StampSettingPerson(
				companyId, 
				buttonEmphasisArt, 
				new DisplaySettingsStampScreen(
						new CorrectionInterval(this.correctionInterval), 
						new SettingDateTimeColorOfStampScreen(
								new ColorCode(this.textColor)), 
						new ResultDisplayTime(this.resultDisplayTime)),
				layouts, 
				EnumAdaptor.valueOf(this.histDisplayMethod, HistoryDisplayMethod.class));
	}
	
	public static KrcmtStampPerson toEntity(StampSettingPerson person){
		return new KrcmtStampPerson(
				person.getCompanyId(), 
				person.getStampingScreenSet().getCorrectionInterval().v(), 
				person.getHistoryDisplayMethod().value, 
				person.getStampingScreenSet().getResultDisplayTime().v(), 
				person.getStampingScreenSet().getSettingDateTimeColor().getTextColor().v(), 
				person.isButtonEmphasisArt());
	}
}