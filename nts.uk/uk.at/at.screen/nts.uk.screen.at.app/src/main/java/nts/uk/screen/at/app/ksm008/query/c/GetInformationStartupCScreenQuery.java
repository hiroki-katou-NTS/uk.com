package nts.uk.screen.at.app.ksm008.query.c;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.BanWorkTogetherDto;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;
import nts.uk.screen.at.app.ksm008.query.b.dto.AlrmCheckDto;
import nts.uk.screen.at.app.ksm008.query.c.dto.DisplayInfoOrganizationDto;
import nts.uk.screen.at.app.ksm008.query.c.dto.InitScreenCDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.TargetOrgIdenInforDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.C: 同時出勤禁止.メニュー別OCD.初期起動の情報取得する
 */

@Stateless
public class GetInformationStartupCScreenQuery {

    @Inject
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    @Inject
    private StartupInfoOrgScreenQuery startupInfoOrgScreenQuery;

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepository;

    public InitScreenCDto get(String code) {

        // 1. コードと名称と説明を取得する(コード)
        AlarmCheckConditionsQueryDto checkCondition = alarmCheckConditionsQuery.getCodeNameDescription(code);

        // 2. 組織情報を取得する()
        OrgInfoDto orgInfo = startupInfoOrgScreenQuery.getOrgInfo();
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(orgInfo.getUnit(), TargetOrganizationUnit.class),
                orgInfo.getWorkplaceId() == null ? Optional.empty() : Optional.of(orgInfo.getWorkplaceId()),
                orgInfo.getWorkplaceGroupId() == null ? Optional.empty() : Optional.of(orgInfo.getWorkplaceGroupId())
        );

        // 3. 組織の同時出勤禁止リストを取得する
        List<BanWorkTogether> banWorkTogetherList = banWorkTogetherRepository.getAll(AppContexts.user().companyId(), targetOrgIdenInfor);

        InitScreenCDto result = new InitScreenCDto();

        result.setAlarmCheck(checkCondition);
        result.setOrgInfo(orgInfo);
        result.setBanWorkTogether(banWorkTogetherList.stream().map(BanWorkTogetherDto::fromDomain).collect(Collectors.toList()));

        return result;
    }

}
