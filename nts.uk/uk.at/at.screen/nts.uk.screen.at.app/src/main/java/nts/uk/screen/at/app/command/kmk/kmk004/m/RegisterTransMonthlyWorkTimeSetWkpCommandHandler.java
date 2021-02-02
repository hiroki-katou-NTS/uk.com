package nts.uk.screen.at.app.command.kmk.kmk004.m;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場別月単位労働時間（変形労働）を登録する
 * 
 * @author tutt
 *
 */
@Stateless
public class RegisterTransMonthlyWorkTimeSetWkpCommandHandler
		extends CommandHandlerWithResult<SaveMonthlyWorkTimeSetWkpCommand, List<WorkplaceIdDto>> {

	@Inject
	private SaveMonthlyWorkTimeSetWkpCommandHandler saveHandler;

	@Inject
	private WorkplaceList wkp;

	@Override
	public List<WorkplaceIdDto> handle(CommandHandlerContext<SaveMonthlyWorkTimeSetWkpCommand> context) {

		// 1: <call> 職場別月単位労働時間を登録・更新する
		this.saveHandler.handle(context.getCommand());

		// 2: <call> 職場リストを表示する
		return this.wkp.get(LaborWorkTypeAttr.DEFOR_LABOR);
	}

}
