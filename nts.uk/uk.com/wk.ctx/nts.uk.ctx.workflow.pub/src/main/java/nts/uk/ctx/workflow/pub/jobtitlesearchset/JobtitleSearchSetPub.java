package nts.uk.ctx.workflow.pub.jobtitlesearchset;

public interface JobtitleSearchSetPub {
	/**
	 * ドメインモデル「職位別のサーチ設定」を取得する
	 * 
	 * @param cid
	 * @param jobtitleId
	 * @return
	 */
	JobtitleSearchSetExport finById(String cid, String jobtitleId);
}
