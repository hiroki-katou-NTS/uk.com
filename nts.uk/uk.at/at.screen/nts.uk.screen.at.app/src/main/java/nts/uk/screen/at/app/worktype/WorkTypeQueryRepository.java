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
	
	/**
	 * Find work type by company and work attribute (分類)
	 * @param companyId
	 * @param workTypeAtrList 分類
	 * @return
	 */
	List<WorkTypeDto> findWorkType(String companyId, List<Integer> workTypeAtrList);

	/**
	 *  Find All Work Type by companyId, abolishAtr, oneDayAtr1, oneDayAtr2
	 * @param companyId
	 * @param abolishAtr
	 * @param oneDayAtr1
	 * @param oneDayAtr2
	 * @return
	 */
	List<WorkTypeDto> findAllWorkTypeSPE(String companyId, int abolishAtr, int oneDayAtr1, int oneDayAtr2);

	/**
	 *  Find All Work Type by companyId, abolishAtr
	 * @param companyId
	 * @param abolishAtr
	 * @return
	 */
	List<WorkTypeDto> findAllWorkTypeDisp(String companyId, int abolishAtr);
}
