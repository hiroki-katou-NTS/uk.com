package nts.uk.ctx.at.request.infra.entity.setting.requestofearch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_WP_APP_CONFIG")
public class KrqstWpAppConfig extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrqstWpAppConfigPK krqstWpAppConfigPK;
	/**
	 * 備考
	 */
	@Column(name = "MEMO")
	public String memo;
	/**
	 * 利用区分
	 */
	@Column(name = "USE_ATR")
	public int useAtr;
	/**
	 * 休出時間申請の事前必須設定
	 */
	@Column(name = "PREREQUISITE_FORPAUSE_FLG")
	public int prerequisiteForpauseFlg;
	/**
	 * 残業申請の事前必須設定
	 */
	@Column(name = "OT_APP_SETTING_FLG")
	public int otAppSettingFlg;
	/**
	 * 申請時の承認者の選択
	 */
	@Column(name = "SELECT_OF_APPROVERS_FLG")
	public int selectOfApproversFlg;
	/**
	 * 申請利用設定
	 */
	@Column(name = "APP_USE_SETTING_FLG")
	public int appUseSettingFlg;
	/**
	 * 残業申請休憩入力欄を表示する
	 */
	@Column(name = "OT_BREAK_INPUTFIELD_DIS_FLG")
	public int otBreakInputFieldDisFlg;
	/**
	 * 残業申請休憩時間を表示する
	 */
	@Column(name = "OT_BREAK_TIME_DISPLAY_FLG")
	public int otBreakTimeDisFlg;
	/**
	 * 残業申請出退勤時刻初期表示区分
	 */
	@Column(name = "OT_ATWORK_TIME_BEGIN_DIS_FLG")
	public int otAtworkTimeBeginDisFlg;
	/**
	 * 残業申請実績から外出を初期表示する
	 */
	@Column(name = "OT_GOOUT_TIME_BEGIN_DIS_FLG")
	public int otGoOutTimeBeginDisFlg;
	/**
	 * 残業申請時刻計算利用区分
	 */
	@Column(name = "OT_TIME_CAL_USE_ATR")
	public int otTimeCalUseAtr;
	/**
	 * 残業申請時間入力利用区分
	 */
	@Column(name = "OT_TIME_INPUT_USE_ATR")
	public int otTimeInputUseAtr;
	/**
	 * 休出申請休憩入力欄を表示する休出申請
	 */
	@Column(name = "HW_BREAK_INPUTFIELD_DIS_FLG")
	public int hwBreakInputFieldDisFlg;
	/**
	 * 休出申請休憩時間を表示する休出申請
	 */
	@Column(name = "HW_BREAK_TIME_DISPLAY_FLG")
	public int hwBreakTimeDisFlg;
	/**
	 * 休出申請出退勤時刻初期表示区分休出申請
	 */
	@Column(name = "HW_ATWORK_TIME_BEGIN_DIS_FLG")
	public int hwAtworkTimeBeginDisFlg;
	/**
	 * 休出申請実績から外出を初期表示する休出申請
	 */
	@Column(name = "HW_GOOUT_TIME_BEGIN_DIS_FLG")
	public int hwGoOutTimeBeginDisFlg;
	/**
	 * 休出申請時刻計算利用区分休出申請	
	 */
	@Column(name = "HW_TIME_CAL_USE_ATR")
	public int hwTimeCalUseAtr;
	/**
	 * 休出申請時間入力利用区分休出申請
	 */
	@Column(name = "HW_TIME_INPUT_USE_ATR")
	public int hwTimeInputUseAtr;
	@Override
	protected Object getKey() {
		return krqstWpAppConfigPK;
	}

}
