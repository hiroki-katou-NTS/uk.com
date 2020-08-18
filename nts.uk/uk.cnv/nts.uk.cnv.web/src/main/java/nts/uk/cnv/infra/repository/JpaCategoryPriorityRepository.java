package nts.uk.cnv.infra.repository;

import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import  nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.infra.entity.categorypriority.ScvmtCategoryPriority;

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

}
