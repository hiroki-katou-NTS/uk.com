package nts.uk.ctx.at.function.ws.alarm.mailsettings;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.AlarmMailSendingRoleDto;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingDto;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingFinder;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingsInformationDto;
import nts.uk.ctx.at.function.dom.adapter.alarm.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.MailExportRolesDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author rafiq
 * アラームリスト実行メール設定を取得する
 */

@Path("at/function/alarm/mail/settings")
@Produces("application/json")
public class MailSettingByWorkPlace extends WebService {

    @Inject
    private MailSettingFinder finder;

    @Inject
    private AlarmMailSettingsAdapter alarmMailSettingsAdapter;

    @POST
    @Path("init")
    public MailSettingsInformationDto getInforMailSeting() {
        AlarmMailSendingRoleDto alarmMailSendingRoleDto = finder.getRole(0);
        MailSettingDto mailSettingDto = finder.getConfigured(1);
        List<MailExportRolesDto> roleNameList = alarmMailSettingsAdapter.getRoleNameList(alarmMailSendingRoleDto.getRoleIds());

        return new MailSettingsInformationDto(alarmMailSendingRoleDto, mailSettingDto, roleNameList);
    }
}
