package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.screen.at.app.dailymodify.command.DailyModifyRCommandFacade;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
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
	private OuenWorkTimeSheetOfDailyRepo ouenSheetRepo;
	
	@Inject
	private OuenWorkTimeOfDailyRepo ouenTimeRepo;
	
	@Inject
	private EditStateOfDailyPerformanceRepository esRepo;
	
	@Override
	protected RegisterWorkContentDto handle(CommandHandlerContext<RegisterWorkContentCommand> context) {
		
		RegisterWorkContentCommand command = context.getCommand();
		
		
		//xoa co dinh No1 
		
//		this.ouenSheetRepo.removePK(command.getEmployeeId(), GeneralDate.today(), 1);
//		this.ouenTimeRepo.removePK(command.getEmployeeId(), GeneralDate.today(), 1);
		
		RegisterWorkContentDto result = new RegisterWorkContentDto();
		// 1. 実績登録パラメータを作成する

		DPItemParent dataParent = createDpItemQuery.CreateDpItem(command.getEmployeeId(), command.getChangedDates(),
				command.getManHrlst(), command.getIntegrationOfDailys());

		//throw business
		// 2. 修正した実績を登録する
		DataResultAfterIU dataResult = this.dailyModifyRCommandFacade.insertItemDomain(dataParent);
		
		// 3. 作業時間帯グループを登録する
		
		command.getWorkDetails().forEach(wd -> {

			RegisterTaskTimeGroupCommand cmd = new RegisterTaskTimeGroupCommand(command.getEmployeeId(), wd.getDate(),
					wd.toTimeZones());

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
