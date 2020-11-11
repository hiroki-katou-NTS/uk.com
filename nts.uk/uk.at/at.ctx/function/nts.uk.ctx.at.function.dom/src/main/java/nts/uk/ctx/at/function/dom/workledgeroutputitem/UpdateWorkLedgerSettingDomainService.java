package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;

/**
 * 勤務台帳の設定を更新する
 *
 * @author khai.dh
 */
public class UpdateWorkLedgerSettingDomainService {

	/**
	 * 設定を更新する
	 *
	 * @param require			Require
	 * @param id				GUID
	 * @param code				コード：出力項目設定コード
	 * @param name				名称: 出力項目設定名称
	 * @param settingCategory	設定区分: 帳票共通の設定区分
	 * @param rankingList		List<順位>
	 * @param attendanceIdList	List<勤怠項目ID>
	 * @return AtomTask
	 */
	public static AtomTask updateSetting(
			Require require,
			String id,
			OutputItemSettingCode code, // TODO QA Add this param
			OutputItemSettingName name,
			SettingClassificationCommon settingCategory,
			List<Integer> rankingList,
			List<Integer> attendanceIdList) {

		val employeeId = AppContexts.user().employeeId();

		if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
			// 出力設定の詳細を取得する(会社ID, GUID)
			Optional<WorkLedgerOutputItem> outputSetting = require.getOutputSettingDetail(id);
			if(!outputSetting.isPresent()) {
				throw new BusinessException("Msg_1928");
			}
		}

		val attendanceListToPrint = AttendanceItemToPrint.createList(attendanceIdList, rankingList);
		AtomTask atomTask = AtomTask.of(null);
		if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
			val workLedgerOutputItem = WorkLedgerOutputItem.create(id, code, name, settingCategory);
			atomTask = AtomTask.of(() -> {
				require.updateWorkLedgerOutputItem(id, workLedgerOutputItem, attendanceListToPrint);
			});

		} else if (settingCategory == SettingClassificationCommon.FREE_SETTING){
			val workLedgerOutputItem = WorkLedgerOutputItem.create(id, employeeId, code, name, settingCategory);
			atomTask = AtomTask.of(() -> {
				require.updateWorkLedgerOutputItem(id, workLedgerOutputItem, attendanceListToPrint);
			});

		}

		return atomTask;
	}

	public interface Require {

		/**
		 * Call 勤務台帳の出力項目Repository#出力設定の詳細を取得する
		 */
		Optional<WorkLedgerOutputItem> getOutputSettingDetail(String id);

		/**
		 * Call 勤務台帳の出力項目Repository#出力設定の詳細を取得する
		 */
		void updateWorkLedgerOutputItem(String id,
				WorkLedgerOutputItem outputSetting,
				List<AttendanceItemToPrint> outputItemList
		);
	}
}
