package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana.LeaveManagementDataCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@Stateless
public class LeaveManagementDataFinder {

	@Inject
	private AddSubHdManagementService addSubHdManaService;

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	/**
	 * Ｌ．代休管理データの修正（休出設定）登録処理
	 * 
	 * @param command
	 *            LeaveManagementDataCommand
	 * @return errorList
	 */
	public List<String> leaveManagementProcess(LeaveManagementDataCommand command) {
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

		// 結果情報に「エラーなし」を返す
		return Collections.emptyList();
	}

	/**
	 * Ｌ．代休管理データの修正（休出設定）入力項目チェック処理
	 * 
	 * @param command
	 *            LeaveManagementDataCommand
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
					if (command.getUnknownDate() != 1 && !Objects.isNull(command.getLeaveDate())) {
						if (command.getLeaveDate().compareTo(command.getExpiredDate()) >= 0) {
							// エラーメッセージ(Msg_825)エラーリストにセットする
							errorList.add("Msg_825");
						}
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
