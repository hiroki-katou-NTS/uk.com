package nts.uk.ctx.at.function.dom.adapter.alarm;

import java.util.List;
/**
 * ロール名称を取得する
 * @author rafiqul.islam
 * */
public interface AlarmMailSettingsAdapter {
    /**
     * @param lstroleId
     * @return List<MailExportRolesDto>
     */
    List<MailExportRolesDto> getRoleNameList(List<String> lstroleId);
}
