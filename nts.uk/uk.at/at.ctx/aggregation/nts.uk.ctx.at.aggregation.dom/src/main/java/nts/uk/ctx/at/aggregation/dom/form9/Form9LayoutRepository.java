package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.List;
import java.util.Optional;

public interface Form9LayoutRepository {
	/**
	 * get
	 * @param companyId 会社ID
	 * @param form9Code コード
	 * @return 
	 */
	Optional<Form9Layout> get(String companyId, Form9Code form9Code);
	
	/**
	 * 出力レイアウト種類を指定して出力レイアウトを取得する
	 * @param companyId 会社ID
	 * @param isSystemFixed システム固定か
	 * @return
	 */
	List<Form9Layout> getLayoutByIsSystemFixed(String companyId, boolean isSystemFixed);

	/**
	 * 利用する出力レイアウトをすべて取得する
	 * @param companyId 会社ID
	 * @return
	 */
	List<Form9Layout> getAllLayoutUse(String companyId);
	
	/**
	 * ユーザー定義のレイアウトを追加する(会社ID, 様式９の出力レイアウト）
	 * @param companyId 会社ID
	 * @param layout 様式９の出力レイアウト
	 */
	void insertLayoutOfUser(String companyId, Form9Layout layout);
	
	/**
	 * ユーザー定義のレイアウトを変更する(会社ID, 様式９の出力レイアウト）
	 * @param companyId 会社ID
	 * @param layout 様式９の出力レイアウト
	 */
	void updateLayoutOfUser(String companyId, Form9Layout layout);
	
	/**
	 * ユーザー定義のレイアウトを削除する
	 * @param companyId 会社ID
	 * @param code 様式９のコード
	 */
	void deleteLayoutOfUser(String companyId, Form9Code code);
	
	/**
	 * システム固定のレイアウトの利用するを変更する
	 * @param companyId 会社ID
	 * @param code 様式９コード
	 * @param isUse 利用区分
	 */
	void updateUseOfLayoutSystem(String companyId, Form9Code code, boolean isUse);
}
