package nts.uk.screen.at.app.ksm011.e.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol.ScheModifyCommonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol.ScheduleModifyByPersonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol.ScheModifyByWorkplaceDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionFuncCtrlSettingDto {
    // 共通設定表 : E5
    private ScheModifyCommonDto scheModifyCommon;

    //基本機能制御の利用区分  : E6_4
    private Integer useAtr;

    // 日数ドロップダウンリスト  : E6_6
    private Integer deadLineDay;

    // 職場別設定表 : E7
    private ScheModifyByWorkplaceDto scheModifyByWorkplace;

    //個人別設定表 : E8
    private ScheduleModifyByPersonDto scheduleModifyByPerson;

}
