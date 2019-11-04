package nts.uk.ctx.pereg.app.command.process.checkdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class CheckDataEmpCommandHandler extends AsyncCommandHandler<CheckDataFromUI>{
	@Inject I18NResourcesForUK ukResouce;
	
	@Inject CheckDataEmployeeServices checkdataServices;
	
	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	
	@Override
	protected void handle(CommandHandlerContext<CheckDataFromUI> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		CheckDataFromUI param = context.getCommand();
		
		this.checkdataServices.manager(param, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
	}
}
