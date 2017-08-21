package nts.uk.ctx.at.request.infra.entity.setting.request.application;

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
@Table(name = "KRQST_APPLICATION_SETTING")
public class KrqstApplicationSetting extends UkJpaEntity {

	@EmbeddedId
	public KrqstApplicationSettingPK krqstApplicationSettingPK;

	@Column(name = "PRE_POST_INIT_ATR")
	public int prePostInitAtr;

	@Column(name = "PRE_POST_CAN_CHANGE_FLG")
	public int prePostCanChangeFlg;

	@Column(name = "TYPICAL_REASON_DISPLAY_FLG")
	public int typicalReasonDisplayFlg;

	@Column(name = "SEND_MAIL_WHEN_APPROVAL_FLG")
	public int sendMailWhenApprovalFlg;
	
	@Column(name = "SEND_MAIL_WHEN_REGISTER_FLG")
	public int sendMailWhenRegisterlFlg;
	
	@Column(name = "DISPLAY_REASON_FLG")
	public int displayReasonFlg;

	@Column(name = "VACATION_APP_TYPE")
	public int vacationAppType;

	@Column(name = "APP_ACT_LOCK_FLG")
	public int appActLockFlg;

	@Column(name = "APP_END_WORK_FLG")
	public int appEndWorkFlg;

	@Column(name = "APP_ACT_CONFIRM_FLG")
	public int appActConfirmFlg;

	@Column(name = "APP_OVERTIME_NIGHT_FLG")
	public int appOvertimeNightFlg;

	@Column(name = "APP_ACT_MONTH_CONFIRM_FLG")
	public int appActMonthConfirmFlg;

	@Column(name = "REQUIRE_APP_REASON_FLG")
	public int requireAppReasonFlg;

	@Column(name = "RETRICT_PRE_METHOD_FLG")
	public int retrictPreMethodFlg;

	@Column(name = "RETRICT_PRE_USE_FLG")
	public int retrictPreUseFlg;

	@Column(name = "RETRICT_PRE_DAY")
	public int retrictPreDay;

	@Column(name = "RETRICT_PRE_TIMEDAY")
	public int retrictPreTimeDay;

	@Column(name = "RETRICT_PRE_CAN_ACCEPT_FLG")
	public int retrictPreCanAcceptFlg;

	@Column(name = "RETRICT_POST_ALLOW_FUTURE_FLG")
	public int retrictPostAllowFutureFlg;

	@Column(name = "DISPLAY_PRE_POST_FLG")
	public int displayPrePostFlg;

	@Column(name = "DISPLAY_SEARCH_TIME_FLG")
	public int displaySearchTimeFlg;

	@Column(name = "DISPLAY_INIT_DAY_FLG")
	public int displayInitDayFlg;

	@Override
	protected Object getKey() {
		return krqstApplicationSettingPK;
	}

}
