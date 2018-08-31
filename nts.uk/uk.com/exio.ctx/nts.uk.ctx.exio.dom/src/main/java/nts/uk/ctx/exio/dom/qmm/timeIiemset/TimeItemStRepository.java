package nts.uk.ctx.exio.dom.qmm.timeIiemset;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author thanh.tq 勤怠項目設定
 *
 */
public interface TimeItemStRepository {

	List<TimeItemSt> getAllTimeItemSt();

	Optional<TimeItemSt> getTimeItemStById(String cid, String salaryItemId);

	void add(TimeItemSt domain);

	void update(TimeItemSt domain);

	void remove(String cid, String salaryItemId);

}
