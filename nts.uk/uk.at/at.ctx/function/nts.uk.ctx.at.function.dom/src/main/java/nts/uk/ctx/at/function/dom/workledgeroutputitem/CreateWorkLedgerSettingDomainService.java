package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * 勤務台帳の設定を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerSettingDomainService {

	/**
	 * 勤務台帳の設定を作成する
	 *
	 * @param require			Require
	 * @param code				コード：出力項目設定コード
	 * @param name				名称: 出力項目設定名称
	 * @param settingCategory	設定区分: 帳票共通の設定区分
	 * @param rankingList		List<順位>
	 * @param attendanceIdList	List<勤怠項目ID>
	 *
	 * @return AtomTask
	 */
	public AtomTask createWorkLedgerSetting(
			Require require,
			OutputItemSettingCode code,
			OutputItemSettingName name,
			SettingClassificationCommon settingCategory,
			List<Integer> rankingList,
			List<Integer> attendanceIdList) {

		return AtomTask.of(null);
	}

	public interface Require {

	}
}
