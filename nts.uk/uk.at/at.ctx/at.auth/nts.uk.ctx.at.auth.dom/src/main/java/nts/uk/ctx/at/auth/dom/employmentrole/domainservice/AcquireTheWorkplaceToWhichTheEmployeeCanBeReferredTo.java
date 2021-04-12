package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

/**
 * @author thanhpv
 * @name 参照可能社員の所属職場を取得するAdapter
 */
@Stateless
public class AcquireTheWorkplaceToWhichTheEmployeeCanBeReferredTo {
	
	@Inject
	private GetWorkplaceToWhichTheEmployeeReferredTo getWorkplaceToWhichTheEmployeeReferredTo;

	/**
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @return 	社員一覧	Map<社員ID,職場ID>
	 */
	public Map<String, String> getReferenceableEmployees(String userID, String employeeID, GeneralDate date) {
		//		return 参照可能社員の所属職場を取得するPublish.取得する(ユーザID,社員ID,基準日)	
		return getWorkplaceToWhichTheEmployeeReferredTo.get(userID, employeeID, date);
	}
}
