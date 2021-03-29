package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;

/**
 * 社員の勤務種別を取得する
 * 
 * @author Trung Tran
 *
 */
public interface BusinessTypeEmpService {
	/**
	 * @param sId
	 *            社員ID：社員ID
	 * @param baseDate
	 *            基準日：年月日
	 * @return
	 */
	public List<BusinessTypeOfEmployee> getData(String sId, GeneralDate baseDate);

}
