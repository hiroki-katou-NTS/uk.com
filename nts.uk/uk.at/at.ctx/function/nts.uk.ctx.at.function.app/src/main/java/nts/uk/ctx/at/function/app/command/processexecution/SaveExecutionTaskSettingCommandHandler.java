package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.eclipse.persistence.jpa.jpql.parser.WhenClause;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.primitive.TimeAsMinutesPrimitiveValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.RepeatMonthDayRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.OneDayRepeatInterval;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.RepeatDetailSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.DailyDaySetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingDaily;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingMonthly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingWeekly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatWeekDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.WeeklyWeekSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.task.schedule.UkJobScheduler;

@Stateless
public class SaveExecutionTaskSettingCommandHandler extends CommandHandlerWithResult<SaveExecutionTaskSettingCommand, String> {

	@Inject
	private ExecutionTaskSettingRepository execTaskSettingRepo;
	
	@Inject
	private RepeatMonthDayRepository repMonthDayRepo;
	
	@Inject
	private UkJobScheduler scheduler;

	@Override
	protected String handle(CommandHandlerContext<SaveExecutionTaskSettingCommand> context) {
		SaveExecutionTaskSettingCommand command = context.getCommand();
 		String companyId = AppContexts.user().companyId();
		// 終了時刻 
		TaskEndTime endTime =
				new TaskEndTime(
						EnumAdaptor.valueOf(command.getEndTimeCls(), EndTimeClassification.class),
						command.getEndTime() == null ? null : new EndTime(command.getEndTime()));
				
		// 繰り返し間隔
		OneDayRepeatInterval oneDayRepInr =
				new OneDayRepeatInterval(
						command.getOneDayRepInterval() == null ? null : EnumAdaptor.valueOf(command.getOneDayRepInterval(),OneDayRepeatIntervalDetail.class),
						EnumAdaptor.valueOf(command.getOneDayRepCls(), OneDayRepeatClassification.class));
		
		// 終了日日付指定
		TaskEndDate endDate = new TaskEndDate(EnumAdaptor.valueOf(command.getEndDateCls(), EndDateClassification.class), command.getEndDate());
		
		
		// 繰り返し詳細設定(毎週)
		RepeatDetailSettingWeekly weekly =
				new RepeatDetailSettingWeekly(
						new RepeatWeekDaysSelect(command.isMonday(), command.isTuesday(), command.isWednesday(),
												 command.isThursday(), command.isFriday(), command.isSaturday(), command.isSunday()));
		
		// 繰り返し詳細設定(毎月)
		List<RepeatMonthDaysSelect> days =
				command.getRepeatMonthDateList()
						.stream()
							.map(x -> EnumAdaptor.valueOf(x, RepeatMonthDaysSelect.class))
								.collect(Collectors.toList());
		RepeatMonthSelect months =
				new RepeatMonthSelect(command.isJanuary(), command.isFebruary(), command.isMarch(),
										command.isApril(), command.isMay(), command.isJune(),
										command.isJuly(), command.isAugust(), command.isSeptember(),
										command.isOctober(), command.isNovember(), command.isDecember());
		RepeatDetailSettingMonthly monthly = new RepeatDetailSettingMonthly(days, months);
		
		// 繰り返し詳細設定
		RepeatDetailSetting detailSetting = new RepeatDetailSetting( weekly, monthly);
		
		ExecutionTaskSetting taskSetting = new ExecutionTaskSetting(oneDayRepInr,
									new ExecutionCode(command.getExecItemCd()),
									companyId,
									command.isEnabledSetting(),
									null, 
									endDate,
									endTime,
									command.isRepeatCls(),
									EnumAdaptor.valueOf(command.getRepeatContent(), RepeatContentItem.class),
									detailSetting,
									command.getStartDate(),
									new StartTime(command.getStartTime()));
		
		// Calculate next execution date time
		taskSetting.setNextExecDateTime();
		
			String cron = this.getCron(command);
		// 登録チェック処理
		taskSetting.validate();
		if (command.isNewMode()) {
			this.execTaskSettingRepo.insert(taskSetting);
		} else {
			this.repMonthDayRepo.removeAllByCidAndExecCd(companyId, command.getExecItemCd());
			this.execTaskSettingRepo.update(taskSetting);
			this.repMonthDayRepo.insert(companyId, command.getExecItemCd(), days);
		}
		return taskSetting.getExecItemCd().v();
	}
	
	private String getCron(SaveExecutionTaskSettingCommand command){
		Integer  repeatMinute = null;
		if(command.getOneDayRepInterval()!=null){
			switch (command.getOneDayRepInterval().intValue()) {
			case 0:
				  repeatMinute = 1;
				break;
			case 1:
				  repeatMinute = 5;
				break;
			case 2:
				  repeatMinute = 10;
				break;
			case 3:
				  repeatMinute = 15;
				break;
			case 4:
				  repeatMinute = 20;
				break;
			case 5:
				  repeatMinute = 30;
				break;
			case 6:
				  repeatMinute = 60;
				break;	
			default:
				repeatMinute = 1;
				break;
			}	
		}
		
		command.getOneDayRepInterval();
		
		StringBuilder cronExpress = new StringBuilder();
		switch (command.getRepeatContent().intValue()) {
		case 0: //day
			if(repeatMinute==null){
				cronExpress.append("* * * * * ? ");
			}else{
				cronExpress.append("* 0/"+repeatMinute+" * * * ? ");
			}
				break;
		case 1: //week
			if(!command.isSunday()&&!command.isMonday()&& !command.isTuesday()&&!command.isWednesday()&&!command.isThursday()&&!command.isFriday()&&!command.isSaturday())
			{
				if(repeatMinute==null){
					cronExpress.append("* * * * * ? ");
				}else{
					cronExpress.append("* 0/"+repeatMinute+" * ? * * ");
				}
				break;
			}
			if(repeatMinute==null){
				cronExpress.append("* * * ? ");
			}else{
				cronExpress.append("* 0/"+repeatMinute+" *	 ?" );
			}
			cronExpress.append(" * ");
			if(command.isSunday()){
				cronExpress.append("SUN,");	
			}
			if(command.isMonday()){
				cronExpress.append("MON,");	
			}
			if(command.isTuesday()){
				cronExpress.append("TUE,");	
			}
			if(command.isWednesday()){
				cronExpress.append("WED,");	
			}
			if(command.isThursday()){
				cronExpress.append("THU,");
			}
			if(command.isFriday()){
				cronExpress.append("FRI,");
			}
			if(command.isSaturday()){
				cronExpress.append("SAT");
			}
			if(cronExpress.toString().endsWith(",")){
				cronExpress.deleteCharAt(cronExpress.length()-1);
			}
			break;
		case 2: //month	
			
			List<Integer> repeatMonthDateList = command.getRepeatMonthDateList();
			if(command.getRepeatMonthDateList().isEmpty()){
				if(repeatMinute==null){
					cronExpress.append("* * * ? ");
				}else
				{
					cronExpress.append("* 0/"+repeatMinute+" * ? ");
				}
				
				if(command.isJanuary()){
					cronExpress.append("JAN,");
				}
				if(command.isFebruary()){
					cronExpress.append("FEB,");
				}
				if(command.isMarch()){
					cronExpress.append("MAR,");
				}
				if(command.isApril()){
					cronExpress.append("APR,");
				}
				if(command.isMay()){
					cronExpress.append("MAY,");
				}
				if(command.isJune()){
					cronExpress.append("JUN,");
				}
				if(command.isJuly()){
					cronExpress.append("JUL,");
				}
				if(command.isJanuary()){
					cronExpress.append("JAN,");
				}
				if(command.isAugust()){
					cronExpress.append("AUG,");
				}
				if(command.isSeptember()){
					cronExpress.append("SEP,");
				}
				if(command.isOctober()){
					cronExpress.append("OCT,");
				}
				if(command.isNovember()){
					cronExpress.append("NOV,");
				}
				if(command.isDecember()){
					cronExpress.append("DEC");
				}
				if(cronExpress.toString().endsWith(",")){
					cronExpress.deleteCharAt(cronExpress.length()-1);
				}
				cronExpress.append(" * *");
				break;
			}
			
			if(repeatMinute==null){
				cronExpress.append("* * * ");
			}else{
				cronExpress.append("* 0/"+repeatMinute+" * ");
			}
			
			for (int dayOfMonth : repeatMonthDateList) {
				cronExpress.append(dayOfMonth+ ",");
			}
			if(cronExpress.toString().endsWith(",")){
				cronExpress.deleteCharAt(cronExpress.length()-1);
			}
			cronExpress.append(" ");
			if(command.isJanuary()){
				cronExpress.append("JAN,");
			}
			if(command.isFebruary()){
				cronExpress.append("FEB,");
			}
			if(command.isMarch()){
				cronExpress.append("MAR,");
			}
			if(command.isApril()){
				cronExpress.append("APR,");
			}
			if(command.isMay()){
				cronExpress.append("MAY,");
			}
			if(command.isJune()){
				cronExpress.append("JUN,");
			}
			if(command.isJuly()){
				cronExpress.append("JUL,");
			}
			if(command.isJanuary()){
				cronExpress.append("JAN,");
			}
			if(command.isAugust()){
				cronExpress.append("AUG,");
			}
			if(command.isSeptember()){
				cronExpress.append("SEP,");
			}
			if(command.isOctober()){
				cronExpress.append("OCT,");
			}
			if(command.isNovember()){
				cronExpress.append("NOV,");
			}
			if(command.isDecember()){
				cronExpress.append("DEC");
			}
			if(cronExpress.toString().endsWith(",")){
				cronExpress.deleteCharAt(cronExpress.length()-1);
			}
			cronExpress.append(" ?");
			
			break;
		default:
			if(repeatMinute==null){
				cronExpress.append("* * * * * ?");
			}else{
				cronExpress.append("* 0/"+repeatMinute+" * * * ?");
			}
				break;
		}
		return cronExpress.toString();
	}
}
