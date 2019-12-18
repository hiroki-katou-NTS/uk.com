package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.command;

import java.util.List;

import lombok.Data;

@Data
public class RetirementInformationRegisterCommand {
	List<RetiInforRegisInfoCommand> retiInfos;
}
