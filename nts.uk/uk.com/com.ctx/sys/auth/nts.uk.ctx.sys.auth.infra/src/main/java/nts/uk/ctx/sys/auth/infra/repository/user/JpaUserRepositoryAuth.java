package nts.uk.ctx.sys.auth.infra.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.infra.entity.user.SacmtUser;

@Stateless
public class JpaUserRepositoryAuth extends JpaRepository implements UserRepository {
	private final String SELECT_ALL_USER = "SELECT c FROM SacmtUser c ";
	private final String SELECT_BY_USER = "SELECT c FROM SacmtUser c" 
			+ " WHERE c.sacmtUserPK.userID = :userID";
	private final String SELECT_BY_ID_OR_NAME  ="SELECT c From SacmtUser c"
			+ " WHERE (c.sacmtUserPK.userID LIKE CONCAT('%', :userIDName, '%')"
			+ " OR c.userName LIKE CONCAT('%', :userIDName, '%'))"
			+ " AND c.expirationDate >= :date";
	private final String SELECT_BY_KEY  ="SELECT c From SacmtUser c"
			+ " WHERE (LOWER(c.loginID) LIKE LOWER(CONCAT('%', :key, '%'))"
			+ " OR LOWER(c.userName) LIKE LOWER(CONCAT('%', :key, '%')))"
			+ " AND c.specialUser = :specialUser "
			+ " AND c.multiCompanyConcurrent = :multiCompanyConcurrent";

	@Override
	public Optional<User> getByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getByAssociatedPersonId(String associatedPersonId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getByUserID(String userID) {
		return this.queryProxy()
				.query(SELECT_BY_USER,SacmtUser.class)
				.setParameter("userID", userID)
				.getSingle(c -> c.toDomain());
	}
	
	@Override
	public List<User> findByKey(String key, boolean Special, boolean Multi) {
		int special = 0;
		if(Special) special = 1;
		int multi = 0;
		if(Multi) multi = 1;
		
		return this.queryProxy()
				.query(SELECT_BY_KEY,SacmtUser.class)
				.setParameter("key", key)
				.setParameter("specialUser", special)
				.setParameter("multiCompanyConcurrent", multi)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<User> getByListUser(List<String> userID) {
		
		List<User> data =  this.queryProxy()
				.query(SELECT_ALL_USER, SacmtUser.class)
				.getList(c -> c.toDomain());
		List<User> dataSelect = new ArrayList<>();
		for(String id :userID){
			for (User user : data){
				if(user.getUserID().equals(id)){
					dataSelect.add(user);
				}
			}
		}  
		
		return dataSelect;
	}

	@Override
	public List<User> searchUser(String userIDName, GeneralDate date) {
		return this.queryProxy().
				query(SELECT_BY_ID_OR_NAME, SacmtUser.class)
				
				
				.setParameter("userIDName", userIDName)
				.setParameter("date", date)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<User> getAllUser() {
		return this.queryProxy()
				.query(SELECT_ALL_USER, SacmtUser.class)
				.getList(c -> c.toDomain());
	}

}
