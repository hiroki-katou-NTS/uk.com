package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public interface DataDeletionSelectionCategoryRepository {
	List<DataDeletionSelectionCategory> findByPatternCdAndPatternAtrAndSystemTypes(String patternCd, int patternAtr, List<Integer> systemTypes);
}
