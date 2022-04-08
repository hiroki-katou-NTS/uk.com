package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

public interface EmployeeUnregisterApprovalRoot {
	/**
	 * Refactor5
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.L:未登録社員リスト.アルゴリズム
	 * L01.承認ルート未登録の社員を取得する
	 * @param companyId
	 * @param baseDate
	 * @param lstNotice 
	 * @param lstEvent
	 * @param lstName
	 * @param sysAtr システム
	 * @return
	 */
	List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate, int sysAtr,
			List<Integer> lstNotice, List<String> lstEvent, List<AppTypeName> lstName);
	/**
	 * 就業の承認ルート未登録の社員を取得する
	 * @param cid
	 * @param period
	 * @param lstSid
	 * @return 社員ID　と　未登録申請名
	 */
	Map<String, List<String>> lstEmplUnregister(String cid, DatePeriod period, List<String> lstSid);
}
