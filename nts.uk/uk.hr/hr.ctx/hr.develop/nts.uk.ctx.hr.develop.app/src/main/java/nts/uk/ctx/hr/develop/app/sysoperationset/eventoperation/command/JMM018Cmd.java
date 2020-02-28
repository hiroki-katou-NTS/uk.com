package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command;

import java.util.List;

import lombok.Data;

@Data
public class JMM018Cmd {
	private List<EventOperationCmd> listEventOper;
	private List<MenuOperationCmd> listMenuOper;
}
