package nts.uk.cnv.dom.td.alteration;

import java.util.List;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

public interface AlterationRepository {

	/**
	 * テーブルの一覧に影響する未検収のおるたのみ取得
	 * @return
	 */
	List<Alteration> getTableListChange();

	/**
	 * 指定テーブルの未検収のすべてのおるたを取得する
	 * @param featureId
	 * @return
	 */
	List<Alteration> getUnaccepted(String tableId);

	void insert(Alteration alt);

	List<AlterationSummary> getAllUndeliveled(String featureId);

	List<AlterationSummary> getOlderUndeliveled(String alterId);

}
