package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * 勤務台帳の表示内容を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerDisplayContentDomainService {

	/**
	 * 勤務台帳の表示内容を作成する
	 *
	 * @param require		Require
	 * @param datePeriod	期間
	 * @param empInfoList	List<社員情報>
	 *
	 * @return List<勤務台帳の帳票表示内容>
	 */
	public static List<WorkLedgerDisplayContent> createWorkLedgerDisplayContent(
			Require require,
			DatePeriod datePeriod,
			List<EmployeeBasicInfoImport> empInfoList,
			WorkLedgerOutputSetting workLedgerOutputSetting,
			List<WorkplaceInfor> workplaceInfoList) {

		// ① = <call> 社員の指定期間中の所属期間を取得する
		List<String> empIdList = new ArrayList<>();
		if(empInfoList != null && empInfoList.size() > 0) {
			for (val item: empInfoList) {
				empIdList.add(item.getSid());
			}
		}
		List<StatusOfEmployee> empStatusList = require.getAffiliateEmpListDuringPeriod(datePeriod, empIdList);

		//		for (val empInfo: empInfoList) {
		//			// AffCom	panyHist x =
		//		}



		return null;
	}

	public interface Require {
		/**
		 * 社員の指定期間中の所属期間を取得する
		 */
		List<StatusOfEmployee> getAffiliateEmpListDuringPeriod(DatePeriod datePeriod, List<String> empIdList);
	}
}
