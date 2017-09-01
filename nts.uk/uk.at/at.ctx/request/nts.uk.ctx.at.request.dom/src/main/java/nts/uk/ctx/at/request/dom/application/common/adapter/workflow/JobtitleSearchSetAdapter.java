package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetImport;

/**
 * 
 * @author vunv
 *
 */
public interface JobtitleSearchSetAdapter {
	/**
	 * ドメインモデル「職位別のサーチ設定」を取得する
	 * 
	 * @param cid
	 * @param jobtitleId
	 * @return
	 */
	JobtitleSearchSetImport finById(String cid, String jobtitleId);
}
