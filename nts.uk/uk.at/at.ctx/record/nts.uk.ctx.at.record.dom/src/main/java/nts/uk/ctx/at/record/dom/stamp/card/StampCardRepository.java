package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.List;

//<<<<<<< HEAD:nts.uk/uk.at/at.ctx/record/nts.uk.ctx.at.record.dom/src/main/java/nts/uk/ctx/at/record/dom/stamp/card/StampCardRepository.java
//public interface StampCardRepository {
//=======
public interface StampCardtemRepository {
	
	//rename file StampCardRepository -> StampCardtemRepository
	
//>>>>>>> pj/at/dev/Team_B/zansu4:nts.uk/uk.at/at.ctx/record/nts.uk.ctx.at.record.dom/src/main/java/nts/uk/ctx/at/record/dom/stamp/card/StampCardtemRepository.java
	/**
	 *  Get List Card by Person ID
	 * @param employeeID
	 * @return
	 */
	List<StampCardItem> findByEmployeeID(String employeeID);
	/**
	 * Get List Card by List employee ID
	 * @param lstEmployeeID
	 * @return
	 */
	List<StampCardItem> findByListEmployeeID(List<String> lstEmployeeID);
	
}
