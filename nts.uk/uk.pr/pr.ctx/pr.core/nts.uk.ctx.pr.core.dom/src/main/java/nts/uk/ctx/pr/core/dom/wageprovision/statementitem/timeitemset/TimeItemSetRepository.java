package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author thanh.tq 勤怠項目設定
 *
 */
public interface TimeItemSetRepository {

	List<TimeItemSet> getAllTimeItemSt();

	Optional<TimeItemSet> getTimeItemStById(String cid, String salaryItemId);

	void add(TimeItemSet domain);

	void update(TimeItemSet domain);

	void updateAll(List<String> lstSalaryId);

	void remove(String cid, String salaryItemId);

}
