package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

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

		//		for (val empInfo: empInfoList) {
		//			// AffCom	panyHist x =
		//		}
		return null;
	}

	public interface Require {

	}
}
