package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPerson;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByPerson;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleModifyByPersonDto {
    private List<ScheModifyAuthCtrlByPerson> scheModifyAuthCtrlByPersons;

    private List<ScheModifyFuncByPerson> scheModifyFuncByPersons;
}
