package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.command;

import java.util.List;

import lombok.Data;

@Data
public class RetirementInformationRegisterCommand {
	List<RetiInforRegisInfoCommand> retiInfos;
}
