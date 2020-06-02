package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.rank;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank.KscmtSyaRank;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

/**
 * 社員ランクRepository
 * @author phongtq
 *
 */
@Stateless
public class JpaEmployeeRankRepository extends JpaRepository implements EmployeeRankRepository {

	private static final String SELECT = "SELECT k FROM KscmtSyaRank k ";
	private static final String GET_RANK = SELECT
			+ "WHERE k.pk.SID = :SID";
	
	private static final String GET_RANKS = SELECT
			+ "WHERE k.pk.SID IN :lstSID";
	
	/**
	 * insert(社員ランク）
	 */
	@Override
	public void insert(EmployeeRank employeeRank) {
		this.commandProxy().insert(KscmtSyaRank.toEntity(employeeRank));
	}

	/**
	 * update(社員ランク)
	 */
	@Override
	public void update(EmployeeRank employeeRank) {
		String sqlQuery = "UPDATE KSCMT_SYA_RANK SET RANK_CD = ? WHERE SID = ?";

		try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(sqlQuery))) {
			ps.setString(1, employeeRank.getEmplRankCode().v());
			ps.setString(2, employeeRank.getSID());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}

	/**
	 * delete(社員ID)
	 */
	@Override
	public void delete(String sID) {
		Optional<EmployeeRank> entity = this.getById(sID);
		if(entity.isPresent())
			this.commandProxy().remove(entity);
	}

	/**
	 * get(社員ID)
	 */
	@Override
	public Optional<EmployeeRank> getById(String sID) {
		return this.queryProxy().query(GET_RANK, KscmtSyaRank.class)
				.setParameter("SID", sID)
				.getSingle(c->c.toDomain());
	}

	/**
	 * *get(List<社員ID>)
	 */
	@Override
	public List<EmployeeRank> getAll(List<String> lstSID) {
		List<EmployeeRank> list = new ArrayList<>();
		CollectionUtil.split(lstSID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			list.addAll(this.queryProxy().query(GET_RANKS, KscmtSyaRank.class)
				.setParameter("lstSID", subList)
				.getList(c -> c.toDomain()));
		});
		return list;
	}

	/**
	 * exists(社員ID）
	 */
	@Override
	public boolean exists(String sID) {
		return this.getById(sID).isPresent();
	}

}
