package nts.uk.ctx.workflow.dom.adapter.bs;

import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;

/**
 * 社員IDに該当する社員を取得する
 * @author dudt
 *
 */
public interface PersonAdapter {
	/**
	 * get person name
	 * @param sID
	 * @return
	 */
	PersonImport getPersonInfo(String sID);
}
