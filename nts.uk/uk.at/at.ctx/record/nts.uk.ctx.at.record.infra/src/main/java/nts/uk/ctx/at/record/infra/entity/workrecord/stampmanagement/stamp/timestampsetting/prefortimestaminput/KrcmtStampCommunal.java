package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.TimeStampSetShareTStamp;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *UKDesign.データベース.ER図.就業.勤務実績.<<core>> 勤務実績.打刻管理.打刻設定.打刻入力の前準備.KRCMT_STAMP_COMMUNAL
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KRCMT_STAMP_COMMUNAL")
public class KrcmtStampCommunal extends ContractUkJpaEntity implements Serializable {
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
	 * 氏名選択利用する
	 */
	@Basic(optional = false)
	@Column(name = "NAME_SELECT_ART")
	public boolean nameSelectArt;
	
	/**
	 * パスワード必須区分
	 */
	@Basic(optional = false)
	@Column(name = "PASSWORD_REQUIRED_ART")
	public boolean passwordRequiredArt;
	
	/**
	 * 社員コード認証利用するか
	 */
	@Basic(optional = false)
	@Column(name = "EMPLOYEE_AUTHC_USE_ART")
	public int employeeAuthcUseArt;
	
	/**
	 * 指認証失敗回数
	 */
	@Basic(optional = true)
	@Column(name = "AUTHC_FAIL_CNT")
	public int authcFailCnt;
	
	@Override
	protected Object getKey() {
		return this.cid;
	}

	public void update(TimeStampSetShareTStamp domain) {
		this.cid = domain.getCid();
		this.correctionInterval = domain.getDisplaySetStampScreen().getServerCorrectionInterval().v();
		this.resultDispayTime = domain.getDisplaySetStampScreen().getResultDisplayTime().v();
		this.textColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getTextColor().v();
		this.backGroundColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getBackgroundColor().v();
		this.nameSelectArt = domain.getUseSelectName();
		this.passwordRequiredArt = domain.getPasswordInputReq();
		this.employeeAuthcUseArt = domain.getUseEmpCodeToAuthen().value;
		this.authcFailCnt = domain.getNumberAuthenfailures().get().v();
		
	}

}
