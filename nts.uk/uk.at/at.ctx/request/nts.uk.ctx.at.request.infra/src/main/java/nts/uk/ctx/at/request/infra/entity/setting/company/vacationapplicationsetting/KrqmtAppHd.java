package nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationTypeDisplayName;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.HolidayAppType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "KRQMT_APP_HD")
public class KrqmtAppHd extends ContractUkJpaEntity {

    @Id
    @Column(name = "CID")
    private String companyid;

    /**
     * 休暇申請設定.半日年休の使用上限チェック
     */
    @Column(name = "CKUPER_LIMIT_HALFDAY_HD")
    private int checkUpperLimitHalfDayHoliday;

    /**
     * 休暇申請種類表示名.表示名 (年休名称)
     */
    @Column(name = "YEAR_HD_NAME")
    private String yearHolidayName;

    /**
     * 休暇申請種類表示名.表示名 (代休名称)
     */
    @Column(name = "OBSTACLE_NAME")
    private String obstacleName;

    /**
     * 休暇申請種類表示名.表示名 (欠勤名称)
     */
    @Column(name = "ABSENTEEISM_NAME")
    private String absenseName;

    /**
     * 休暇申請種類表示名.表示名 (特別休暇名称)
     */
    @Column(name = "SPECIAL_VACATION_NAME")
    private String specialVacationName;

    /**
     * 休暇申請種類表示名.表示名 (積立年休名称)
     */
    @Column(name = "YEAR_RESIG_NAME")
    private String yearResigName;

    /**
     * 休暇申請種類表示名.表示名 (休日名称)
     */
    @Column(name = "HD_NAME")
    private String holidayName;

    /**
     * 休暇申請種類表示名.表示名 (時間消化名称)
     */
    @Column(name = "TIME_DIGEST_NAME")
    private String timeDigestName;

    /**
     * 休暇申請の反映．休暇系申請の反映.出退勤を反映する
     */
    @Column(name = "WORK_TIME_REFLECT_ATR")
    private int workTimeReflectAtr;

    /**
     * 休暇申請の反映．休暇系申請の反映.1日休暇の場合は出退勤を削除
     */
    @Column(name = "WORK_TIME_DELETE_ATR")
    private int workTimeDeleteAtr;

    @Column(name = "WORK_TIME_CD_REFLECT_ATR")
    private int workTimeConditionReflectAtr;

    @Column(name = "SIXTY_OVERTIME_REFLECT_ATR")
    private int sixtyOvertimeReflectAtr;

    @Column(name = "CARE_REFLECT_ATR")
    private int careReflectAtr;

    @Column(name = "CHILD_CARE_REFLECT_ATR")
    private int childCareReflectAtr;

    @Column(name = "HDCOM_REFLECT_ATR")
    private int substituteLeaveReflectAtr;

    @Column(name = "HDSP_REFLECT_ATR")
    private int specialHolidayReflectAtr;

    @Column(name = "HDPAID_REFLECT_ATR")
    private int annualLeaveReflectAtr;

    public HolidayApplicationSetting toHolidayApplicationSetting() {
        List<HolidayApplicationTypeDisplayName> displayNames = Arrays.asList(
                HolidayApplicationTypeDisplayName.create(HolidayAppType.ANNUAL_PAID_LEAVE.value, yearHolidayName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.SUBSTITUTE_HOLIDAY.value, obstacleName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.ABSENCE.value, absenseName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.SPECIAL_HOLIDAY.value, specialVacationName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.YEARLY_RESERVE.value, yearResigName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.HOLIDAY.value, holidayName),
                HolidayApplicationTypeDisplayName.create(HolidayAppType.DIGESTION_TIME.value, timeDigestName)
        );
        return HolidayApplicationSetting.create(companyid, displayNames, checkUpperLimitHalfDayHoliday);
    }

    public VacationApplicationReflect toVacationApplicationReflect() {
        return new VacationApplicationReflect(
                this.companyid,
                new VacationAppReflectOption(
                        EnumAdaptor.valueOf(workTimeDeleteAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(workTimeReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(workTimeConditionReflectAtr, ReflectWorkHourCondition.class)
                ),
                new TimeLeaveAppReflectCondition(
                        EnumAdaptor.valueOf(sixtyOvertimeReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(careReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(childCareReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(substituteLeaveReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(annualLeaveReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(specialHolidayReflectAtr, NotUseAtr.class)
                )
        );
    }

    public static KrqmtAppHd create(HolidayApplicationSetting setting, VacationApplicationReflect reflect) {
        KrqmtAppHd entity = new KrqmtAppHd();
        entity.setCompanyid(setting.getCompanyId());
        entity.setCheckUpperLimitHalfDayHoliday(setting.getHalfDayAnnualLeaveUsageLimitCheck().value);
        setting.getHolidayApplicationTypeDisplayName().forEach(d -> {
            switch (d.getHolidayApplicationType()) {
                case ANNUAL_PAID_LEAVE:
                    entity.setYearHolidayName(d.getDisplayName().v());
                    break;
                case SUBSTITUTE_HOLIDAY:
                    entity.setObstacleName(d.getDisplayName().v());
                    break;
                case ABSENCE:
                    entity.setAbsenseName(d.getDisplayName().v());
                    break;
                case SPECIAL_HOLIDAY:
                    entity.setSpecialVacationName(d.getDisplayName().v());
                    break;
                case YEARLY_RESERVE:
                    entity.setYearResigName(d.getDisplayName().v());
                    break;
                case HOLIDAY:
                    entity.setHolidayName(d.getDisplayName().v());
                    break;
                case DIGESTION_TIME:
                    entity.setTimeDigestName(d.getDisplayName().v());
                    break;
                default:
                    break;
            }
        });
        entity.workTimeReflectAtr = reflect.getWorkAttendanceReflect().getReflectAttendance().value;
        entity.workTimeDeleteAtr = reflect.getWorkAttendanceReflect().getOneDayLeaveDeleteAttendance().value;
        entity.workTimeConditionReflectAtr = reflect.getWorkAttendanceReflect().getReflectWorkHour().value;
        entity.sixtyOvertimeReflectAtr = reflect.getTimeLeaveReflect().getSuperHoliday60H().value;
        entity.careReflectAtr = reflect.getTimeLeaveReflect().getNursing().value;
        entity.childCareReflectAtr = reflect.getTimeLeaveReflect().getChildNursing().value;
        entity.substituteLeaveReflectAtr = reflect.getTimeLeaveReflect().getSubstituteLeaveTime().value;
        entity.annualLeaveReflectAtr = reflect.getTimeLeaveReflect().getAnnualVacationTime().value;
        entity.specialHolidayReflectAtr = reflect.getTimeLeaveReflect().getSpecialVacationTime().value;
        return entity;
    }

    @Override
    protected Object getKey() {
        return companyid;
    }

    public void updateSetting(HolidayApplicationSetting setting) {
        this.setCheckUpperLimitHalfDayHoliday(setting.getHalfDayAnnualLeaveUsageLimitCheck().value);
        setting.getHolidayApplicationTypeDisplayName().forEach(d -> {
            switch (d.getHolidayApplicationType()) {
                case ANNUAL_PAID_LEAVE:
                    this.setYearHolidayName(d.getDisplayName().v());
                    break;
                case SUBSTITUTE_HOLIDAY:
                    this.setObstacleName(d.getDisplayName().v());
                    break;
                case ABSENCE:
                    this.setAbsenseName(d.getDisplayName().v());
                    break;
                case SPECIAL_HOLIDAY:
                    this.setSpecialVacationName(d.getDisplayName().v());
                    break;
                case YEARLY_RESERVE:
                    this.setYearResigName(d.getDisplayName().v());
                    break;
                case HOLIDAY:
                    this.setHolidayName(d.getDisplayName().v());
                    break;
                case DIGESTION_TIME:
                    this.setTimeDigestName(d.getDisplayName().v());
                    break;
                default:
                    break;
            }
        });
    }

    public void updateReflect(VacationApplicationReflect reflect) {
        this.workTimeReflectAtr = reflect.getWorkAttendanceReflect().getReflectAttendance().value;
        this.workTimeDeleteAtr = reflect.getWorkAttendanceReflect().getOneDayLeaveDeleteAttendance().value;
        this.workTimeConditionReflectAtr = reflect.getWorkAttendanceReflect().getReflectWorkHour().value;
        this.sixtyOvertimeReflectAtr = reflect.getTimeLeaveReflect().getSuperHoliday60H().value;
        this.careReflectAtr = reflect.getTimeLeaveReflect().getNursing().value;
        this.childCareReflectAtr = reflect.getTimeLeaveReflect().getChildNursing().value;
        this.substituteLeaveReflectAtr = reflect.getTimeLeaveReflect().getSubstituteLeaveTime().value;
        this.annualLeaveReflectAtr = reflect.getTimeLeaveReflect().getAnnualVacationTime().value;
        this.specialHolidayReflectAtr = reflect.getTimeLeaveReflect().getSpecialVacationTime().value;
    }
}
