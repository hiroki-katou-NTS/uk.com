package nts.uk.ctx.hr.develop.dom.interview.service;

import java.util.List;

public interface IInterviewRecordSummary {

	/**
	 * 面談記録概要の取得
	 * [Summary]
	 *	・会社ID、面談区分、社員IDリスト、その他パラメータから該当する面談記録の概要情報を返す
	 *	Tra ve thong tin tom tat cua interviewRecord tuong duong tu Company ID, interview category, employee ID list, param khac
	 * @param companyID 会社ID // (company ID)
	 * @param interviewCate 面談区分
	 * @param listEmployeeID 社員IDリスト
	 * @param getSubInterview サブ面談者を取得する
	 * @param getDepartment 部門を取得する
	 * @param getPosition 職位を取得する
	 * @param getEmployment 雇用を取得する
	 * @return
	 * ・面談記録有無リスト{社員ID、有無}
	 * ・面談記録情報リスト {社員ID、面談記録ID、面談日、メイン面談者社員ID、社員コード、ビジネスネーム、ビジネスネームカナ、部門コード、部門表示名、職位コード、職位名称、雇用コード、雇用名称}
	 * ・サブ面談者社員情報リスト{面談記録ID、サブ面談者社員ID、社員コード、ビジネスネーム、ビジネスネームカナ、部門コード、部門表示名、職位コード、職位名称、雇用コード、雇用名称} 
	 */
	InterviewSummary getInterviewInfo(String companyID, int interviewCate, List<String> listEmployeeID,
			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment);
}
