package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetAdaptorDto;

/**
 * 
 * @author vunv
 *
 */
public interface JobtitleSearchSetAdaptor {
	/**
	 * ドメインモデル「職位別のサーチ設定」を取得する
	 * 
	 * @param cid
	 * @param jobtitleId
	 * @return
	 */
	JobtitleSearchSetAdaptorDto finById(String cid, String jobtitleId);
}
