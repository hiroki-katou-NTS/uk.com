/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkTypeRepository.
 */
public interface WorkTypeRepository {

	/**
	 * Gets the possible work type.
	 *
	 * @param companyId
	 *            the company id
	 * @param lstPossible
	 *            the lst possible
	 * @return the possible work type
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible);

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	List<WorkType> findByCompanyId(String companyId);
	
	/**
	 * 廃止されていない勤務種類をすべて取得する 
	 * @author danpv
	 * @param companyId
	 * @return
	 */
	List<WorkType> findByCompanyIdAndLeaveAbsence(String companyId);
	
	
	/**
	 *1日.休職
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	
	List<WorkType> findNotDeprecateByCompanyId(String companyId);

	/**
	 * Find not deprecated.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	List<WorkType> findNotDeprecated(String companyId);

	/**
	 * Find not deprecated by list code.
	 *
	 * @param companyId
	 *            the company id
	 * @param codes
	 *            the codes
	 * @return the list
	 */
	List<WorkType> findNotDeprecatedByListCode(String companyId, List<String> codes);
	
	/**
	 * Find work type by.
	 * allDayAtr, halfAtr = WorkTypeClassification
	 * 勤務種類の分類:
	 * 	0.出勤
	 * 	1.休日
	 * 	2.年休
	 * 	3.積立年休
	 * 	4.特別休暇
	 * 	5.欠勤
	 * 	6.代休
	 * 	7.振出
	 * 	8.振休
	 * 	9.時間消化休暇
	 * 	10.連続勤務
	 * 	11.休日出勤
	 * 	12.休職
	 * 	13.休業 
	 * abolishAtr = DeprecateClassification (0: 廃止しない, 1: 廃止する)   
	 * @param companyId the company id
	 * @param abolishAtr deprecate (廃止区分)
	 * @param allDayAtrs all day (勤務種類の分類 - 1日単位で勤務種類を登録する)
	 * @param halfAtrs half day (勤務種類の分類 - 半日単位で勤務種類を登録する)
	 * @return the list of Work Type
	 */
	List<WorkType> findWorkType(String companyId, int abolishAtr, List<Integer> allDayAtrs, List<Integer> halfAtrs);
	
	/**
	 * Find by companyId and workTypeCd.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTypeCd
	 *            the work type cd
	 * @return WorkType
	 */
	Optional<WorkType> findByPK(String companyId, String workTypeCd);
	
	/**
	 * 
	 * @param companyId
	 * @param workTypeCd
	 * @return
	 */
	List<WorkTypeSet> findWorkTypeSet(String companyId, String workTypeCode);
	
	/**
	 * Find work type set.
	 *
	 * @param companyId the company id
	 * @param closeAtr the close atr
	 * @return the list
	 */
	List<WorkTypeSet> findWorkTypeSetCloseAtr(String companyId, int closeAtr);
	
	/**
	 * Insert workType to DB
	 * 
	 * @param workType
	 */
	void add(WorkType workType);
	
	/**
	 * Update workType to DB
	 * 
	 * @param workType
	 */
	void update(WorkType workType);
	
	/**
	 * Insert workTypeSet to DB
	 * 
	 * @param workTypeSet
	 */
	void addWorkTypeSet(WorkTypeSet workTypeSet);
	
	/**
	 * Delete workTypeSet to DB
	 * 
	 * @param workTypeSet
	 */
	void removeWorkTypeSet(String companyId, String workTypeCd);
	
	/**
	 * Delete workType to DB
	 * 
	 * @param workTypeCd
	 */
	void remove(String companyId, String workTypeCd);
	/**
	 * @param companyId  会社ID
	 * @param abolishAtr 勤務種類.廃止する
	 * @param worktypeAtr １日の勤務
	 * @return the list of Work Type
	 */
	List<WorkType> findWorkOneDay(String companyId, int abolishAtr, int worktypeAtr);
}
