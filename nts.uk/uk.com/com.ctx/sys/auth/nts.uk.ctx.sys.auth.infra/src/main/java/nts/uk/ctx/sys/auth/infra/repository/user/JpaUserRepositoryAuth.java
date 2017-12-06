package nts.uk.ctx.sys.auth.infra.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.entity.JpaEntity;
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
				.getSingle(c -> toDomain(c));
	}

	@Override
	public List<User> getByListUser(List<String> userID) {
		
		List<User> data =  this.queryProxy()
				.query(SELECT_ALL_USER, SacmtUser.class)
				.getList(c -> toDomain(c));
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

	private User toDomain(SacmtUser entity) {
		return User.createFromJavatype(
				entity.sacmtUserPK.userID, 
				entity.defaultUser, 
				entity.password, 
				entity.loginID, 
				entity.contractCd, 
				entity.expirationDate, 
				entity.specialUser, 
				entity.multiCompanyConcurrent, 
				entity.mailAdd, 
				entity.userName, 
				entity.associatedPersonID);
	}

	@Override
	public List<User> searchUser(String userIDName, GeneralDate date) {
		return this.queryProxy().
				query(SELECT_BY_ID_OR_NAME, SacmtUser.class)
				.setParameter("userIDName", userIDName)
				.setParameter("date", date)
				.getList(c -> toDomain(c));
	}

}
