package nts.uk.ctx.at.request.dom.application.common.service.other;

import nts.arc.time.GeneralDate;

public interface ChangeInputAppDate {
	
	/**
	 * 申請日を変更する
	 * @return
	 */
	public GeneralDate changeInputAppDate(String cid,String sid,int employmentRootAtr,int appType,GeneralDate standardDate);
}
