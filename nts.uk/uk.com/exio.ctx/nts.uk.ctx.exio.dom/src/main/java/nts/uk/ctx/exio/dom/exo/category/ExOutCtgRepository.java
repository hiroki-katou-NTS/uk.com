package nts.uk.ctx.exio.dom.exo.category;

import java.util.List;
import java.util.Optional;

/**
 * 外部出力カテゴリ
 */
public interface ExOutCtgRepository {

	List<ExOutCtg> getAllExOutCtg();
	
	List<ExOutCtg> getExOutCtgList();

	Optional<ExOutCtg> getExOutCtgById(String categoryId);
	
	Optional<ExOutCtg> getExOutCtgByIdAndCtgSetting(Integer categoryId);
	
	List<List<String>> getData(String sql);

	void add(ExOutCtg domain);

	void update(ExOutCtg domain);

	void remove(int functionNo);

}
