package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCNTSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
public interface HoriTotalCategoryRepository {
	
	/**
	 * get all hori total category data
	 * @param companyId
	 * @return
	 */
	List<HoriTotalCategory> findAllCate(String companyId);
	
	/**
	 * get all hori cal day set
	 * @param companyId
	 * @return
	 */
	List<HoriCalDaysSet> findAllCal(String companyId);
	
	/**
	 * get all hori total cnt set
	 * @param companyId
	 * @return
	 */
	List<HoriTotalCNTSet> findAllCNT(String companyId);
	
	/**
	 * get hori total cnt set by PK
	 * @param companyId
	 * @param totalItemNo
	 * @param totalTimeNo
	 * @return
	 */
	List<HoriTotalCNTSet> findCNTSet(String companyId, String categoryCode, int totalItemNo);
	
	/**
	 * update a hori total category 
	 * @param aggregateCategory
	 */
	void updateCate(HoriTotalCategory horiTotalCategory);
	
	/**
	 * insert a hori total category
	 * @param aggregateCategory
	 */
	void insertCate(HoriTotalCategory horiTotalCategory);
	
	/**
	 * delete a hori total category
	 * @param companyId
	 * @param categoryCode
	 */
	void deleteCate(String companyId, String categoryCode);
	
	/**
	 * find a hori total category by categoryCode
	 * @param company
	 * @param categoryCode
	 * @return
	 */
	Optional<HoriTotalCategory> findCateByCode(String companyId, String categoryCode);
	
	/**
	 * find all total eval order
	 * @param companyId
	 * @return
	 */
	List<TotalEvalOrder> findAllOrder (String companyId);
	
	/**
	 * update list total eval order
	 * @param totalEvalOrders
	 */
	void updateOrder(List<TotalEvalOrder> totalEvalOrders);
	
	/**
	 * insert list total eval order
	 * @param totalEvalOrders
	 */
	void insertOrder(List<TotalEvalOrder> totalEvalOrders);
	
	/**
	 * update list cntSet
	 * @param cntSets
	 */
	void updateCNTSet(List<HoriTotalCNTSet> cntSets);
	
	/**
	 * insert list cntSet
	 * @param cntSets
	 */
	void insertCNTSet(List<HoriTotalCNTSet> cntSets);
	
	/**
	 * find list total eval order by code
	 * @param companyId
	 * @param categoryCode
	 * @param totalItemNo
	 * @return
	 */
	
	/**
	 * update a hori cal day set item 
	 * @param horiCalDaysSet
	 */
	void updateCalDaySet(HoriCalDaysSet horiCalDaysSet);
	
	/**
	 * insert a hori cal day set item
	 * @param horiCalDaysSet
	 */
	void insertCalDaySet(HoriCalDaysSet horiCalDaysSet);
	
	/**
	 * find a total eval order item
	 * @param companyId
	 * @param categoryCode
	 * @param totalItemNo
	 * @return
	 */
	List<TotalEvalOrder> findOrder(String companyId, String categoryCode, Integer totalItemNo);
	
	/**
	 * find hori cal day set by code  
	 * @param companyId
	 * @param categoryCode
	 * @return
	 */
	List<HoriCalDaysSet> findCalSet(String companyId, String categoryCode);	
	
	/**
	 * find all total eval item
	 * @param companyId
	 * @return
	 */
	List<TotalEvalItem> findAllItem (String companyId);
	
	/**
	 * find total eval item by code
	 * @param companyId
	 * @param totalItemNo
	 * @return
	 */
	List<TotalEvalItem> findEvalItem(String companyId, int totalItemNo);
}
