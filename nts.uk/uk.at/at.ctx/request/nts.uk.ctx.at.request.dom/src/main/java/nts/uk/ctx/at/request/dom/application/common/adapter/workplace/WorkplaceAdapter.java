package nts.uk.ctx.at.request.dom.application.common.adapter.workplace;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author hoatt
 *
 */
public interface WorkplaceAdapter {

	/**
	 * アルゴリズム「社員から職場を取得する」を実行する
	 * @param sID
	 * @param date
	 * @return
	 */
	public WkpHistImport findWkpBySid(String sID, GeneralDate date);
	
}
