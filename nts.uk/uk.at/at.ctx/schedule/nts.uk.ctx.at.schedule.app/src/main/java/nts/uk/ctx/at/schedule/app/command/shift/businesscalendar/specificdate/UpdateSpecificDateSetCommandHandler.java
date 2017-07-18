package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.util.List;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

public class UpdateSpecificDateSetCommandHandler extends CommandHandler<UpdateSpecificDateSetCommand>{

	@Override
	protected void handle(CommandHandlerContext<UpdateSpecificDateSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateSpecificDateSetCommand data = context.getCommand();
		if(data.getStrDate()>data.getEndDate()){
			//strDate > endDate => display msg_136
			throw new BusinessException("Msg_136");
		}
		if(data.getDayofWeek().isEmpty()){
			//date set is emtity => display msg_136
			throw new BusinessException("Msg_137");
		}
		if(data.getLstTimeItemId().isEmpty()){
			//time item seleced is emtity => display msg_136
			throw new BusinessException("Msg_138");
		}
		if(data.getUtil()==1){//company
			
		}else{//workplace
			
		}
	}
	public static boolean ConverttoDayofWeek(GeneralDate dateInString, List<Integer> dayofWeek){
		Integer dateInInt = dateInString.dayOfWeek();
		boolean value = false;
		for (Integer integer : dayofWeek) {
			if(integer == dateInInt){
				value = true;
				break;
			}
		}
		return value;
	}
	private void UpdatebyDay(int strDate, int endDate, List<Integer> dayofWeek, List<String> lstTimeItemId ,int setUpdate){
		GeneralDate sDate = GeneralDate.fromString(String.valueOf(strDate), "yyyyMMdd");
		GeneralDate eDate = GeneralDate.fromString(String.valueOf(endDate), "yyyyMMdd");
		GeneralDate date = sDate;
		while(date == eDate){
			if(!ConverttoDayofWeek(date,dayofWeek)){//not setting
				date.addDays(1);
				continue;
			}
			//setting
			if(setUpdate==1){
				//既に設定されている内容は据え置き、追加で設定する - complement
				
			}else{
				//既に設定されている内容をクリアし、今回選択したものだけを設定する - add new
			}
			date.addDays(1);
		}
	}

}
