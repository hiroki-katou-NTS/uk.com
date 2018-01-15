package nts.uk.screen.at.app.worktype;

import java.util.List;


public interface WorkTypeQueryRepository {
	
	/**
	 * Find work type by company 
	 * @param companyId
	 * @return
	 */
	List<WorkTypeDto> findAllWorkType(String companyId);
	
	/**
	 * Find work type by company and work attribute (分類)
	 * @param companyId
	 * @param workTypeAtrList 分類
	 * @return
	 */
	List<WorkTypeDto> findAllWorkType(String companyId, List<Integer> workTypeAtrList);

	List<WorkTypeDto> findAllWorkTypeSPE(String companyId, int abolishAtr, int oneDayAtr1, int oneDayAtr2);
}
