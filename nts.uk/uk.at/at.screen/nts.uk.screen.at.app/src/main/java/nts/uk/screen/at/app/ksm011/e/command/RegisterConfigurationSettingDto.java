package nts.uk.screen.at.app.ksm011.e.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterConfigurationSettingDto {
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
