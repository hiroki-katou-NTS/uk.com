package nts.uk.screen.at.app.ksm011.e.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPermissionSettingCommand {
    // ロールリスト
    private String roleId;

    // 基本機能制御の利用区分
    private int useAtr;

    private int deadLineDay;

    // E5 : 共通設定表
    private List<AvailabilityPermissionDto> scheModifyCtrlCommons;

    // E7 : 職場別設定表
    private List<AvailabilityPermissionDto> scheModifyByWorkplaces;

    // E8 : 個人別設定表
    private List<AvailabilityPermissionDto> scheModifyByPersons;
}
