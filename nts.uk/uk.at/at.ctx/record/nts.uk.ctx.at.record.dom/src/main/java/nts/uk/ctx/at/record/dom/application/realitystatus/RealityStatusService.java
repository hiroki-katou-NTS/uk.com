package nts.uk.ctx.at.record.dom.application.realitystatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.application.realitystatus.enums.TransmissionAttr;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.StatusWkpActivityOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.UseSetingOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.WkpIdMailCheckOutput;

public interface RealityStatusService {
	/**
	 * アルゴリズム「承認状況職場実績起動」を実行する
	 */
	List<StatusWkpActivityOutput> getStatusWkpActivity(List<String> listWorkplaceId, GeneralDate startDate,
			GeneralDate endDate, List<String> listEmpCd, boolean isConfirmData);

	/**
	 * アルゴリズム「承認状況未確認メール送信」を実行する
	 */
	void checkSendUnconfirmedMail(List<WkpIdMailCheckOutput> listWkp);
	
	/**
	 * アルゴリズム「承認状況未確認メール送信実行」を実行する
	 */
	void exeSendUnconfirmMail(TransmissionAttr type, List<WkpIdMailCheckOutput> listWkp, GeneralDate startDate,
			GeneralDate endDate, List<String> listEmpCd);
	/**
	 * アルゴリズム「承認状況取得実績使用設定」を実行する
	 */
	UseSetingOutput getUseSetting(String cid);
}
