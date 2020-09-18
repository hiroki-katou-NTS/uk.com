package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.AfterOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.残業申請設定
 */
@Entity
@Table(name = "KRQMT_APP_OVERTIME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppOvertime extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CID")
    private String companyId;

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

    @Column(name = "ATD_WORK_REFLECT_ATR")
    private int atdWorkReflectAtr;

    @Column(name = "PRE_WORK_REFLECT_ATR")
    private int preWorkReflectAtr;

    @Column(name = "PRE_INPUT_TIME_REFLECT_ATR")
    private int preInputTimeReflectAtr;

    @Column(name = "PRE_BREAK_TIME_REFLECT_ATR")
    private int preBreakTimeReflectAtr;

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

    @OneToMany(mappedBy = "appOvertimeSetting", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KrqmtAppOvertimeFrame> overtimeFrames = new ArrayList<>();

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public OvertimeAppSet toOvertimeAppSetDomain() {
        return new OvertimeAppSet(
                this.companyId,
                OvertimeLeaveAppCommonSet.create(
                        this.preExcessAtr,
                        this.extraTimeExcessAtr,
                        this.extraTimeDisplayAtr,
                        this.atdExcessAtr,
                        this.instructExcessAtr,
                        this.dvgcExcessAtr,
                        this.atdExcessOverrideAtr),
                KrqmtAppOvertimeFrame.toDomains(this.companyId, this.overtimeFrames),
                ApplicationDetailSetting.create(
                        this.instructRequiredAtr,
                        this.preRequiredAtr,
                        this.timeInputUseAtr,
                        this.timeCalUseAtr,
                        this.workTimeIniAtr,
                        this.endWorkTimeIniAtr
                )
        );
    }

    public OtWorkAppReflect toOvertimeWorkAppReflect() {
        return new OtWorkAppReflect(
                BeforeOtWorkAppReflect.create(preWorkReflectAtr, preInputTimeReflectAtr, preBreakTimeReflectAtr),
                AfterOtWorkAppReflect.create(
                        postWorkTimeReflectAtr,
                        postBpTimeReflectAtr,
//                        postAnyvTimeReflectAtr,
                        postDvgcReflectAtr,
                        postBreakTimeReflectAtr
                ),
                EnumAdaptor.valueOf(atdWorkReflectAtr, NotUseAtr.class)
        );
    }

    public static KrqmtAppOvertime create(OvertimeAppSet overtimeAppSet, OtWorkAppReflect overtimeWorkAppReflect) {
        return new KrqmtAppOvertime(
                overtimeAppSet.getCompanyID(),
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getPreExcessDisplaySetting().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getExtratimeExcessAtr().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getExtratimeDisplayAtr().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getPerformanceExcessAtr().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getOverrideSet().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getCheckOvertimeInstructionRegister().value,
                overtimeAppSet.getOvertimeLeaveAppCommonSet().getCheckDeviationRegister().value,
                BooleanUtils.toInteger(overtimeAppSet.getApplicationDetailSetting().getRequiredInstruction()),
                overtimeAppSet.getApplicationDetailSetting().getPreRequireSet().value,
                overtimeAppSet.getApplicationDetailSetting().getTimeInputUse().value,
                overtimeAppSet.getApplicationDetailSetting().getTimeCalUse().value,
                overtimeAppSet.getApplicationDetailSetting().getAtworkTimeBeginDisp().value,
                BooleanUtils.toInteger(overtimeAppSet.getApplicationDetailSetting().isDispSystemTimeWhenNoWorkTime()),
                overtimeWorkAppReflect.getReflectActualWorkAtr().value,
                overtimeWorkAppReflect.getBefore().getReflectWorkInfoAtr().value,
                overtimeWorkAppReflect.getBefore().getReflectActualOvertimeHourAtr().value,
                overtimeWorkAppReflect.getBefore().getBreakLeaveApplication().getBreakReflectAtr().value,
                overtimeWorkAppReflect.getAfter().getWorkReflect().value,
                overtimeWorkAppReflect.getAfter().getOthersReflect().getReflectPaytimeAtr().value,
//                overtimeWorkAppReflect.getAfter().getOthersReflect().getReflectOptionalItemsAtr().value,
                overtimeWorkAppReflect.getAfter().getOthersReflect().getReflectDivergentReasonAtr().value,
                overtimeWorkAppReflect.getAfter().getBreakLeaveApplication().getBreakReflectAtr().value,
                KrqmtAppOvertimeFrame.fromDomains(overtimeAppSet.getCompanyID(), overtimeAppSet.getOvertimeQuotaSet())
        );
    }

}

