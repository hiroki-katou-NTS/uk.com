package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.List;

public interface StampCardRepository {
	// Get List Card by Person ID
	List<StampCardItem> findByPersonID(String companyId,String PID);
	
	// Get List Card by List Person ID
	List<StampCardItem> findByListPersonID(String companyId,List<String> LstPID);
	
}
