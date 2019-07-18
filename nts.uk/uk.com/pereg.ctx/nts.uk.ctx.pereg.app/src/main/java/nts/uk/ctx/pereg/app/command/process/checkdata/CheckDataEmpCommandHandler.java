package nts.uk.ctx.pereg.app.command.process.checkdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
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
		
		// check 画面の選択状態をチェックする (Check trạng thái chọn màn hình) 
		validateDataUI(asyncContext.getCommand());
		
		this.checkdataServices.manager(param, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
	}
	
	// check 画面の選択状態をチェックする (Check trạng thái chọn màn hình) 
	private void validateDataUI(CheckDataFromUI query){
		if(!query.isPerInfoCheck() && !query.isMasterCheck()){
			throw new BusinessException(new RawErrorMessage("Msg_929"));
		}
		
		if(!query.isPerInfoCheck() && query.isMasterCheck() && !query.isBonusMngCheck()
			&& !query.isDailyPerforMngCheck() && !query.isMonthCalCheck()
			&& !query.isMonthPerforMngCheck() && !query.isPayRollMngCheck()
			&& !query.isScheduleMngCheck() && !query.isYearlyMngCheck()){
			throw new BusinessException(new RawErrorMessage("Msg_929"));
		}
	}
}
