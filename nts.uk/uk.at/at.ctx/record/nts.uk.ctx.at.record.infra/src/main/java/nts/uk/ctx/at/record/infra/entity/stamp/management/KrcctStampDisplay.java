package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ColorSetting;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.HistoryDisplayMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampingScreenSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 打刻画面の表示設定
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_DISPLAY")
public class KrcctStampDisplay extends ContractUkJpaEntity{

	@EmbeddedId
    public KrcctStampDisplayPk pk;
	
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
	
	/** 背景色 */
	@Column(name ="BACK_GROUND_COLOR")
	public String backGroundColor;
	
	/** 出退勤ボタンを強調する 0:利用しない 1:利用する */
	@Column(name ="BUTTON_EMPHASIS_ART")
	public boolean buttonEmphasisArt;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public KrcctStampDisplay(KrcctStampDisplayPk pk, int correctionInterval, int histDisplayMethod,
			int resultDisplayTime, String textColor, String backGroundColor, boolean buttonEmphasisArt) {
		super();
		this.pk = pk;
		this.correctionInterval = correctionInterval;
		this.histDisplayMethod = histDisplayMethod;
		this.resultDisplayTime = resultDisplayTime;
		this.textColor = textColor;
		this.backGroundColor = backGroundColor;
		this.buttonEmphasisArt = buttonEmphasisArt;
	}
	
	public StampSettingPerson toDomain(){
		return new StampSettingPerson(
				pk.companyId, 
				buttonEmphasisArt, 
				new StampingScreenSet(
						EnumAdaptor.valueOf(this.histDisplayMethod, HistoryDisplayMethod.class), 
						new CorrectionInterval(this.correctionInterval), 
						new ColorSetting(
								new ColorCode(this.textColor), 
								new ColorCode(this.backGroundColor)), 
						new ResultDisplayTime(this.resultDisplayTime))
				,Collections.emptyList()
				,null);
	}
	
	//TODO: Chungnt
	public StampSettingPerson toDomain(List<StampPageLayout> layouts){
		return new StampSettingPerson(
				pk.companyId, 
				buttonEmphasisArt, 
				new StampingScreenSet(
						EnumAdaptor.valueOf(this.histDisplayMethod, HistoryDisplayMethod.class), 
						new CorrectionInterval(this.correctionInterval), 
						new ColorSetting(
								new ColorCode(this.textColor), 
								new ColorCode(this.backGroundColor)), 
						new ResultDisplayTime(this.resultDisplayTime)),
				layouts, null);
	}
	
	public static KrcctStampDisplay toEntity(StampSettingPerson person){
		return new KrcctStampDisplay(new KrcctStampDisplayPk(
				person.getCompanyId(), 
				1), 
				person.getStampingScreenSet().getCorrectionInterval().v(), 
				person.getStampingScreenSet().getHistoryDisplayMethod().value, 
				person.getStampingScreenSet().getResultDisplayTime().v(), 
				person.getStampingScreenSet().getColorSetting().getTextColor().v(), 
				person.getStampingScreenSet().getColorSetting().getBackGroundColor().v(), 
				person.isButtonEmphasisArt());
	}
}