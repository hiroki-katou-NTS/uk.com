package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service;

import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;

public interface BaseDateGet {
	
	/**
	 * 基準日として扱う日の取得
	 * @param date 申請対象日(ApplicationDate)<Optional>
	 * @return
	 */
	public GeneralDate getBaseDate(Optional<GeneralDate> date, RecordDate recordDate);
}
