package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.canonicalize.history.ExternalImportHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryKeyColumnNames;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;

/**
 * ドメインのテーブルに対して直接アクセスする汎用Repository
 */
public interface DomainDataRepository {

	boolean exists(DomainDataId id);
	
	void delete(DomainDataId id);
	
	ExternalImportHistory getHistory(DomainDataId id, HistoryType historyType, HistoryKeyColumnNames keyColumnNames);
	
	void update(DomainDataId targetKey, DatePeriod period);
}
