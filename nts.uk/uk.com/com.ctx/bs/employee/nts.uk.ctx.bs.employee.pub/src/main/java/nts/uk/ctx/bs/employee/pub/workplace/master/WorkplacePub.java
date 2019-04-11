package nts.uk.ctx.bs.employee.pub.workplace.master;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplacePub {

	/**
	 * [No.560]職場IDから職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listWorkplaceId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate);

}
