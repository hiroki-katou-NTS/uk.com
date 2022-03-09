package nts.uk.ctx.at.function.app.nrl.request;

import java.util.Optional;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.ConditionMonitor;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeRecordSettingImport;

/**
 * @author thanh_nx
 *
 *         リクエスト通信の状態監視を更新する
 */
public class ReqComStatusMonitoringProcess {

	public static void updateStatus(Require require, Command command, String contractCode, String terminalCode,
			ConditionMonitor statusConnect) {
		if (statusConnect == ConditionMonitor.START
				&& (command.onlyCreate() || command.simple())) {
			require.updateReqComStatusMonitor(contractCode, terminalCode, true);
			return;
		}

		if (statusConnect == ConditionMonitor.FINISH && command.canCreateAndDestroy()
				&& checkFinish(require, command, contractCode, terminalCode)) {
			if (!command.simple()) {
				require.removeAllSetting(terminalCode, contractCode);
			}
			require.updateReqComStatusMonitor(contractCode, terminalCode, false);
		}

		return;
	}

	// リクエストの終了を確認する
	private static boolean checkFinish(Require require, Command command, String contractCode, String terminalCode) {
		Optional<SendTimeRecordSettingImport> sendTimeRecordSetting = require.sendTimeRecordSetting(terminalCode,
				contractCode);
		int requestSettingFinishNumber = sendTimeRecordSetting.flatMap(x -> {
			try {
				return x.getRequestFinishWithTrue();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return Optional.empty();
			}
		}).orElse(-1);
		Optional<Command> findCommand = convertRequestToCommand(requestSettingFinishNumber);
		if (findCommand.map(x -> x == command).orElse(false)  || command.simple()) {
			return true;
		} else {
			return false;
		}
	}

	//リクエスト番号からコマンドの種類を取得
	private static Optional<Command> convertRequestToCommand(int requestSettingNumber) {

		switch (requestSettingNumber) {
		case 1:
			return Optional.of(Command.ALL_IO_TIME);
		case 2:
			return Optional.of(Command.TIMESET_INFO);
		case 3:
			return Optional.of(Command.ALL_RESERVATION);
		case 4:
			return Optional.of(Command.ALL_PETITIONS);
		case 6:
			return Optional.of(Command.PERSONAL_INFO);
		case 7:
			return Optional.of(Command.WORKTYPE_INFO);
		case 8:
			return Optional.of(Command.WORKTIME_INFO);
		case 9:
			return Optional.of(Command.OVERTIME_INFO);
		case 10:
			return Optional.of(Command.RESERVATION_INFO);
		case 11:
			return Optional.of(Command.APPLICATION_INFO);
		default:
			return Optional.empty();
		}
	}

	public static interface Require {

		// ReqComStatusMonitoringAdapter
		public void updateReqComStatusMonitor(String contractCode, String terminalCode, boolean statusConnect);

		//SendNRDataAdapter
		public Optional<SendTimeRecordSettingImport> sendTimeRecordSetting(String empInfoTerCode, String contractCode);

		//DeleteRequestSettingTRAdapter
		public void removeAllSetting(String empInfoTerCode, String contractCode);
	}

}
