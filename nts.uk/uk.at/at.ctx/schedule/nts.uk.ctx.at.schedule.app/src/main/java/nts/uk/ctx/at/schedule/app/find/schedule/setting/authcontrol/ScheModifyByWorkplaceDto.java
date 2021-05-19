package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByWorkplace;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheModifyByWorkplaceDto {

    private List<ScheModifyAuthCtrlByWorkplace> scheModifyAuthCtrlByWkp;

    private List<ScheModifyFuncByWorkplace> scheModifyFuncByWorkplaces;
}
