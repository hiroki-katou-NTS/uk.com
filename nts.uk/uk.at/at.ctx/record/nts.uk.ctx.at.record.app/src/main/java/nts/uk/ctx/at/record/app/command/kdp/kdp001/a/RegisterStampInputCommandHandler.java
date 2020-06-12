package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromPortalService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.shr.com.context.AppContexts;

public class RegisterStampInputCommandHandler
		extends CommandHandlerWithResult<RegisterStampInputCommand, RegisterStampInputResult> {

	@Inject
	private EnterStampFromPortalService portalService;

	@Override
	protected RegisterStampInputResult handle(CommandHandlerContext<RegisterStampInputCommand> context) {
		RegisterStampInputCommand cmd = context.getCommand();

		EnterStampFromPortalServiceRequireImpl require = new EnterStampFromPortalServiceRequireImpl();

		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());

		String employeeID = AppContexts.user().employeeId();

		GeneralDateTime datetime = cmd.getDatetime();

		ButtonPositionNo buttonPositionNo = new ButtonPositionNo(cmd.getButtonPositionNo());

		RefectActualResult refActualResults = cmd.getRefActualResults().toDomainValue();

		// 作成する(@Require, 契約コード, 社員ID, 日時, 打刻ボタン位置NO, 実績への反映内容)

		TimeStampInputResult inputResult = this.portalService.create(require, contractCode, employeeID, datetime,
				buttonPositionNo, refActualResults);

		if (inputResult == null) {
			return null;
		}

		// not 打刻入力結果 empty
		Optional<AtomTask> atomInput = inputResult.getAt();

		transaction.execute(() -> {
			atomInput.get().run();
		});

		if (inputResult.getStampDataReflectResult() == null) {
			return null;
		}

		// not 打刻データ反映処理結果 empty
		AtomTask atomResult = inputResult.getStampDataReflectResult().getAtomTask();

		transaction.execute(() -> {
			atomResult.run();
		});

		return new RegisterStampInputResult(inputResult.getStampDataReflectResult().getReflectDate());
	}

	private class EnterStampFromPortalServiceRequireImpl implements EnterStampFromPortalService.Require {

		@Override
		public Optional<PortalStampSettings> get(String comppanyID) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
