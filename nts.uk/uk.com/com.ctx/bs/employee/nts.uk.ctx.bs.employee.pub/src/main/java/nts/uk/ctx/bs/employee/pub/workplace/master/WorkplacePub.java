package nts.uk.ctx.bs.employee.pub.workplace.master;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplacePub {

	/**
	 * [No.559]運用している職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInforExport> getAllActiveWorkplaceInfor(String companyId, GeneralDate baseDate);

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

	/**
	 * [No.561]過去の職場の情報を取得する
	 * 
	 * @param companyId
	 * @param historyId
	 * @param listWorkplaceId
	 * @return
	 */
	public List<WorkplaceInforExport> getPastWorkplaceInfor(String companyId, String historyId,
			List<String> listWorkplaceId);
	
}
