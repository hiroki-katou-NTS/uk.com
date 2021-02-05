package nts.uk.screen.at.app.command.kmk.kmk004.j;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員別月単位労働時間（フレックス勤務）を登録する
 */
@Stateless
public class RegisterFlexMonthlyWorkTimeSetShaCommandHandler
		extends CommandHandlerWithResult<SaveMonthlyWorkTimeSetShaCommand, List<String>> {

	@Inject
	private SaveMonthlyWorkTimeSetShaCommandHandler saveMonthlyWorkTimeSetShaCommandHandler;

	@Inject
	private EmployeeList employeeList;

	@Override
	protected List<String> handle(CommandHandlerContext<SaveMonthlyWorkTimeSetShaCommand> context) {

		// 社員別月単位労働時間を登録・更新する
		this.saveMonthlyWorkTimeSetShaCommandHandler.handle(context.getCommand());

		// 社員リストを表示する
		return this.employeeList.get(LaborWorkTypeAttr.FLEX).stream().map(x-> x.employeeId).collect(Collectors.toList());
	}

}
