package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

/**
 * 勤務台帳の設定を複製する
 *
 * @author khai.dh
 */
public class DuplicateWorkLedgerSettingDomainService {

	/**
	 * 勤務台帳の設定を複製する
	 *
	 * @param settingCategory		設定区分: 帳票共通の設定区分
	 * @param dupSourceSettingID	複製元の設定ID: 勤務状GUID
	 * @param destCode				複製先のコード: 勤務状況の設定表示コード
	 * @param destName				複製先の名称: 勤務状況の設定名称
	 *
	 * @return AtomTask
	 */
	public AtomTask updateWorkLedgerSetting(
			SettingClassificationCommon settingCategory,
			String dupSourceSettingID,
			String destCode,
			String destName) {

		return AtomTask.of(null);
	}

	public interface Require {

	}
}
