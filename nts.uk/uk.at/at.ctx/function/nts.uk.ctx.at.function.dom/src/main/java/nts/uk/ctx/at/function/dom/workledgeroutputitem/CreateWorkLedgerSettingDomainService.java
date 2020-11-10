package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 勤務台帳の設定を作成する
 *
 * @author khai.dh
 */
@Stateless
public class CreateWorkLedgerSettingDomainService {

	@Inject
	private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

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

		val cid = AppContexts.user().companyId();
		val employeeId = AppContexts.user().employeeId();
		val uid = IdentifierUtil.randomUniqueId();

		boolean checkResult = false;
		if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
			// 定型選択の重複をチェックする(コード, ログイン会社ID)
			checkResult = WorkLedgerOutputItem.checkDuplicateStandardSelection(require, code);
		} else if (settingCategory == SettingClassificationCommon.FREE_SETTING){
			// 自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
			checkResult = WorkLedgerOutputItem.checkDuplicateFreeSettings(require, code, employeeId);
		}

		if (checkResult) {
			throw new BusinessException("Msg_1927");
		}

		val attendanceListToPrint = AttendanceItemToPrint.createList(attendanceIdList, rankingList);
		AtomTask atomTask = AtomTask.of(null);
		if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
			val workLedgerOutputItem = WorkLedgerOutputItem.create(uid, code, name, settingCategory);
			atomTask = AtomTask.of(() -> {
				workLedgerOutputItemRepo.createNew(cid, workLedgerOutputItem, attendanceListToPrint);
			});
		} else if (settingCategory == SettingClassificationCommon.FREE_SETTING){
			val workLedgerOutputItem = WorkLedgerOutputItem.create(uid, employeeId, code, name, settingCategory);
			atomTask = AtomTask.of(() -> {
				workLedgerOutputItemRepo.createNew(cid, workLedgerOutputItem, attendanceListToPrint);
			});
		}

		return atomTask;
	}

	public interface Require extends WorkLedgerOutputItem.Require{

	}
}
