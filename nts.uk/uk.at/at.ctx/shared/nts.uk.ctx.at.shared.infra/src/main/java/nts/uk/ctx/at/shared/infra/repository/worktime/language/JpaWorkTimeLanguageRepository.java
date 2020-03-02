package nts.uk.ctx.at.shared.infra.repository.worktime.language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.language.WorkTimeLanguage;
import nts.uk.ctx.at.shared.dom.worktime.language.WorkTimeLanguageRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkTimeLanguageRepository extends JpaRepository implements WorkTimeLanguageRepository {

	@Override
	public List<WorkTimeLanguage> findByCIdAndLangId(String companyId, String langId) {
		String sql = "SELECT a.CID, a.WORKTIME_CD, a.LANG_ID, a.NAME, a.ABNAME FROM KSHMT_WT_LANGUAGE a WHERE a.CID = ? AND a.LANG_ID = ?";

		List<WorkTimeLanguage> results = new ArrayList<>();

		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setString(2, langId);

			results.addAll(new NtsResultSet(stmt.executeQuery()).getList(rs -> {
				return new WorkTimeLanguage(rs.getString("CID"), new WorkTimeCode(rs.getString("WORKTIME_CD")),
						rs.getString("LANG_ID"), new WorkTimeName(rs.getString("NAME")),
						new WorkTimeAbName(rs.getString("ABNAME")));
			}));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return results;
	}

	@Override
	public Optional<WorkTimeLanguage> findById(String companyId, String langId, String workTimeCode) {
		String sql = "SELECT a.CID, a.WORKTIME_CD, a.LANG_ID, a.NAME, a.ABNAME FROM KSHMT_WT_LANGUAGE a WHERE a.CID = ? AND a.LANG_ID = ? AND a.WORKTIME_CD = ?";

		Optional<WorkTimeLanguage> result = Optional.empty();

		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setString(2, langId);
			stmt.setString(3, workTimeCode);

			result = new NtsResultSet(stmt.executeQuery()).getSingle(rs -> {
				return new WorkTimeLanguage(rs.getString("CID"), new WorkTimeCode(rs.getString("WORKTIME_CD")),
						rs.getString("LANG_ID"), new WorkTimeName(rs.getString("NAME")),
						new WorkTimeAbName(rs.getString("ABNAME")));
			});
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}

	@Override
	public void insert(WorkTimeLanguage workTimeLanguage) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sql = "INSERT INTO KSHMT_WT_LANGUAGE (CID, WORKTIME_CD, LANG_ID, NAME, ABNAME) VALUES ('"
				+ workTimeLanguage.getCompanyId() + "','" + workTimeLanguage.getWorkTimeCode().v() + "','"
				+ workTimeLanguage.getLangId() + "','" + workTimeLanguage.getName().v() + "','"
				+ workTimeLanguage.getAbbreviationName().v() + "')";

		try {
			con.createStatement().executeUpdate(JDBCUtil.toInsertWithCommonField(sql));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(WorkTimeLanguage workTimeLanguage) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sql = "UPDATE KSHMT_WT_LANGUAGE SET NAME = '" + workTimeLanguage.getName().v() + "', ABNAME = '"
				+ workTimeLanguage.getAbbreviationName().v() + "' WHERE CID = '" + workTimeLanguage.getCompanyId()
				+ "' AND WORKTIME_CD = '" + workTimeLanguage.getWorkTimeCode().v() + "' AND LANG_ID = '"
				+ workTimeLanguage.getLangId() + "'";

		try {
			con.createStatement().executeUpdate(JDBCUtil.toUpdateWithCommonField(sql));
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}

	@Override
	public void delete(String companyId, String langId, String workTimeCode) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sql = "DELETE FROM KSHMT_WT_LANGUAGE WHERE CID = '" + companyId + "' AND WORKTIME_CD = '" + workTimeCode
				+ "' AND LANG_ID = '" + langId + "'";

		try {
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
		
	}

}
