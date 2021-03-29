package nts.uk.ctx.at.request.infra.entity.reasonappdaily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.reasonappdaily.AppReasonMap;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQDT_APPLICATION_REASON")
public class KrqdtApplicationReason extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtApplicationReasonPK pk;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;

	/** 定型理由コード（事前早出残業申請） */
	@Column(name = "PRE_OVERTIME_EARLY_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT, fix = true, overType = OvertimeAppAtr.EARLY_OVERTIME)
	public Integer preOverEarlyFix;

	/** 申請理由（事前早出残業申請） */
	@Column(name = "PRE_OVERTIME_EARLY_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT,  overType = OvertimeAppAtr.EARLY_OVERTIME)
	public String preOverEarlyApp;

	/** 定型理由コード（事後早出残業申請） */
	@Column(name = "POST_OVERTIME_EARLY_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true, overType = OvertimeAppAtr.EARLY_OVERTIME)
	public Integer postOverEarlyFix;

	/** 申請理由（事後早出残業申請） */
	@Column(name = "POST_OVERTIME_EARLY_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR,  overType = OvertimeAppAtr.EARLY_OVERTIME)
	public String postOverEarlyApp;

	/** 定型理由コード（事前通常残業申請） */
	@Column(name = "PRE_OVERTIME_NORMAL_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT, fix = true, overType = OvertimeAppAtr.NORMAL_OVERTIME)
	public Integer preOverNormalFix;

	/** 申請理由（事前通常残業申請） */
	@Column(name = "PRE_OVERTIME_NORMAL_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT,  overType = OvertimeAppAtr.NORMAL_OVERTIME)
	public String preOverNormalApp;

	/** 定型理由コード（事後通常残業申請） */
	@Column(name = "POST_OVERTIME_NORMAL_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true, overType = OvertimeAppAtr.NORMAL_OVERTIME)
	public Integer postOverNormalFix;

	/** 申請理由（事後通常残業申請） */
	@Column(name = "POST_OVERTIME_NORMAL_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR,  overType = OvertimeAppAtr.NORMAL_OVERTIME)
	public String postOverNormalApp;

	/** 定型理由コード（事前早出・通常残業申請） */
	@Column(name = "PRE_OVERTIME_EARLY_NORMAL_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT, fix = true, overType = OvertimeAppAtr.EARLY_NORMAL_OVERTIME)
	public Integer preOverEarlyNormalFix;

	/** 申請理由（事前早出・通常残業申請） */
	@Column(name = "PRE_OVERTIME_EARLY_NORMAL_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.PREDICT,  overType = OvertimeAppAtr.EARLY_NORMAL_OVERTIME)
	public String preOverEarlyNormalApp;

	/** 定型理由コード（事後早出・通常残業申請） */
	@Column(name = "POST_OVERTIME_EARLY_NORMAL_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true, overType = OvertimeAppAtr.EARLY_NORMAL_OVERTIME)
	public Integer postOverEarlyNormalFix;

	/** 申請理由（事後早出・通常残業申請） */
	@Column(name = "POST_OVERTIME_EARLY_NORMAL_APP_REASON")
	@AppReasonMap(type = ApplicationType.OVER_TIME_APPLICATION, before = PrePostAtr.POSTERIOR,  overType = OvertimeAppAtr.EARLY_NORMAL_OVERTIME)
	public String postOverEarlyNormalApp;

	/** 定型理由コード（事前休暇申請）） */
	@Column(name = "PRE_HD_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.ABSENCE_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preHdFix;

	/** 申請理由（事前休暇申請） */
	@Column(name = "PRE_HD_APP_REASON")
	@AppReasonMap(type = ApplicationType.ABSENCE_APPLICATION, before = PrePostAtr.PREDICT)
	public String preHdApp;

	/** 定型理由コード（事後休暇申請） */
	@Column(name = "POST_HD_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.ABSENCE_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postHdFix;

	/** 申請理由（事後休暇申請） */
	@Column(name = "POST_HD_APP_REASON")
	@AppReasonMap(type = ApplicationType.ABSENCE_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postHdApp;

	/** 定型理由コード（事前勤務変更申請） */
	@AppReasonMap(type = ApplicationType.WORK_CHANGE_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	@Column(name = "PRE_WORK_CHANGE_FIXED_REASON")
	public Integer preWorkChangeFix;

	/** 申請理由（事前勤務変更申請） */
	@Column(name = "PRE_WORK_CHANGE_APP_REASON")
	@AppReasonMap(type = ApplicationType.WORK_CHANGE_APPLICATION, before = PrePostAtr.PREDICT)
	public String preWorkChangeApp;

	/** 定型理由コード（事後勤務変更申請） */
	@Column(name = "POST_WORK_CHANGE_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.WORK_CHANGE_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postWorkChangeFix;

	/** 申請理由（事後勤務変更申請） */
	@Column(name = "POST_WORK_CHANGE_APP_REASON")
	@AppReasonMap(type = ApplicationType.WORK_CHANGE_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postWorkChangeApp;

	/** 定型理由コード（事前出張申請）） */
	@Column(name = "PRE_TRIP_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.BUSINESS_TRIP_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preTripFix;

	/** 申請理由（事前出張申請） */
	@Column(name = "PRE_TRIP_APP_REASON")
	@AppReasonMap(type = ApplicationType.BUSINESS_TRIP_APPLICATION, before = PrePostAtr.PREDICT)
	public String preTripApp;

	/** 定型理由コード（事後出張申請） */
	@Column(name = "POST_TRIP_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.BUSINESS_TRIP_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postTripFix;

	/** 申請理由（事後出張申請） */
	@Column(name = "POST_TRIP_APP_REASON")
	@AppReasonMap(type = ApplicationType.BUSINESS_TRIP_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postTripApp;

	/** 定型理由コード（事前直行直帰申請） */
	@Column(name = "PRE_GOBACK_DRT_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preGobackDrtFix;

	/** 申請理由（事前直行直帰申請） */
	@Column(name = "PRE_GOBACK_DRT_APP_REASON")
	@AppReasonMap(type = ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, before = PrePostAtr.PREDICT)
	public String preGobackDrtApp;

	/** 定型理由コード（事後直行直帰申請） */
	@Column(name = "POST_GOBACK_DRT_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postGobackDrtFix;

	/** 申請理由（事後直行直帰申請） */
	@Column(name = "POST_GOBACK_DRT_APP_REASON")
	@AppReasonMap(type = ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postGobackDrtApp;

	/** 定型理由コード（事前休出時間申請） */
	@Column(name = "PRE_HD_WORK_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.HOLIDAY_WORK_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preHdWorkFix;

	/** 申請理由（事前休出時間申請） */
	@Column(name = "PRE_HD_WORK_APP_REASON")
	@AppReasonMap(type = ApplicationType.HOLIDAY_WORK_APPLICATION, before = PrePostAtr.PREDICT)
	public String preHdWorkApp;

	/** 定型理由コード（事後休出時間申請） */
	@Column(name = "POST_HD_WORK_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.HOLIDAY_WORK_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postHdWorkFix;

	/** 申請理由（事後休出時間申請） */
	@Column(name = "POST_HD_WORK_APP_REASON")
	@AppReasonMap(type = ApplicationType.HOLIDAY_WORK_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postHdWorkApp;

	/** 定型理由コード（事前打刻申請） */
	@Column(name = "PRE_STAMP_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.STAMP_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preStampFix;

	/** 申請理由（事前打刻申請） */
	@Column(name = "PRE_STAMP_APP_REASON")
	@AppReasonMap(type = ApplicationType.STAMP_APPLICATION, before = PrePostAtr.PREDICT)
	public String preStampApp;

	/** 定型理由コード（事後打刻申請） */
	@Column(name = "POST_STAMP_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.STAMP_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postStampFix;

	/** 申請理由（事後打刻申請） */
	@Column(name = "POST_STAMP_APP_REASON")
	@AppReasonMap(type = ApplicationType.STAMP_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postStampApp;

	/** 定型理由コード（事前時間休暇申請） */
	@Column(name = "PRE_TIME_HD_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.ANNUAL_HOLIDAY_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preTimeHdFix;

	/** 申請理由（事前時間休暇申請） */
	@Column(name = "PRE_TIME_HD_APP_REASON")
	@AppReasonMap(type = ApplicationType.ANNUAL_HOLIDAY_APPLICATION, before = PrePostAtr.PREDICT)
	public String preTimeHdApp;

	/** 定型理由コード（事後時間休暇申請） */
	@Column(name = "POST_TIME_HD_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.ANNUAL_HOLIDAY_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postTimeHdFix;

	/** 申請理由（事後時間休暇申請） */
	@Column(name = "POST_TIME_HD_APP_REASON")
	@AppReasonMap(type = ApplicationType.ANNUAL_HOLIDAY_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postTimeHdApp;

	/** 定型理由コード（事前遅刻早退取消申請） */
	@Column(name = "PRE_LATE_OR_LEAVE_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preLateLeavFix;

	/** 申請理由（事前遅刻早退取消申請） */
	@Column(name = "PRE_LATE_OR_LEAVE_APP_REASON")
	@AppReasonMap(type = ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, before = PrePostAtr.PREDICT)
	public String preLateLeavApp;

	/** 定型理由コード（事後遅刻早退取消申請） */
	@Column(name = "POST_LATE_OR_LEAVE_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postLateLeavFix;

	/** 申請理由（事後遅刻早退取消申請） */
	@Column(name = "POST_LATE_OR_LEAVE_APP_REASON")
	@AppReasonMap(type = ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postLateLeavApp;

	/** 定型理由コード（事前振休振出申請） */
	@Column(name = "PRE_HD_SUB_REC_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.COMPLEMENT_LEAVE_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preHdSubRecFix;

	/** 申請理由（事前振休振出申請） */
	@Column(name = "PRE_HD_SUB_REC_APP_REASON")
	@AppReasonMap(type = ApplicationType.COMPLEMENT_LEAVE_APPLICATION, before = PrePostAtr.PREDICT)
	public String preHdSubRecApp;

	/** 定型理由コード（事後振休振出申請） */
	@Column(name = "POST_HD_SUB_REC_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.COMPLEMENT_LEAVE_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postHdSubRecFix;

	/** 申請理由（事後振休振出申請） */
	@Column(name = "POST_HD_SUB_REC_APP_REASON")
	@AppReasonMap(type = ApplicationType.COMPLEMENT_LEAVE_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postHdSubRecApp;

	/** 定型理由コード（事前任意項目申請） */
	@Column(name = "PRE_ANYV_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OPTIONAL_ITEM_APPLICATION, before = PrePostAtr.PREDICT, fix = true)
	public Integer preAnyvFix;

	/** 申請理由（事前任意項目申請） */
	@Column(name = "PRE_ANYV_APP_REASON")
	@AppReasonMap(type = ApplicationType.OPTIONAL_ITEM_APPLICATION, before = PrePostAtr.PREDICT)
	public String preAnyvApp;

	/** 定型理由コード（事後任意項目申請） */
	@Column(name = "POST_ANYV_FIXED_REASON")
	@AppReasonMap(type = ApplicationType.OPTIONAL_ITEM_APPLICATION, before = PrePostAtr.POSTERIOR, fix = true)
	public Integer postAnyvFix;

	/** 申請理由（事後任意項目申請） */
	@Column(name = "POST_ANYV_APP_REASON")
	@AppReasonMap(type = ApplicationType.OPTIONAL_ITEM_APPLICATION, before = PrePostAtr.POSTERIOR)
	public String postAnyvApp;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
