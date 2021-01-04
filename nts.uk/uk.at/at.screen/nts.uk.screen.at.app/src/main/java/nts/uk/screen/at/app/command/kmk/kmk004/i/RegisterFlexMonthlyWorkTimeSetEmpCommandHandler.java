package nts.uk.screen.at.app.command.kmk.kmk004.i;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別月単位労働時間（フレックス勤務）を登録する
 */
@Stateless
public class RegisterFlexMonthlyWorkTimeSetEmpCommandHandler
		extends CommandHandlerWithResult<SaveMonthlyWorkTimeSetEmpCommand, List<String>> {

	@Inject
	private SaveMonthlyWorkTimeSetEmpCommandHandler saveHandler;

	@Inject
	private EmploymentList employmentList;

	@Override
	protected List<String> handle(CommandHandlerContext<SaveMonthlyWorkTimeSetEmpCommand> context) {
		// 雇用別月単位労働時間を登録・更新する
		this.saveHandler.handle(context.getCommand());
		// 雇用リストを表示する

		return this.employmentList.get(LaborWorkTypeAttr.FLEX).stream().map(x-> x.employmentCode).collect(Collectors.toList());
	}

}
