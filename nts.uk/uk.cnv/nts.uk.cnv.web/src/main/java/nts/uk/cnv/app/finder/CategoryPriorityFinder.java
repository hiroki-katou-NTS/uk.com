package nts.uk.cnv.app.finder;

import java.util.List;

import javax.inject.Inject;

import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;

public class CategoryPriorityFinder {

	@Inject
	CategoryPriorityRepository categoryPriorityRepository;

	public List<String> getCategories() {
		return categoryPriorityRepository.getAll();
	}
}
