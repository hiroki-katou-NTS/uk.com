package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHolidayAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteSimultaneousAppSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KRQMT_APP_HDSUB_REC")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrqmtAppHdsubRec extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "MULTI_REQUIRE_ATR")
    private Integer multiRequireAtr;

//    @Column(name = "HD_SUB_BEFORE_ATR")
//    private int hdSubBeforeAtr;

    @Column(name = "HD_SUB_CMT_FONT_COLOR")
    private String hdSubCmtFontColor;

    @Column(name = "HD_SUB_CMT_CONTENT")
    private String hdSubCmtContent;

    @Column(name = "HD_SUB_CMT_FONT_WEIGHT")
    private int hdSubCmtFontWeight;

    @Column(name = "REC_CMT_FONT_COLOR")
    private String recCmtFontColor;

    @Column(name = "REC_CMT_CONTENT")
    private String recCmtContent;

    @Column(name = "REC_CMT_FONT_WEIGHT")
    private int recCmtFontWeight;

    @Column(name = "REC_WORK_TIME_REFLECT_ATR")
    private int recWorkTimeReflectAtr;

    @Column(name = "SUB_WORK_TIME_REFLECT_ATR")
    private int subWorkTimeReflectAtr;

    @Column(name = "SUB_WORK_TIME_DELETE_ATR")
    private int subWorkTimeDeleteAtr;

    @Column(name = "SUB_WORK_TIME_CD_REFLECT_ATR")
    private int subWorkTimeConditionReflectAtr;

    @Override
    protected Object getKey() {
        return companyId;
    }

    public SubstituteLeaveAppReflect toSubstituteLeaveAppReflect() {
        return new SubstituteLeaveAppReflect(
                new VacationAppReflectOption(
                        EnumAdaptor.valueOf(subWorkTimeDeleteAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(subWorkTimeReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(subWorkTimeConditionReflectAtr, ReflectWorkHourCondition.class)
                )
        );
    }

    public SubstituteWorkAppReflect toSubstituteWorkAppReflect() {
        return new SubstituteWorkAppReflect(EnumAdaptor.valueOf(recWorkTimeReflectAtr, NotUseAtr.class));
    }

    public SubstituteHdWorkAppSet toSetting() {
        return new SubstituteHdWorkAppSet(
                companyId,
                new SubstituteSimultaneousAppSet(
                        BooleanUtils.toBoolean(multiRequireAtr)
//                        EnumAdaptor.valueOf(hdSubBeforeAtr, ApplyPermission.class)
                ),
                new SubstituteHolidayAppSet(
                        new AppCommentSet(
                                new Comment(hdSubCmtContent),
                                BooleanUtils.toBoolean(hdSubCmtFontWeight),
                                new ColorCode(hdSubCmtFontColor)
                        )
                ),
                new SubstituteWorkAppSet(
                        new AppCommentSet(
                                new Comment(recCmtContent),
                                BooleanUtils.toBoolean(recCmtFontWeight),
                                new ColorCode(recCmtFontColor)
                        )
                )
        );
    }

    public static KrqmtAppHdsubRec create(SubstituteHdWorkAppSet setting, SubstituteLeaveAppReflect suspenseReflect, SubstituteWorkAppReflect drawOutReflect) {
        return new KrqmtAppHdsubRec(
                setting.getCompanyId(),
                BooleanUtils.toInteger(setting.getSimultaneousSetting().isSimultaneousApplyRequired()),
//                setting.getSimultaneousSetting().getAllowanceForAbsence().value,
                setting.getSubstituteHolidaySetting().getComment().getColorCode().v(),
                setting.getSubstituteHolidaySetting().getComment().getComment().v(),
                BooleanUtils.toInteger(setting.getSubstituteHolidaySetting().getComment().isBold()),
                setting.getSubstituteWorkSetting().getComment().getColorCode().v(),
                setting.getSubstituteWorkSetting().getComment().getComment().v(),
                BooleanUtils.toInteger(setting.getSubstituteWorkSetting().getComment().isBold()),
                drawOutReflect.getReflectAttendanceAtr().value,
                suspenseReflect.getWorkInfoAttendanceReflect().getReflectAttendance().value,
                suspenseReflect.getWorkInfoAttendanceReflect().getOneDayLeaveDeleteAttendance().value,
                suspenseReflect.getWorkInfoAttendanceReflect().getReflectWorkHour().value
        );
    }

    public void updateSetting(SubstituteHdWorkAppSet setting) {
        this.multiRequireAtr = BooleanUtils.toInteger(setting.getSimultaneousSetting().isSimultaneousApplyRequired());
//        this.hdSubBeforeAtr = setting.getSimultaneousSetting().getAllowanceForAbsence().value;
        this.hdSubCmtFontColor = setting.getSubstituteHolidaySetting().getComment().getColorCode().v();
        this.hdSubCmtContent = setting.getSubstituteHolidaySetting().getComment().getComment().v();
        this.hdSubCmtFontWeight = BooleanUtils.toInteger(setting.getSubstituteHolidaySetting().getComment().isBold());
        this.recCmtFontColor = setting.getSubstituteWorkSetting().getComment().getColorCode().v();
        this.recCmtContent = setting.getSubstituteWorkSetting().getComment().getComment().v();
        this.recCmtFontWeight = BooleanUtils.toInteger(setting.getSubstituteWorkSetting().getComment().isBold());
    }

    public void updateSubLeaveReflect(SubstituteLeaveAppReflect reflect) {
        this.subWorkTimeReflectAtr = reflect.getWorkInfoAttendanceReflect().getReflectAttendance().value;
        this.subWorkTimeDeleteAtr = reflect.getWorkInfoAttendanceReflect().getOneDayLeaveDeleteAttendance().value;
        this.subWorkTimeConditionReflectAtr = reflect.getWorkInfoAttendanceReflect().getReflectWorkHour().value;
    }

    public void updateSubWorkReflect(SubstituteWorkAppReflect reflect) {
        this.recWorkTimeReflectAtr = reflect.getReflectAttendanceAtr().value;
    }
}
