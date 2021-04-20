package nts.uk.ctx.at.record.app.command.workrecord.workrecord;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別時間帯別実績.App.作業工数を登録する.応援作業別勤怠時間帯を登録する
 * @author tutt
 *
 */
@Stateless
public class AddAttendanceTimeZoneCommandHandler extends CommandHandlerWithResult<AddAttendanceTimeZoneCommand, List<IntegrationOfDaily>> {

	@Override
	protected List<IntegrationOfDaily> handle(CommandHandlerContext<AddAttendanceTimeZoneCommand> context) {
		List<IntegrationOfDaily> result = new ArrayList<>();
		AddAttendanceTimeZoneCommand command = context.getCommand();
		
		//loop 年月日 in List<年月日,List<作業詳細>>
		List<WorkDetail> workDetails = command.getWorkDetails();
		
		//1: *登録する(対象者,年月日,編集状態,List<作業詳細>): 工数入力結果
		
		//2: persist()
				
		return result;
	}

}
