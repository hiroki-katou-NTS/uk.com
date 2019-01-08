package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;
import java.util.List;

/**
 * データ形式初期値
 */
public interface DataFormatInitRepository {

	List<DataFormatInit> getAllDataFormat();

	Optional<DataFormatInit> getDataFormatSetById(String cId);

	void add(DataFormatInit domain);

	void update(DataFormatInit domain);

	void remove();

	Optional<DataFormatInit> getDataFormatSetByCid(String cid);
}
