package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

/**
 * 勤務台帳の設定を複製する
 *
 * @author khai.dh
 */
public class DuplicateWorkLedgerSettingDomainService {

	/**
	 * 設定を複製する
	 *
	 * @param settingCategory	設定区分: 帳票共通の設定区分
	 * @param dupSrcId			複製元の設定ID: 勤務状GUID
	 * @param dupCode			複製先のコード: 勤務状況の設定表示コード
	 * @param dupName			複製先の名称: 勤務状況の設定名称
	 * @return AtomTask
	 */
	public static AtomTask duplicateSetting(
			Require require,
			SettingClassificationCommon settingCategory,
			String dupSrcId,
			OutputItemSettingCode dupCode,
			OutputItemSettingName dupName) {

		val employeeId = AppContexts.user().employeeId();

		// 出力設定の詳細を取得する(会社ID, GUID)
		val optOutputSetting = require.getOutputItemDetail(dupSrcId);
		if(!optOutputSetting.isPresent()) {
			throw new BusinessException("Msg_1928");
		}

		boolean isDuplicated = false;
		if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
			isDuplicated = WorkLedgerOutputItem.checkDuplicateStandardSelection(require, dupCode);
		} else if (settingCategory == SettingClassificationCommon.FREE_SETTING){
			isDuplicated = WorkLedgerOutputItem.checkDuplicateFreeSettings(require, dupCode, employeeId);
		}

		if (isDuplicated) {
			throw new BusinessException("Msg_1927");
		}

		// ② IDを生成する
		String uid = IdentifierUtil.randomUniqueId();

		return AtomTask.of(() -> {
			require.duplicateWorkLedgerOutputItem(employeeId, dupSrcId, uid, dupCode, dupName);
		});
	}

	public interface Require extends WorkLedgerOutputItem.Require {
		/**
		 * Call 勤務台帳の出力項目Repository#出力設定の詳細を取得する
		 */
		Optional<WorkLedgerOutputItem> getOutputItemDetail(String id);

		/**
		 * Call 勤務台帳の出力項目Repository#設定の詳細を複製するる
		 */
		void duplicateWorkLedgerOutputItem(
				String employeeId,
				String dupSrcId,
				String dupDestId,
				OutputItemSettingCode dupCode,
				OutputItemSettingName dupName
		);
	}
}
