package nts.uk.ctx.at.request.infra.entity.setting.requestofearch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

public class KrqstWpAppConfigDetail  extends UkJpaEntity implements Serializable{
	/*主キー*/
	@EmbeddedId
	public KrqstWpAppConfigDetailPK krqstWpAppConfigDetailPK;
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
	 * 休憩入力欄を表示する
	 */
	@Column(name = "BREAK_INPUTFIELD_DIS_FLG")
	public int breakInputFieldDisFlg;
	/**
	 * 休憩時間を表示する
	 */
	@Column(name = "BREAK_TIME_DISPLAY_FLG")
	public int breakTimeDisFlg;
	/**
	 * 出退勤時刻初期表示区分
	 */
	@Column(name = "ATWORK_TIME_BEGIN_DIS_FLG")
	public int atworkTimeBeginDisFlg;
	/**
	 * 実績から外出を初期表示する
	 */
	@Column(name = "GOOUT_TIME_BEGIN_DIS_FLG")
	public int goOutTimeBeginDisFlg;
	/**
	 * 時刻計算利用区分
	 */
	@Column(name = "TIME_CAL_USE_ATR")
	public int timeCalUseAtr;
	/**
	 * 時間入力利用区分
	 */
	@Column(name = "TIME_INPUT_USE_ATR")
	public int timeInputUseAtr;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		return krqstWpAppConfigDetailPK;
	}

}
