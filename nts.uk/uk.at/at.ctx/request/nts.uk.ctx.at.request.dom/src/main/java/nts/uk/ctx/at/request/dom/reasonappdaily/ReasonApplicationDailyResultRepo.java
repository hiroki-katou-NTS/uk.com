package nts.uk.ctx.at.request.dom.reasonappdaily;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;

public interface ReasonApplicationDailyResultRepo {

	// 日別実績の申請理由を取得する
	public List<ReasonApplicationDailyResult> findReasonAppDaily(String employeeId, GeneralDate date, PrePostAtr preAtr,
			ApplicationType apptype, Optional<OvertimeAppAtr> overAppAtr);

	// 日別実績の申請理由を追加する
	public void addUpdateReason(String cid, List<ReasonApplicationDailyResult> reason);

}
