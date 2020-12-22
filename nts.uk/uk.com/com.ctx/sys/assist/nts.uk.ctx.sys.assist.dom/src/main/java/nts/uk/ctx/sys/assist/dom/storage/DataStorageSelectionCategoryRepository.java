package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

public interface DataStorageSelectionCategoryRepository {
	List<DataStorageSelectionCategory> findByPatternCdAndPatternAtrAndSystemTypes(String patternCd, 
																				  int patternAtr,
																				  List<Integer> systemTypes);
}
