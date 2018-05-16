/**
 * 
 */
package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddSubHdManagementService {
	@Inject
	ClosureRepository repoClosure;
	/**
	 * @param subHdManagementData
	 */
	public List<String> addProcessOfSHManagement(SubHdManagementData subHdManagementData) {
		List<String> errorList = addSubSHManagement(subHdManagementData);
		if (errorList.isEmpty()) {

			return Collections.emptyList();
		}
		return errorList;
	}

	/**
	 * 代休管理データの新規追加入力項目チェック処理
	 * 
	 * @param subHdManagementData
	 */
	private List<String> addSubSHManagement(SubHdManagementData subHdManagementData) {
		if (subHdManagementData.getCheckedHoliday() == true) {
			checkHoliday();
		}
		if (subHdManagementData.getCheckedHoliday() == false) {
			
		}
		return Collections.emptyList();
	}

	/**
	 * 休出（年月日）チェック処理
	 */
	private void checkHoliday() {
		//ドメインモデル「締め」を読み込む
		
	}
}
