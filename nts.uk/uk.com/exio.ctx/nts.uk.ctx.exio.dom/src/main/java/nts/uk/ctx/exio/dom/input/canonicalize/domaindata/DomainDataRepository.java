package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * ドメインのテーブルに対して直接アクセスする汎用Repository
 */
public interface DomainDataRepository {

	boolean exists(DomainDataId id);
	
	void delete(DomainDataId id);
	
	//ここの2つは履歴を汎用的に触るやつなので、このRepositoryにまとまっているのは不適切化も
	History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(DomainDataId id);
	void update(DomainDataId targetKey, DatePeriod period);
}
