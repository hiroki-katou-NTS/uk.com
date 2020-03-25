package nts.uk.ctx.bs.employee.app.command.groupcommonmaster;

import java.util.List;

import lombok.Getter;

@Getter
public class UpdateCommonMasterCommand {
	private CommonMasterCommand masterSelected;
	
	private List<CommonMasterCommand> listMaster;
}
