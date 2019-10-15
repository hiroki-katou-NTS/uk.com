package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;

@Stateless
public class RegisterNewEmpCommandHandler extends CommandHandler<DataBeforeReflectCommand> {

	@Override
	protected void handle(CommandHandlerContext<DataBeforeReflectCommand> context) {
		DataBeforeReflectCommand command = context.getCommand();

		// アルゴリズム[事前チェック]を実行する (THực hiện thuật toán [Check trước ] )
		preCheck(command);

	}

	private boolean preCheck(DataBeforeReflectCommand command) {

		// [A222_12 退職日] と[A222_14 公開日]をチェックする ( Check [A222_14 Release date] và
		// [A222_12 Retirement date]
		
		GeneralDate releaseDate = GeneralDate.legacyDate(command.releaseDate.date());
		GeneralDate retirementDate = GeneralDate.legacyDate(command.retirementDate.date());
		
		if (retirementDate.beforeOrEquals(releaseDate)) {
			throw new BusinessException("MsgJ_JCM007_5");
		}
		return false;
	}
}
