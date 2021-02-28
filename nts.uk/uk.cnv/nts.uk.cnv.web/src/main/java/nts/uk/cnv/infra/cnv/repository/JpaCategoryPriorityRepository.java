package nts.uk.cnv.infra.cnv.repository;

import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.cnv.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.infra.cnv.entity.categorypriority.ScvmtCategoryPriority;

@Stateless
public class JpaCategoryPriorityRepository  extends JpaRepository implements CategoryPriorityRepository{

	@Override
	public LinkedList<String> getAll() {
		String query = "SELECT cp FROM ScvmtCategoryPriority cp ORDER BY cp.sequenceNo ASC";
		return this.queryProxy().query(query, ScvmtCategoryPriority.class)
			.getList().stream()
				.map(cp -> cp.getCategoryName())
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void register(int seq, String category) {
		ScvmtCategoryPriority entity = new ScvmtCategoryPriority(category, seq);
		this.commandProxy().insert(entity);
	}

	@Override
	public void deleteAll() {
		String sqlDelete = "DELETE FROM SCVMT_CATEGORY_PRIORITY";
		this.jdbcProxy().query(sqlDelete).execute();
	}

	@Override
	public void delete(String category) {
		this.commandProxy().remove(ScvmtCategoryPriority.class, category);
	}

}
