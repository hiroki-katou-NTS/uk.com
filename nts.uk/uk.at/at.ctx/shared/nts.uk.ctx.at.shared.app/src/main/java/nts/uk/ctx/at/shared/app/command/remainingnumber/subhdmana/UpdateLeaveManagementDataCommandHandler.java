package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@Stateless
public class UpdateLeaveManagementDataCommandHandler
		extends CommandHandlerWithResult<LeaveManagementDataCommand, List<String>> {

	@Inject
	private AddSubHdManagementService addSubHdManaService;

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<LeaveManagementDataCommand> context) {
		return this.leaveManagementProcess(context.getCommand());
	}

	/**
	 * Ｌ．代休管理データの修正（休出設定）登録処理
	 * 
	 * @param command LeaveManagementDataCommand
	 * @return errorList
	 */
	private List<String> leaveManagementProcess(LeaveManagementDataCommand command) {
		// アルゴリズム「休出（年月日）チェック処理」を実行する
		List<String> errorList = this.addSubHdManaService.checkHoliday(command.getLeaveDate(), Optional.empty(),
				command.getClosureId());

		if (!errorList.isEmpty()) {
			return errorList;
		}

		// アルゴリズム「Ｌ．代休管理データの修正（休出設定）入力項目チェック処理」を実行する
		errorList.addAll(this.inputItemCheck(command));

		if (!errorList.isEmpty()) {
			return errorList;
		}

		// ドメインモデル「休出管理データ」の選択データを更新する
		leaveManaDataRepository.udpateByHolidaySetting(command.getLeaveId(), command.getIsCheckedExpired(),
				command.getExpiredDate(), command.getOccurredDays(), command.getUnUsedDays());

		// 結果情報に「エラーなし」を返す
		return Collections.emptyList();
	}

	/**
	 * Ｌ．代休管理データの修正（休出設定）入力項目チェック処理
	 * 
	 * @param command LeaveManagementDataCommand
	 * @return errorList
	 */
	private List<String> inputItemCheck(LeaveManagementDataCommand command) {
		List<String> errorList = new ArrayList<>();
		if (!command.getIsCheckedExpired()) {
			Optional<LeaveManagementData> domain = this.leaveManaDataRepository.getByLeaveId(command.getLeaveId());
			if (domain.isPresent()) {
				if (domain.get().getSubHDAtr().value == 2) {
					// エラーメッセージ(Msg_1212)をエラーリストにセットする
					errorList.add("Msg_1212");
				} else {
					// 期限切れチェック
					if (command.getLeaveDate().after(command.getExpiredDate())) {
						// エラーメッセージ(Msg_825)エラーリストにセットする
						errorList.add("Msg_825");
					}
				}
			}
		} else {
			// 入力画面「残数」をチェックする
			if (Double.valueOf(0).equals(command.getUnUsedDays())) {
				// エラーメッセージ(Msg_1213)をエラーリストにセットする
				errorList.add("Msg_1213");
			} else {
				// エラーメッセージ(Msg_1302)をエラーリストにセットする
				errorList.add("Msg_1302");
			}
		}
		return errorList;
	}
}
