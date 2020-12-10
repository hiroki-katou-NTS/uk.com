package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;

/**
 * @author thanh_nx
 *
 *         実績の締め確定をチェックする
 */
public class CheckAchievementConfirmation {

	public static ConfirmClsStatus check(Require require, String companyId, String employeeId, GeneralDate baseDate,
			int closureId) {

		// [RQ30]社員所属職場履歴を取得
		SWkpHistImport sWkpHistImport = require.getSWkpHistByEmployeeID(employeeId, baseDate);

		// [No.571]職場の上位職場を基準職場を含めて取得する
		List<String> lstWplId = require.getWorkplaceIdAndUpper(companyId, baseDate, sWkpHistImport.getWorkplaceId());

		// 取得した職場IDでループ
		for (String wplId : lstWplId) {

			// [RQ674]対象の職場が就業確定されているかチェックする
			boolean confirm = require.getEmploymentFixedStatus(companyId, baseDate, wplId, closureId);

			if (confirm)
				return ConfirmClsStatus.Confirm;
		}

		// 就業確定状態を返す
		return ConfirmClsStatus.Pending;
	}

	public static interface Require {

		// EmployeeRequestAdapter
		public SWkpHistImport getSWkpHistByEmployeeID(String employeeId, GeneralDate baseDate);

		// EmployeeRequestAdapter
		public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId);

		// WorkFixedAdapter --- same No674
		public boolean getEmploymentFixedStatus(String companyID, GeneralDate date, String workPlaceID, int closureID);

	}

	public static enum ConfirmClsStatus {

		/** The confirm. */
		// 確定
		Confirm(1, "Enum_ConfirmClsStatus_CONFIRM"),

		/** The pending. */
		// 未確定
		Pending(0, "Enum_ConfirmClsStatus_PENDING");

		/** The value. */
		public int value;

		/** The name id. */
		public String nameId;

		/** The Constant values. */
		private final static ConfirmClsStatus[] values = ConfirmClsStatus.values();

		private ConfirmClsStatus(int value, String nameId) {
			this.value = value;
			this.nameId = nameId;
		}

		private ConfirmClsStatus(int value) {
			this.value = value;
		}

		public static ConfirmClsStatus valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (ConfirmClsStatus val : ConfirmClsStatus.values) {
				if (val.value == value) {
					return val;
				}
			}
			// Not found.
			return null;
		}
	}
}
