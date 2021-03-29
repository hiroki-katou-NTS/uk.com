package nts.uk.screen.at.app.ksm008.BanHolidayTogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherCodeNameDto;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherQuery;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Screen F : 初期起動
 */
@Stateless
public class StartupInfoBanHolidayScreenQuery {
    @Inject
    I18NResourcesForUK ukResource;

    @Inject
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    @Inject
    private BanHolidayTogetherQuery banHolidayTogetherQuery;

    @Inject
    private StartupInfoOrgScreenQuery startupInfoOrgScreenQuery;

    /**
     * 初期起動情報を取得する
     * 初期起動の情報取得する
     */
    public StartupInfoBanHolidayDto getStartupInfoBanHoliday(String code) {
        //1. コードと名称と説明を取得する(コード)
        AlarmCheckConditionsQueryDto codeNameDescription = alarmCheckConditionsQuery.getCodeNameDescription(code);

        //2. 組織情報を取得する()
        OrgInfoDto orgInfo = startupInfoOrgScreenQuery.getOrgInfo();

        //3. 取得する(会社ID, 対象組織情報)
        List<BanHolidayTogetherCodeNameDto> listBanHolidayTogether = banHolidayTogetherQuery.getAllBanHolidayTogether(
                orgInfo.getUnit(),
                orgInfo.getWorkplaceId(),
                orgInfo.getWorkplaceGroupId()
        );

        List<EnumConstant> businessDaysCalendarTypeEnum = EnumAdaptor.convertToValueNameList(BusinessDaysCalendarType.class, ukResource);

        return new StartupInfoBanHolidayDto(
                code,
                codeNameDescription.getConditionName(),
                codeNameDescription.getExplanationList(),
                orgInfo.getUnit(),
                orgInfo.getWorkplaceId(),
                orgInfo.getWorkplaceGroupId(),
                orgInfo.getCode(),
                orgInfo.getDisplayName(),
                listBanHolidayTogether,
                businessDaysCalendarTypeEnum
        );
    }
}
