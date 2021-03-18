package nts.uk.cnv.dom.td.alteration;

import java.util.List;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

public interface AlterationRepository {

	/**
	 * テーブルの一覧に影響する未検収のおるたのみ取得
	 * @return
	 */
	List<Alteration> getTableListChange();
	
	List<Alteration> getTable(String tableId, DevelopmentProgress progress);

	void insert(Alteration alt);
}
