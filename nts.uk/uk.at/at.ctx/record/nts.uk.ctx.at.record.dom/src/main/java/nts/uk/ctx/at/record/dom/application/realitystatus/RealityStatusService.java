package nts.uk.ctx.at.record.dom.application.realitystatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.SendMailResultImport;
import nts.uk.ctx.at.record.dom.application.realitystatus.enums.ApprovalStatusMailType;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.EmpPerformanceOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.StatusWkpActivityOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.UseSetingOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.WkpIdMailCheckOutput;

public interface RealityStatusService {
	/**
	 * 承認状況職場実績起動
	 */
	List<StatusWkpActivityOutput> getStatusWkpActivity(List<String> listWorkplaceId, GeneralDate startDate,
			GeneralDate endDate, List<String> listEmpCd, boolean isConfirmData);

	/**
	 * 承認状況未確認メール送信
	 */
	String checkSendUnconfirmedMail(List<WkpIdMailCheckOutput> listWkp);
	
	/**
	 * 承認状況未確認メール送信実行
	 */
	SendMailResultImport exeSendUnconfirmMail(ApprovalStatusMailType type, List<WkpIdMailCheckOutput> listWkp, GeneralDate startDate,
			GeneralDate endDate, List<String> listEmpCd);
	/**
	 * 承認状況取得実績使用設定
	 */
	UseSetingOutput getUseSetting(String cid);
	
	/**
	 * 承認状況取得職場社員実績
	 */
	List<EmpPerformanceOutput> getAcquisitionWkpEmpPerformance(String wkpId, GeneralDate startDate, GeneralDate endDate, List<String> listEmpCd);
}
