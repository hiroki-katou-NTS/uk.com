package nts.uk.ctx.exio.dom.exi.csvimport;

import java.util.List;

public interface AcceptCsvErrorContentRepository {
	/**
	 * workエラーログを追加
	 * @param content　
	 */
	void add(List<AcceptCsvErrorContent> lstContent);
	/**
	 * workエラーログを削除
	 * @param cid　会社ID
	 * @param asynTaskId　非同期タスクID
	 */
	void delete(String cid, String asynTaskId);
	/**
	 * workエラーログを取得
	 * @param cid
	 * @param asynTaskId
	 * @return
	 */
	List<AcceptCsvErrorContent> getByAsynTaskId(String cid, String asynTaskId);
}
