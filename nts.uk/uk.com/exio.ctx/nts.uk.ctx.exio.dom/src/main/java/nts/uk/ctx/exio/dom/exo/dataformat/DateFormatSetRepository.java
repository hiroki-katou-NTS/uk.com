package nts.uk.ctx.exio.dom.exo.dataformat;

import java.util.Optional;
import java.util.List;

/**
 * 日付型データ形式設定
 */
public interface DateFormatSetRepository {

	List<DateFormatSet> getAllDateFormatSet();

	Optional<DateFormatSet> getDateFormatSetById(String cId);

	void add(DateFormatSet domain);

	void update(DateFormatSet domain);

	void remove();

	Optional<DateFormatSet> getDateFormatSetByCid(String cid);
}
