package nts.uk.cnv.infra.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.infra.entity.categorypriority.ScvmtCategoryPriority;

@Stateless
public class JpaCategoryPriorityRepository  extends JpaRepository implements CategoryPriorityRepository{

	@Inject
	ConversionCategoryTableRepository categryTableRepo;
	
	@Override
	public LinkedList<String> getAll() {
		String query = "SELECT cp FROM ScvmtCategoryPriority cp ORDER BY cp.sequenceNo ASC";
		return this.queryProxy().query(query, ScvmtCategoryPriority.class)
			.getList().stream()
				.map(cp -> cp.getCategoryName())
				.collect(Collectors.toCollection(LinkedList::new));
	}
	
	private Optional<ScvmtCategoryPriority> get(String category) {
		String query = "SELECT cp"
				+ " FROM ScvmtCategoryPriority cp"
				+ " WHERE cp.categoryName=:category"
				+ " ORDER BY cp.sequenceNo ASC";
		return this.queryProxy().query(query, ScvmtCategoryPriority.class)
				.setParameter("category", category)
				.getSingle();
	}

	@Override
	public void register(String category) {
		int seq;
		val categoryPriority = get(category);
		if(categoryPriority.isPresent()) {
			seq = categoryPriority.get().getSequenceNo();
			this.delete(category);
		}
		else {
			seq = nextSeqNo();
		}
		ScvmtCategoryPriority entity = new ScvmtCategoryPriority(category, seq);
		this.commandProxy().insert(entity);
		this.getEntityManager().flush();
	}

	@Override
	public void delete(String category) {
		this.commandProxy().remove(ScvmtCategoryPriority.class, category);
		this.getEntityManager().flush();
	}

	private int nextSeqNo() {
		String query = "SELECT MAX(SEQ_NO) FROM SCVMT_CATEGORY_PRIORITY";
		int latestSeqNo = this.jdbcProxy().query(query).getSingle(rec -> rec.getInt(1)).get();
		return latestSeqNo + 1;
	}

	@Override
	public void update(List<String> categories) {
		List<String> beforeCategories = getAll();
		
		List<String> addedCategories = beforeCategories.stream()
				.filter(before -> !categories.contains(before))
				.collect(Collectors.toList());
		
		String updateSeqQuery = "UPDATE SCVMT_CATEGORY_PRIORITY"
				+ " SET SEQ_NO=@seqNo"
				+ " WHERE CATEGORY_NAME=@category";
		for(int seqNo=0; seqNo<beforeCategories.size(); seqNo++) {
				
			String category = (seqNo < categories.size()) 
						? categories.get(seqNo)
						: addedCategories.get(seqNo - categories.size());
			this.jdbcProxy().query(updateSeqQuery)
				.paramInt("seqNo", seqNo)
				.paramString("category", category)
				.execute();
		}
	}
}
