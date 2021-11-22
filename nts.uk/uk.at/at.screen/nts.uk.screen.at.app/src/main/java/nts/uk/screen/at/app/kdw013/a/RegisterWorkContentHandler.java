package nts.uk.screen.at.app.kdw013.a;

import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.screen.at.app.dailymodify.command.DailyModifyRCommandFacade;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.kdw013.command.RegisterTaskTimeGroupCommand;
import nts.uk.screen.at.app.kdw013.command.RegisterTaskTimeGroupCommandHandler;
import nts.uk.screen.at.app.kdw013.query.CreateDpItemQuery;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.作業内容を登録する
 * 
 * @author tutt
 *
 */
@Stateless
public class RegisterWorkContentHandler extends CommandHandlerWithResult<RegisterWorkContentCommand, RegisterWorkContentDto> {

	@Inject
	private CheckAlarmTargetDate checkAlarmTargetDate;

	@Inject
	private CreateDpItemQuery createDpItemQuery;

	@Inject
	private DailyModifyRCommandFacade dailyModifyRCommandFacade;

	@Inject
	private RegisterTaskTimeGroupCommandHandler handler;
	
	@Inject
	private GetTargetTime getTargetTime;

	@Inject
	private GetDailyPerformanceData getDailyPerformanceData;
	
	@Override
	protected RegisterWorkContentDto handle(CommandHandlerContext<RegisterWorkContentCommand> context) {
		
		RegisterWorkContentCommand command = context.getCommand();
		
		GeneralDate startDate =  command.getChangedDates().stream().min(Comparator.comparing(GeneralDate::dayOfYear)).get();
		GeneralDate endDate =  command.getChangedDates().stream().max(Comparator.comparing(GeneralDate::dayOfYear)).get();
		
		
		List<IntegrationOfDaily> dailys = this.getDailyPerformanceData
				.get(command.getEmployeeId(), new DatePeriod(startDate, endDate), command.getItemIds())
				.getLstIntegrationOfDaily();
		
		//map lại break time
		dailys.forEach(daily -> {
			command.getIntegrationOfDailys().stream().filter(id -> id.getYmd().equals(daily.getYmd())).findFirst()
					.ifPresent(id -> {
						daily.setBreakTime(id.getBreakTime());
					});
		});
		
		RegisterWorkContentDto result = new RegisterWorkContentDto();
		// 1. 実績登録パラメータを作成する

		DPItemParent dataParent = createDpItemQuery.CreateDpItem(command.getEmployeeId(), command.getChangedDates(),
				command.getManHrlst(), dailys);

		//throw business
		// 2. 修正した実績を登録する

		result.setDataResult(this.dailyModifyRCommandFacade.insertItemDomain(dataParent));
		
		// 3. 作業時間帯グループを登録する
		
		command.getWorkDetails().forEach(wd -> {

			RegisterTaskTimeGroupCommand cmd = new RegisterTaskTimeGroupCommand(command.getEmployeeId(), wd.getDate(), wd.toTimeZones());

			this.handler.handle(cmd);
		});

		// 4. アラーム発生対象日を確認する

		checkAlarmTargetDate.checkAlarm(command.getEmployeeId(), command.getChangedDates());
		
		
		if(command.getMode() == 1){
			
			// 5.残業申請・休出時間申請の対象時間を取得する
			
			List<OvertimeLeaveTimeDto> ots = this.getTargetTime.get(command.getEmployeeId(),
					command.getChangedDates());
			
			result.setLstOvertimeLeaveTime(ots);
			
		}
		
		// 6. List<残業休出時間>.isPresent check dưới client

		return result;
	}

	
}
