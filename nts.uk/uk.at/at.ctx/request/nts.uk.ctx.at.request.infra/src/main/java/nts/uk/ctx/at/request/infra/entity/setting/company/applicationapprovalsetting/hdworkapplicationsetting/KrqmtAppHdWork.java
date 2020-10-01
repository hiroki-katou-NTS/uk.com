package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.残業申請設定
 */
@Entity
@Table(name = "KRQMT_APP_HD_WORK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppHdWork extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "STAMP_MISS_CAL_ATR")
    private int stampMissCalAtr;

    @Column(name = "GO_BACK_DIRECTLY_ATR")
    private int goBackDirectlyAtr;


    @Column(name = "PRE_EXCESS_ATR")
    private int preExcessAtr;

    @Column(name = "EXTRATIME_EXCESS_ATR")
    private int extraTimeExcessAtr;

    @Column(name = "EXTRATIME_DISPLAY_ATR")
    private int extraTimeDisplayAtr;

    @Column(name = "ATD_EXCESS_ATR")
    private int atdExcessAtr;

    @Column(name = "ATD_EXCESS_OVERRIDE_ATR")
    private int atdExcessOverrideAtr;

    @Column(name = "INSTRUCT_EXCESS_ATR")
    private int instructExcessAtr;

    @Column(name = "DVGC_EXCESS_ATR")
    private int dvgcExcessAtr;

    @Column(name = "INSTRUCT_REQUIRED_ATR")
    private int instructRequiredAtr;

    @Column(name = "PRE_REQUIRED_ATR")
    private int preRequiredAtr;

    @Column(name = "TIME_INPUT_USE_ATR")
    private int timeInputUseAtr;

    @Column(name = "TIME_CAL_USE_ATR")
    private int timeCalUseAtr;

    @Column(name = "WORK_TIME_INI_ATR")
    private int workTimeIniAtr;

    @Column(name = "END_WORK_TIME_INI_ATR")
    private int endWorkTimeIniAtr;

    @Column(name = "PRE_INPUT_TIME_REFLECT_ATR")
    private int preInputTimeReflectAtr;

    @Column(name = "POST_WORK_TIME_REFLECT_ATR")
    private int postWorkTimeReflectAtr;

    @Column(name = "POST_BP_TIME_REFLECT_ATR")
    private int postBpTimeReflectAtr;

//    @Column(name = "POST_ANYV_TIME_REFLECT_ATR")
//    private int postAnyvTimeReflectAtr;

    @Column(name = "POST_DVGC_REFLECT_ATR")
    private int postDvgcReflectAtr;

    @Column(name = "POST_BREAK_TIME_REFLECT_ATR")
    private int postBreakTimeReflectAtr;

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public HolidayWorkAppSet toHolidayWorkAppSetDomain() {
        return new HolidayWorkAppSet(
                companyId,
                EnumAdaptor.valueOf(stampMissCalAtr, CalcStampMiss.class),
                BooleanUtils.toBoolean(goBackDirectlyAtr),
                ApplicationDetailSetting.create(
                        this.instructRequiredAtr,
                        this.preRequiredAtr,
                        this.timeInputUseAtr,
                        this.timeCalUseAtr,
                        this.workTimeIniAtr,
                        this.endWorkTimeIniAtr
                ),
                OvertimeLeaveAppCommonSet.create(
                        this.preExcessAtr,
                        this.extraTimeExcessAtr,
                        this.extraTimeDisplayAtr,
                        this.atdExcessAtr,
                        this.instructExcessAtr,
                        this.dvgcExcessAtr,
                        this.atdExcessOverrideAtr)
        );
    }

    public HdWorkAppReflect toHolidayWorkAppReflect() {
        return new HdWorkAppReflect(
                new BeforeHdWorkAppReflect(EnumAdaptor.valueOf(preInputTimeReflectAtr, NotUseAtr.class)),
                AfterHdWorkAppReflect.create(
                        postWorkTimeReflectAtr,
                        postBpTimeReflectAtr,
//                        postAnyvTimeReflectAtr,
                        postDvgcReflectAtr,
                        postBreakTimeReflectAtr
                )
        );
    }

    public static KrqmtAppHdWork create(HolidayWorkAppSet holidayWorkAppSet, HdWorkAppReflect holidayWorkAppReflect) {
        return new KrqmtAppHdWork(
                holidayWorkAppSet.getCompanyID(),
                holidayWorkAppSet.getCalcStampMiss().value,
                BooleanUtils.toInteger(holidayWorkAppSet.isUseDirectBounceFunction()),
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getPreExcessDisplaySetting().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getExtratimeExcessAtr().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getExtratimeDisplayAtr().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getPerformanceExcessAtr().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getOverrideSet().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getCheckOvertimeInstructionRegister().value,
                holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getCheckDeviationRegister().value,
                BooleanUtils.toInteger(holidayWorkAppSet.getApplicationDetailSetting().getRequiredInstruction()),
                holidayWorkAppSet.getApplicationDetailSetting().getPreRequireSet().value,
                holidayWorkAppSet.getApplicationDetailSetting().getTimeInputUse().value,
                holidayWorkAppSet.getApplicationDetailSetting().getTimeCalUse().value,
                holidayWorkAppSet.getApplicationDetailSetting().getAtworkTimeBeginDisp().value,
                BooleanUtils.toInteger(holidayWorkAppSet.getApplicationDetailSetting().isDispSystemTimeWhenNoWorkTime()),
                holidayWorkAppReflect.getBefore().getReflectActualHolidayWorkAtr().value,
                holidayWorkAppReflect.getAfter().getWorkReflect().value,
                holidayWorkAppReflect.getAfter().getOthersReflect().getReflectPaytimeAtr().value,
//                holidayWorkAppReflect.getAfter().getOthersReflect().getReflectOptionalItemsAtr().value,
                holidayWorkAppReflect.getAfter().getOthersReflect().getReflectDivergentReasonAtr().value,
                holidayWorkAppReflect.getAfter().getBreakLeaveApplication().getBreakReflectAtr().value
        );
    }

    public void updateSettng(HolidayWorkAppSet domain) {
        stampMissCalAtr = domain.getCalcStampMiss().value;
        goBackDirectlyAtr = BooleanUtils.toInteger(domain.isUseDirectBounceFunction());
        preExcessAtr = domain.getOvertimeLeaveAppCommonSet().getPreExcessDisplaySetting().value;
        extraTimeExcessAtr = domain.getOvertimeLeaveAppCommonSet().getExtratimeExcessAtr().value;
        extraTimeDisplayAtr = domain.getOvertimeLeaveAppCommonSet().getExtratimeDisplayAtr().value;
        atdExcessAtr = domain.getOvertimeLeaveAppCommonSet().getPerformanceExcessAtr().value;
        atdExcessOverrideAtr = domain.getOvertimeLeaveAppCommonSet().getOverrideSet().value;
        instructExcessAtr = domain.getOvertimeLeaveAppCommonSet().getCheckOvertimeInstructionRegister().value;
        dvgcExcessAtr = domain.getOvertimeLeaveAppCommonSet().getCheckDeviationRegister().value;
        instructRequiredAtr = BooleanUtils.toInteger(domain.getApplicationDetailSetting().getRequiredInstruction());
        preRequiredAtr = domain.getApplicationDetailSetting().getPreRequireSet().value;
        timeInputUseAtr = domain.getApplicationDetailSetting().getTimeInputUse().value;
        timeCalUseAtr = domain.getApplicationDetailSetting().getTimeCalUse().value;
        workTimeIniAtr = domain.getApplicationDetailSetting().getAtworkTimeBeginDisp().value;
        endWorkTimeIniAtr = BooleanUtils.toInteger(domain.getApplicationDetailSetting().isDispSystemTimeWhenNoWorkTime());
    }

    public void updateReflect(HdWorkAppReflect domain) {
        preInputTimeReflectAtr = domain.getBefore().getReflectActualHolidayWorkAtr().value;
        postWorkTimeReflectAtr = domain.getAfter().getWorkReflect().value;
        postBpTimeReflectAtr = domain.getAfter().getOthersReflect().getReflectPaytimeAtr().value;
//        postAnyvTimeReflectAtr = domain.getAfter().getOthersReflect().getReflectOptionalItemsAtr().value;
        postDvgcReflectAtr = domain.getAfter().getOthersReflect().getReflectDivergentReasonAtr().value;
        postBreakTimeReflectAtr = domain.getAfter().getBreakLeaveApplication().getBreakReflectAtr().value;
    }
}

