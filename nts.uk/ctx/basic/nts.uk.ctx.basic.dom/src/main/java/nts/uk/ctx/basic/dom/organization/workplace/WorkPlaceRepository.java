package nts.uk.ctx.basic.dom.organization.workplace;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.primitive.Memo;

public interface WorkPlaceRepository {
	
	void add(WorkPlace workPlace);
	
	void update(WorkPlace workPlace);
	
	void remove(String companyCode, WorkPlaceCode workPlaceCode, String historyId);
	
	void registerMemo(String companyCode, String historyId, Memo memo);
	
	Optional<WorkPlace> findSingle(String companyCode, WorkPlaceCode workPlaceCode, String historyId);
	
	List<WorkPlace> findAllByHistory(String companyCode, String historyId);
	
	List<WorkPlace> findHistories(String companyCode);
	
	boolean checkExist(String companyCode);
	
	Optional<WorkPlaceMemo> findMemo(String companyCode, String historyId);
	
	boolean isDuplicateWorkPlaceCode(String companyCode, WorkPlaceCode workPlaceCode);

}
