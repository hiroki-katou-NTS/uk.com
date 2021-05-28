package nts.uk.cnv.dom.categorypriority;

import java.util.LinkedList;

public interface CategoryPriorityRepository {

	LinkedList<String> getAll();

	void register(int seq, String categories);

	void deleteAll();

	void delete(String category);
}
