package nts.uk.ctx.sys.auth.infra.repository.user;

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
		return this.queryProxy().query(SELECT_USER_BY_IDS, SacmtUser.class)
				.setParameter("listUserID", listUserID)
				.getList(c -> c.toDomain());
	}

	private final String SELECT_BY_ID_OR_NAME = "SELECT c From SacmtUser c"
			+ " WHERE (LOWER(c.sacmtUserPK.userID) LIKE LOWER(CONCAT('%', :userIDName, '%'))"
			+ " OR LOWER(c.userName) LIKE LOWER(CONCAT('%', :userIDName, '%')))"
			+ " AND c.expirationDate >= :date";
	@Override
	public List<User> searchUser(String userIDName, GeneralDate date) {
		return this.queryProxy().query(SELECT_BY_ID_OR_NAME, SacmtUser.class)
				.setParameter("userIDName", userIDName)
				.setParameter("date", date).getList(c -> c.toDomain());
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

}
