package nts.uk.ctx.exio.dom.exi.csvimport;

import java.util.List;

public interface AcceptCsvContentRepository {
	/**
	 * 外部受入設定 （CSV内容エラーなし）を削除
	 * @param cid
	 * @param asynTaskId
	 */
	void removeAll(String cid, String asynTaskId);
	/**
	 * 外部受入CSV内容を追加
	 * @param lstContent
	 */
	void addAll(List<AcceptCsvContent> lstContent);
	/**
	 * 外部受入CSV内容を取得
	 * @param cid
	 * @param asynTaskId
	 */
	List<AcceptCsvContent> getAll(String cid, String asynTaskId);
}
