package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.rank;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank.KscmtRank;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank.KscmtRankPk;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaRankRepository extends JpaRepository implements RankRepository {
	private static final String SELECT = "SELECT k FROM KscmtRank k ";
	private static final String GET_RANK = SELECT
			+ "WHERE k.kscmtRankPk.companyId = :companyId and k.kscmtRankPk.rankCd = :rankCd";
	private static final String GET_LIST_RANK_BY_COMPANY = SELECT
			+ "WHERE k.kscmtRankPk.companyId = :companyId order by k.kscmtRankPk.rankCd ASC";
	private static final String GET_LIST_RANK = SELECT
			+ "WHERE k.kscmtRankPk.companyId = :companyId and k.kscmtRankPk.rankCd IN :listRankCd";

	@Override
	public Rank getRank(String companyId, RankCode rankCd) {
		return this.queryProxy().query(GET_RANK, KscmtRank.class).setParameter("companyId", companyId)
				.setParameter("rankCd", rankCd.v()).getSingleOrNull(x -> toRankDomain(x));
	}

	@Override
	public List<Rank> getListRank(String companyId) {
		return this.queryProxy().query(GET_LIST_RANK_BY_COMPANY, KscmtRank.class).setParameter("companyId", companyId)
				.getList(x -> toRankDomain(x));
	}

	@Override
	public List<Rank> getListRank(String companyId, List<RankCode> listRankCd) {
		List<String> lstRankCd = listRankCd.stream().map(x -> x.v()).collect(Collectors.toList());

		return this.queryProxy().query(GET_LIST_RANK, KscmtRank.class).setParameter("companyId", companyId)
				.setParameter("listRankCd", lstRankCd).getList(x -> toRankDomain(x));
	}

	@Override
	public void insert(Rank rank, RankPriority rankPriority) {
		this.commandProxy().insert(toEntity(rank, rankPriority));
	}

	@Override
	public void updateRank(Rank rank) {
		String sqlQuery = "UPDATE KSCMT_RANK SET SYNAME = ? WHERE CID = ? AND CD = ?";

		try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(sqlQuery))) {
			ps.setString(1, rank.getRankSymbol().v());
			ps.setString(2, rank.getCompanyId());
			ps.setString(3, rank.getRankCode().v());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(String companyId, RankCode rankCd) {
		// (1) 指定されたランクコードのランクを削除する
		this.commandProxy().remove(KscmtRank.class, new KscmtRankPk(companyId, rankCd.v()));
		// (2) 残ったランクの優先順を補正する
		Optional<RankPriority> optRankPriority = this.getRankPriority(companyId);

		if (!optRankPriority.isPresent()) {
			return;
		}

		this.updateRankPriority(optRankPriority.get());
	}

	@Override
	public boolean exist(String companyId, RankCode rankCd) {

		if (this.getRank(companyId, rankCd) == null) {
			return false;
		}

		return true;
	}

	@Override
	public Optional<RankPriority> getRankPriority(String companyId) {
		List<KscmtRank> lstKscmtRank = this.queryProxy().query(GET_LIST_RANK_BY_COMPANY, KscmtRank.class)
				.setParameter("companyId", companyId).getList();

		if (lstKscmtRank.isEmpty()) {
			return Optional.empty();
		}

		// order by priority asc
		List<KscmtRank> lstKscmtRankSorted = lstKscmtRank.stream().sorted(Comparator.comparingInt(x -> x.priority))
				.collect(Collectors.toList());

		RankPriority rankPriority = new RankPriority(lstKscmtRankSorted.get(0).kscmtRankPk.companyId, lstKscmtRankSorted
				.stream().map(x -> new RankCode(String.valueOf(x.kscmtRankPk.rankCd))).collect(Collectors.toList()));

		return Optional.of(rankPriority);

	}

	@Override
	public void updateRankPriority(RankPriority rankPriority) {
		String companyId = rankPriority.getCompanyId();
		List<KscmtRank> kscmtRanks = this.queryProxy().query(GET_LIST_RANK_BY_COMPANY, KscmtRank.class)
				.setParameter("companyId", companyId).getList();
		List<String> rankCds = rankPriority.getListRankCd().stream().map(x -> x.v()).collect(Collectors.toList());

		kscmtRanks.stream().forEach(x -> {
			x.priority = rankCds.indexOf(x.kscmtRankPk.rankCd) + 1;
		});

		String sqlDelete = "DELETE FROM KSCMT_RANK WHERE CID = ?";
		String sqlInsert = "INSERT INTO KSCMT_RANK (CID, CD, SYNAME, PRIORITY) VALUES (?,?,?,?)";

		try (PreparedStatement ps1 = this.connection().prepareStatement(sqlDelete)) {
			ps1.setString(1, companyId);
			ps1.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try (PreparedStatement ps2 = this.connection().prepareStatement(JDBCUtil.toInsertWithCommonField(sqlInsert))) {
			for (int i = 0; i < kscmtRanks.size(); i++) {
				ps2.setString(1, companyId);
				ps2.setString(2, kscmtRanks.get(i).kscmtRankPk.rankCd);
				ps2.setString(3, kscmtRanks.get(i).rankSymbol);
				ps2.setInt(4, kscmtRanks.get(i).priority);
				ps2.addBatch();
			}
			ps2.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * convert entity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private static Rank toRankDomain(KscmtRank entity) {
		Rank domain = new Rank(entity.kscmtRankPk.companyId, new RankCode(entity.kscmtRankPk.rankCd),
				new RankSymbol(entity.rankSymbol));

		return domain;
	}

	private static KscmtRank toEntity(Rank rank, RankPriority rankPriority) {
		KscmtRankPk pk = new KscmtRankPk(rank.getCompanyId(), rank.getRankCode().v());
		int priority = rankPriority.getListRankCd().indexOf(rank.getRankCode()) + 1;
		KscmtRank entity = new KscmtRank(pk, rank.getRankSymbol().v(), priority);

		return entity;
	}

}
