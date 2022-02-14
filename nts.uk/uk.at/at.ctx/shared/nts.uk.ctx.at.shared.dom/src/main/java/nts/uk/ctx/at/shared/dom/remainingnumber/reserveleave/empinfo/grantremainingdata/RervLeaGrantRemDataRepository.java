package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RervLeaGrantRemDataRepository {

	List<ReserveLeaveGrantRemainingData> find(String employeeId);
	
	List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId);
	
	List<ReserveLeaveGrantRemainingData> find(String employeeId, GeneralDate grantDate);
	
	Optional<ReserveLeaveGrantRemainingData> getById(String id);
	
	boolean checkValidateGrantDay(String sid, String rid, GeneralDate grantDate);
	
	void add(ReserveLeaveGrantRemainingData data);
	
	/**
	 * @author lanlt
	 * add all 
	 * @param cid
	 * @param domains
	 */
	void addAll(String cid, List<ReserveLeaveGrantRemainingData> domains);
	
	void update(ReserveLeaveGrantRemainingData data);
	
	void updateWithGrantDate(ReserveLeaveGrantRemainingData data);
	
	void delete(String rsvLeaId);
	
	/**
	 * getAll
	 * @param employeeId
	 * @param cId
	 * @return
	 */
	List<ReserveLeaveGrantRemainingData> getAll(String cid, List<String> sids);
	
}
