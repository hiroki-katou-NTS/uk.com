package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.algorithm;

import java.util.List;

public interface IAlgorithm {
	/**
	 * [RQ641]給与雇用と締めのリストを取得する
	 * 
	 * @param 対象会社ID
	 * 
	 * @return 締め日リス Export
	 */

	public List<ClosureDateExport> GetClosingSalaryEmploymentList(String companyId);
}
