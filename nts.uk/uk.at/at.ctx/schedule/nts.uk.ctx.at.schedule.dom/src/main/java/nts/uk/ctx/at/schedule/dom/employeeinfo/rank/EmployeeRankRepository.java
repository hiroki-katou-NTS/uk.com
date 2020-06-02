package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.List;
import java.util.Optional;

/**
 * 社員ランクRepository
 * @author phongtq
 *
 */
public interface EmployeeRankRepository {

	// [3] insert(社員ランク）
	public void insert(EmployeeRank employeeRank);
	
	// [4] update(社員ランク)
	public void update(EmployeeRank employeeRank);
	
	// [5] delete(社員ID)
	public void delete(String sID);
	
	// [1] get(社員ID)
	Optional<EmployeeRank> getById(String sID);
	
	// [2] *get(List<社員ID>)
	List<EmployeeRank> getAll(List<String> lstSID);
	
	// [6] exists(社員ID）
	boolean exists(String sID);
}
