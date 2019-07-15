package nts.uk.ctx.pereg.app.command.process.checkdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;

@Stateless
public class ExecuteCheckDataAsynComHandle extends AsyncCommandHandler<CheckDataFromUI>{
	
	@Inject
	private CheckDataEmployeeServices checkDataServices;

	@Override
	protected void handle(CommandHandlerContext<CheckDataFromUI> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		CheckDataFromUI excuteCommand = context.getCommand();
		this.checkDataServices.manager(excuteCommand, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
	}

}
