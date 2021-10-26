package nts.uk.cnv.dom.categorypriority;

import java.util.LinkedList;
import java.util.List;

public interface CategoryPriorityRepository {

	LinkedList<String> getAll();

	void register(String category);

	void delete(String category);

	void update(List<String> categories);
}
