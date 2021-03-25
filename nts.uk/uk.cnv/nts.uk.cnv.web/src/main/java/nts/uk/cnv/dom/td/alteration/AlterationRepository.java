package nts.uk.cnv.dom.td.alteration;

import java.util.List;

import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

public interface AlterationRepository {

	Alteration get(String alterationId);

	List<Alteration> gets(List<String> alterIds);

//	/**
//	 * テーブルの一覧に影響する未検収のおるたのみ取得
//	 * @return
//	 */
//	List<Alteration> getTableListChange();

	List<Alteration> getTable(String tableId, DevelopmentProgress progress);

	void insert(Alteration alt);

	List<Alteration> getByEvent(String eventId);
}
