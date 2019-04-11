package nts.uk.ctx.bs.employee.pub.deparment.master;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DepartmentPub {

	/**
	 * [No.563]部門IDから部門の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listDepartmentId
	 * @param baseDate
	 * @return
	 */
	public List<DepartmentInforExport> getDepartmentInforByDepIds(String companyId, List<String> listDepartmentId,
			GeneralDate baseDate);

}
