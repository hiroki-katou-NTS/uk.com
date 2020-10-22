package nts.uk.screen.at.app.ksm008.BanHolidayTogether;

import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherQuery;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherQueryDto;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Screen F : 初期起動
 */
@Stateless
public class StartupInfoBanHolidayScreenQuery {
    @Inject
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    @Inject
    private BanHolidayTogetherQuery banHolidayTogetherQuery;

    @Inject
    private StartupInfoOrgScreenQuery startupInfoOrgScreenQuery;

    /**
     * 初期起動の情報取得する
     */
    public StartupInfoBanHolidayDto getStartupInfoBanHoliday() {
        String code = "03";
        AlarmCheckConditionsQueryDto codeNameDescription = alarmCheckConditionsQuery.getCodeNameDescription(code);

        OrgInfoDto orgInfo = startupInfoOrgScreenQuery.getOrgInfo();

        BanHolidayTogetherQueryDto listBanHolidayTogether = banHolidayTogetherQuery.getAllBanHolidayTogether(
                orgInfo.getUnit(),
                orgInfo.getWorkplaceId(),
                orgInfo.getWorkplaceGroupId()
        );

        return new StartupInfoBanHolidayDto(
                codeNameDescription.getConditionName(),
                codeNameDescription.getExplanationList(),
                orgInfo.getUnit(),
                orgInfo.getWorkplaceId(),
                orgInfo.getWorkplaceGroupId(),
                orgInfo.getCode(),
                orgInfo.getDisplayName(),
                listBanHolidayTogether.getBanHolidayTogetherCode(),
                listBanHolidayTogether.getBanHolidayTogetherName()
        );
    }
}
