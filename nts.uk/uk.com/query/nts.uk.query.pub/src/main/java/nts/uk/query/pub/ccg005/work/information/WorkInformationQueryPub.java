package nts.uk.query.pub.ccg005.work.information;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkInformationQueryPub {
	/**
	 * 
	 * @param sids 社員IDリスト
	 * @param baseDate 基準日
	 * @return List<EmployeeWorkInformationExport> List<社員の勤務情報>
	 */
	public List<EmployeeWorkInformationExport> getWorkInformationQuery(List<String> sids, GeneralDate baseDate);
}
