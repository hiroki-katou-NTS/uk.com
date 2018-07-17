package nts.uk.ctx.sys.auth.dom.grant.rolesetperson;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */

public interface RoleSetGrantedPersonRepository {

	public boolean checkRoleSetCdExist(String roleSetCd, String companyId);
	
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId);
	
	public Optional<RoleSetGrantedPerson> getByEmployeeId(String employeeId);
	
	public void insert(RoleSetGrantedPerson domain);
	
	public void update(RoleSetGrantedPerson domain);
	
	public void delete(String employeeId);
	
	public Optional<RoleSetGrantedPerson> findByIDAndDate (String companyId , String employeeID , GeneralDate date);
	
	public Optional<RoleSetGrantedPerson> findByDetail (String companyID , String employeeID , List<String> roleSetCDLst, GeneralDate date);
	
}
