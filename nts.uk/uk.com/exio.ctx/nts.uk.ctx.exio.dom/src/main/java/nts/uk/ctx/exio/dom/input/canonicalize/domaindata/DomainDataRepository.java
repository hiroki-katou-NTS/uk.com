package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * ドメインのテーブルに対して直接アクセスする汎用Repository
 */
public interface DomainDataRepository {

	boolean exists(DomainDataId id);
	
	void delete(DomainDataId id);
	
	History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(DomainDataId id, HistoryType historyType);
	
	void update(DomainDataId targetKey, DatePeriod period);
}
