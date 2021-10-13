package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
public class RegisterWorkContentHandler {

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

	public RegisterWorkContentDto registerWorkContent(RegisterWorkContentCommand command) {

		RegisterWorkContentDto result = new RegisterWorkContentDto();
		// 1. 実績登録パラメータを作成する

		DPItemParent dataParent = createDpItemQuery.CreateDpItem(command.getEmployeeId(), command.getChangedDates(),
				command.getManHrlst(), command.getIntegrationOfDailys());

		// 2. 修正した実績を登録する
		this.dailyModifyRCommandFacade.insertItemDomain(dataParent);
		
		// 6. 作業時間帯グループを登録する
		
		RegisterTaskTimeGroupCommand cmd = new RegisterTaskTimeGroupCommand();

		this.handler.handle(cmd);
		

		// 3. アラーム発生対象日を確認する

		checkAlarmTargetDate.checkAlarm(command.getEmployeeId(), command.getChangedDates());
		
		
		if(command.getMode() == 1){
			
			// 4.残業申請・休出時間申請の対象時間を取得する
			
			List<OvertimeLeaveTimeDto> ots = this.getTargetTime.get(command.getEmployeeId(),
					command.getChangedDates());
			
			result.setLstOvertimeLeaveTime(ots);
			
		}
		
		// 5. List<残業休出時間>.isPresent check dưới client

		return result;
	}
}
