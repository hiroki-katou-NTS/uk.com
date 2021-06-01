package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommon;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncCommon;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheModifyCommonDto {

    private List<ScheModifyAuthCtrlCommon> scheModifyAuthCtrlCommons;

    private List<ScheModifyFuncCommon> scheModifyFuncCommons;
}
