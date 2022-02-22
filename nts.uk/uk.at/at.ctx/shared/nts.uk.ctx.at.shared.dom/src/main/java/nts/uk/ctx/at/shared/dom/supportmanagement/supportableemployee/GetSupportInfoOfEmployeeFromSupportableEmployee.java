package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;

/**
 * 応援可能な社員から社員の応援情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.応援可能な社員から社員の応援情報を取得する
 * @author dan_pv
 */
public class GetSupportInfoOfEmployeeFromSupportableEmployee {
	
	/**
	 * 取得する
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @return
	 */
	public static SupportInfoOfEmployee get(Require require, EmployeeId employeeId, GeneralDate date) {
		
		val affiliationOrg = GetTargetIdentifiInforService.get(require, date, employeeId.v());
		
		val supportableEmployeeList = require.getSupportableEmployee(employeeId, date);
		if ( supportableEmployeeList.isEmpty() ) {
			return SupportInfoOfEmployee.createWithoutSupport(employeeId, date, affiliationOrg);
		}
		
		if ( supportableEmployeeList.get(0).getSupportType() == SupportType.ALLDAY ) {
			return SupportInfoOfEmployee.createWithAllDaySupport(employeeId, date, affiliationOrg, supportableEmployeeList.get(0).getRecipient());
		} else {
			val recipientList = supportableEmployeeList.stream()
					.map(SupportableEmployee::getRecipient)
					.collect(Collectors.toList());
			return SupportInfoOfEmployee.createWithTimezoneSupport(employeeId, date, affiliationOrg, recipientList);
		}
	}
	
	public static interface Require extends GetTargetIdentifiInforService.Require {
		
		/**
		 * 応援可能な社員を取得する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		List<SupportableEmployee> getSupportableEmployee(EmployeeId employeeId, GeneralDate date);
	}

}
