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

	@Column(name = "DISPLAY_PRE_POST_FLG")
	public int displayPrePostFlg;

	@Column(name = "DISPLAY_SEARCH_TIME_FLG")
	public int displaySearchTimeFlg;

	@Column(name = "DISPLAY_INIT_DAY_FLG")
	public int displayInitDayFlg;

	/** 承認 */

	@Column(name = "ADVANCE_EXCESS_MESS_DISP_ATR")
	public int advanceExcessMessDispAtr;

	@Column(name = "HW_ADVANCE_DISP_ATR")
	public int hwAdvanceDispAtr;

	@Column(name = "HW_ACTUAL_DISP_ATR")
	public int hwActualDispAtr;

	@Column(name = "ACTUAL_EXCESS_MESS_DISP_ATR")
	public int actualExcessMessDispAtr;

	@Column(name = "OT_ADVANCE_DISP_ATR")
	public int otAdvanceDispAtr;

	@Column(name = "OT_ACTUAL_DISP_ATR")
	public int otActualDispAtr;

	@Column(name = "WARNING_DATE_DISP_ATR")
	public int warningDateDispAtr;

	@Column(name = "APP_REASON_DISP_ATR")
	public int appReasonDispAtr;

	@Column(name = "APP_CONTENT_CHANGE_FLG")
	public int appContentChangeFlg;

	@Column(name = "PERSON_APPROVAL_FLG")
	public int personApprovalFlg;

	@Column(name = "SCHE_REFLECT_FLG")
	public int scheReflectFlg;

	@Column(name = "PRIORITY_TIME_REFLECT_FLG")
	public int priorityTimeReflectFlg;

	@Column(name = "ATTENDENT_TIME_REFLECT_FLG")
	public int attendentTimeReflectFlg;

	@Override
	protected Object getKey() {
		return krqstApplicationSettingPK;
	}

}
