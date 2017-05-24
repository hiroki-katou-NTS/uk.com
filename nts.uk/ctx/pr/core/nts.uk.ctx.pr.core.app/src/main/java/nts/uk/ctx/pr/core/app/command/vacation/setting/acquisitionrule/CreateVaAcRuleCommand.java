package nts.uk.ctx.pr.core.app.command.vacation.setting.acquisitionrule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreateVaAcRuleCommand extends VacationAcquisitionRuleBaseCommand implements AcquisitionRuleGetMemento {

}
