package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;

@Stateless
public class PreCheckCommandHandler extends CommandHandler<DataBeforeReflectCommand> {

	@Override
	protected void handle(CommandHandlerContext<DataBeforeReflectCommand> context) {

		// [A222_12 退職日] と[A222_14 公開日]をチェックする ( Check [A222_14 Release date] và
		// [A222_12 Retirement date]

		DataBeforeReflectCommand command = context.getCommand();
		
		GeneralDate retirementDate = GeneralDate.legacyDate(command.retirementDate.date()); // A222_12
		GeneralDate releaseDate = GeneralDate.legacyDate(command.releaseDate.date()); // A222_14
		GeneralDate dismissalNoticeDate = GeneralDate.legacyDate(command.dismissalNoticeDate.date()); // A222_35

		BundledBusinessException bundleExeption = BundledBusinessException.newInstance();
		int index = 0;

		if (retirementDate.beforeOrEquals(releaseDate)) {
			index++;
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_2"));
		}
		/**
		 * { value: 1, text: '退職' }, { value: 2, text: '転籍' }, { value: 3, text:
		 * '解雇' }, { value: 4, text: '定年' }
		 */
		int retirementType = command.retirementType;
		if (retirementType == 3) {
			if (retirementDate.beforeOrEquals(dismissalNoticeDate)) {
				index++;
				bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_3"));
			}
		}

		if (index > 0) {
			throw bundleExeption;
		}
	}

}
