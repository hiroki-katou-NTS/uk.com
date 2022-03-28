package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.指定社員の中間データを作成する
 */
@Stateless
public class CreateEmployeeInterimDataDomainService {

	/**
	 * [1]作成する
	 * @param cid			会社ID
	 * @param sid			社員ID
	 * @param startDate		開始日
	 */
	public static void create(Require require, String cid, String sid, GeneralDate startDate) {
		// 指定社員の中間データを作成する(会社ID、社員ID、「1：就業日別確認」、開始日、開始日)）
		require.createDailyApprover(sid, RecordRootType.CONFIRM_WORK_BY_DAY, startDate, startDate);

		// 指定社員の中間データを作成する(会社ID、社員ID、「2：就業月別確認」、開始日、開始日)）
		require.createDailyApprover(sid, RecordRootType.CONFIRM_WORK_BY_MONTH, startDate, startDate);
	}
	
	public interface Require {
		void createDailyApprover(String sid, RecordRootType recordRootType, GeneralDate recordDate, GeneralDate closureStartDate);
	}
}
