package nts.uk.ctx.sys.auth.infra.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;


import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.auth.dom.user.SearchUser;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.infra.entity.user.SacmtUser;

@Stateless
public class JpaUserRepositoryAuth extends JpaRepository implements UserRepository {
	
	private final String SELECT_BY_LOGIN_ID = "SELECT c FROM SacmtUser c WHERE c.loginID = :loginID";
	@Override
	public List<User> getByLoginId(String loginID) {
		return this.queryProxy().query(SELECT_BY_LOGIN_ID, SacmtUser.class).setParameter("loginID", loginID).getList(c->c.toDomain());
	}
	
	private final String SELECT_BY_CONTRACT_LOGIN_ID = "SELECT c FROM SacmtUser c WHERE c.contractCd = :contractCode AND c.loginID = :loginID";
	@Override
	public Optional<User> getByContractAndLoginId(String contractCode, String loginId) {
		return this.queryProxy().query(SELECT_BY_CONTRACT_LOGIN_ID, SacmtUser.class)
				.setParameter("contractCode", contractCode)
				.setParameter("loginID", loginId)
				.getSingle(c->c.toDomain());
	}

	private final String SELECT_BY_ASSOCIATE_PERSIONID = "SELECT c FROM SacmtUser c WHERE c.associatedPersonID = :associatedPersonID";
	@Override
	public Optional<User> getByAssociatedPersonId(String associatedPersonId) {
		return this.queryProxy().query(SELECT_BY_ASSOCIATE_PERSIONID, SacmtUser.class)
				.setParameter("associatedPersonID", associatedPersonId)
				.getSingle(c->c.toDomain());
	}

	private final String SELECT_BY_USER = "SELECT c FROM SacmtUser c" + " WHERE c.sacmtUserPK.userID = :userID";
	@Override
	public Optional<User> getByUserID(String userID) {
		return this.queryProxy().query(SELECT_BY_USER, SacmtUser.class).setParameter("userID", userID)
				.getSingle(c -> c.toDomain());
	}
	
	private final String SELECT_BY_KEY = "SELECT c From SacmtUser c"
			+ " WHERE c.expirationDate >= :systemDate"
			+ " AND c.specialUser = :specialUser "
			+ " AND c.multiCompanyConcurrent = :multiCompanyConcurrent";
	@Override
	public List<User> searchBySpecialAndMulti(GeneralDate systemDate, int special, int multi) {
		return this.queryProxy()
				.query(SELECT_BY_KEY,SacmtUser.class)
				.setParameter("systemDate", systemDate)
				.setParameter("specialUser", special)
				.setParameter("multiCompanyConcurrent", multi)
				.getList(c -> c.toDomain());
	}

	private final String SELECT_USER_BY_IDS = "SELECT c FROM SacmtUser c WHERE c.sacmtUserPK.userID IN :listUserID";
	@Override
	public List<User> getByListUser(List<String> listUserID) {
		List<User> datas = new ArrayList<>();
		CollectionUtil.split(listUserID, 1000, subIdList -> {
			datas.addAll(this.queryProxy().query(SELECT_USER_BY_IDS, SacmtUser.class)
					.setParameter("listUserID", subIdList).getList(c->c.toDomain()));
		});
		return datas;
	}

	private final String SELECT_BY_ID_OR_NAME = "SELECT c.sacmtUserPK.userID, c.loginID, c.userName, p.personName FROM SacmtUser c" 
			+ " LEFT JOIN BpsmtPerson p ON c.associatedPersonID = p.bpsmtPersonPk.pId"
			+ " WHERE (LOWER(c.loginID) LIKE LOWER(CONCAT('%', :userIDName, '%'))"
			+ " OR LOWER(c.userName) LIKE LOWER(CONCAT('%', :userIDName, '%'))"
			+ " OR LOWER(p.personName) LIKE LOWER(CONCAT('%', :userIDName, '%')))"
			+ " AND c.expirationDate >= :date";
	@Override
	public List<SearchUser> searchUser(String userIDName, GeneralDate date) {
		List<Object[]> data = this.queryProxy()
				.query(SELECT_BY_ID_OR_NAME, Object[].class)
				.setParameter("userIDName", userIDName)
				.setParameter("date", date).getList();
		
		List<SearchUser> result = new ArrayList<SearchUser>();
		for(val object: data) {
			String userID = object[0].toString();
			String loginID = object[1].toString();
			String personName = object[3] == null ? object[2].toString() : object[3].toString();
	/*		String perSon = "";
			if (object[3] == null){
				perSon = object[2].toString();
			}
			else
				perSon = object[3].toString();
			result.add(new SearchUser(userID, loginID, perSon));*/
		result.add(new SearchUser(userID, loginID, personName));
		}
		return result;
	}

	
	
	private final String SELECT_ALL_USER = "SELECT c FROM SacmtUser c ";
	@Override
	public List<User> getAllUser() {
		return this.queryProxy().query(SELECT_ALL_USER, SacmtUser.class).getList(c -> c.toDomain());
	}

	@Override
	public void addNewUser(User user) {
		this.commandProxy().insert(SacmtUser.toEntity(user));
		this.getEntityManager().flush();
	}
	
	private final String SELECT_USER_BY_LIST_AS_ID = "SELECT s FROM SacmtUser s WHERE s.associatedPersonID IN :listAssociatePersonId";
	@Override
	public List<User> getListUserByListAsID(List<String> listAssociatePersonId) {
		List<User> datas = new ArrayList<>();
		CollectionUtil.split(listAssociatePersonId, 1000, subIdList -> {
			datas.addAll(this.queryProxy().query(SELECT_USER_BY_LIST_AS_ID, SacmtUser.class)
					.setParameter("listAssociatePersonId", subIdList).getList(c->c.toDomain()));
		});
		return datas;
	}

	private final String SELECT_USER_BY_DEFUSER = "SELECT s FROM SacmtUser s WHERE s.sacmtUserPK.userID = :userID AND s.defaultUser = :defUser AND s.expirationDate = :expirationDate";
	@Override
	public Optional<User> getListUserByDefUser(String userID, int defUser, GeneralDate expirationDate) {
		return this.queryProxy().query(SELECT_USER_BY_DEFUSER , SacmtUser.class)
				.setParameter("userID", userID)
				.setParameter("defUser", 1)
				.setParameter("expirationDate", expirationDate).getSingle(c -> c.toDomain());
	}

	@Override
	public void update(User user) {
		SacmtUser entity = SacmtUser.toEntity(user);
	    this.commandProxy().update(entity);
	}

	private final String SELECT_ALL_USER_LIKE_NAME = "SELECT p.businessName, c From SacmtUser c LEFT JOIN BpsmtPerson p ON c.associatedPersonID = p.bpsmtPersonPk.pId "
			+ "WHERE c.expirationDate >= :systemDate "
			+ "AND c.specialUser = :specialUser "
			+ "AND c.multiCompanyConcurrent = :multiCompanyConcurrent " 
			+ "AND c.userName LIKE :key "
			+ "AND c.associatedPersonID IS NOT NULL";
	@Override
	public List<User> searchByKey(GeneralDate systemDate, int special, int multi, String key) {
		return this.queryProxy()
				.query(SELECT_ALL_USER_LIKE_NAME, Object[].class)
				.setParameter("systemDate", systemDate)
				.setParameter("specialUser", special)
				.setParameter("multiCompanyConcurrent", multi)
				.setParameter("key", '%' +key+ '%')
				.getList(c -> this.joinObjectToDomain(c));
	}

	private final String SELECT_MULTI_CONDITION = "SELECT c From SacmtUser c "
			+ "WHERE c.expirationDate >= :systemDate "
			+ "AND c.specialUser = :specialUser "
			+ "AND c.multiCompanyConcurrent = :multiCompanyConcurrent "
			+ "AND c.associatedPersonID IN :employeePersonId ";
	private final String SELECT_MULTI_AND_PERSON_ID = SELECT_MULTI_CONDITION 
			+ "AND (c.userName LIKE :key OR c.associatedPersonID IN :employeePersonIdFindName)";
	private final String SELECT_MULTI_NO_PERSON_ID = SELECT_MULTI_CONDITION 
			+ "AND c.userName LIKE :key ";
	@Override
	public List<User> searchUserMultiCondition(GeneralDate systemDate, int special, int multi, String key,
			List<String> employeePersonIdFindName, List<String> employeePersonId) {
		if(employeePersonId.isEmpty()) {
			return new ArrayList<>();
		}
		if(employeePersonIdFindName.isEmpty()) {
			return this.queryProxy()
					.query(SELECT_MULTI_NO_PERSON_ID,SacmtUser.class)
					.setParameter("systemDate", systemDate)
					.setParameter("specialUser", special)
					.setParameter("multiCompanyConcurrent", multi)
					.setParameter("employeePersonId", employeePersonId)
					.setParameter("key", '%' +key+ '%')
					.getList(c -> c.toDomain());
		}else {
			return this.queryProxy()
					.query(SELECT_MULTI_AND_PERSON_ID,SacmtUser.class)
					.setParameter("systemDate", systemDate)
					.setParameter("specialUser", special)
					.setParameter("multiCompanyConcurrent", multi)
					.setParameter("employeePersonId", employeePersonId)
					.setParameter("key", '%' +key+ '%')
					.setParameter("employeePersonIdFindName", employeePersonIdFindName)
					.getList(c -> c.toDomain());
		}
	}
	private User joinObjectToDomain(Object[] object) {
		String businessName =  object[0]==null?"":(String) object[0];
		SacmtUser user = (SacmtUser) object[1];
		return User.createFromJavatype(
				user.sacmtUserPK.userID, 
				user.defaultUser == 1, 
				user.password, 
				user.loginID, 
				user.contractCd, 
				user.expirationDate, 
				user.specialUser, 
				user.multiCompanyConcurrent, 
				user.mailAdd, 
				StringUtil.isNullOrEmpty(businessName, true)? user.userName : businessName, 
				user.associatedPersonID, 
				user.passStatus);
	}

	



}
