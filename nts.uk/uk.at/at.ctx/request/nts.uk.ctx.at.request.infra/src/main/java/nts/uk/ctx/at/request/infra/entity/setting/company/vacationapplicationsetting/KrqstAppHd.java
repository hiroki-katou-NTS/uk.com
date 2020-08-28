package nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.HolidayAppType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "KRQST_APP_HD")
public class KrqstAppHd extends UkJpaEntity {

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

    // TODO: there are more fields

    public HolidayApplicationSetting toHolidayApplicationSetting() {
        if (!StringUtils.isEmpty(yearHolidayName))
            return HolidayApplicationSetting.create(companyid, yearHolidayName, HolidayAppType.ANNUAL_PAID_LEAVE, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(obstacleName))
            return HolidayApplicationSetting.create(companyid, obstacleName, HolidayAppType.SUBSTITUTE_HOLIDAY, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(absenseName))
            return HolidayApplicationSetting.create(companyid, absenseName, HolidayAppType.ABSENCE, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(specialVacationName))
            return HolidayApplicationSetting.create(companyid, specialVacationName, HolidayAppType.SPECIAL_HOLIDAY, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(yearResigName))
            return HolidayApplicationSetting.create(companyid, yearResigName, HolidayAppType.YEARLY_RESERVE, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(holidayName))
            return HolidayApplicationSetting.create(companyid, holidayName, HolidayAppType.HOLIDAY, checkUpperLimitHalfDayHoliday);
        if (!StringUtils.isEmpty(timeDigestName))
            return HolidayApplicationSetting.create(companyid, timeDigestName, HolidayAppType.DIGESTION_TIME, checkUpperLimitHalfDayHoliday);
        return null;
    }

    public static KrqstAppHd fromHolidayApplicationSetting(HolidayApplicationSetting domain) {
        KrqstAppHd entity = new KrqstAppHd();
        entity.setCompanyid(domain.getCompanyId());
        entity.setCheckUpperLimitHalfDayHoliday(domain.getHalfDayAnnualLeaveUsageLimitCheck().value);
        switch (domain.getHolidayApplicationTypeDisplayName().getHolidayApplicationType()) {
            case ANNUAL_PAID_LEAVE:
                entity.setYearHolidayName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case SUBSTITUTE_HOLIDAY:
                entity.setObstacleName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case ABSENCE:
                entity.setAbsenseName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case SPECIAL_HOLIDAY:
                entity.setYearHolidayName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case YEARLY_RESERVE:
                entity.setYearResigName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case HOLIDAY:
                entity.setHolidayName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            case DIGESTION_TIME:
                entity.setTimeDigestName(domain.getHolidayApplicationTypeDisplayName().getDisplayName().v());
                return entity;
            default:
                return null;
        }

    }

    @Override
    protected Object getKey() {
        return companyid;
    }
}
