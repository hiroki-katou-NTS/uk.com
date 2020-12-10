package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;

@Data
public class DeleteBanWorkTogetherCommand {

    private TargetOrgIdenInforDto targetOrgIdenInfor;

    private String code;

}
