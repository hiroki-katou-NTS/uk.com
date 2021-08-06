package nts.uk.ctx.at.function.dom.adapter.role;

import nts.uk.ctx.at.function.dom.adapter.alarm.MailExportRolesDto;

import java.util.List;

public interface AlarmMailSettingsAdapter {
    List<MailExportRolesDto> getRoleNameList(List<String> lstRoleId);
}
