package nts.uk.ctx.at.schedule.infra.repository.shift.rank;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.rank.Rank;
import nts.uk.ctx.at.schedule.dom.shift.rank.RankRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.rank.KscmtRank;
/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class JpaRankRepository extends JpaRepository implements RankRepository {
	private static final String SELECT_NO_WHERE = "SELECT k FROM KscmtRank k ";
	private static final String SELECT_BY_COMPANYID = SELECT_NO_WHERE + "WHERE k.kscmtRankPk.companyId = :companyId ORDER BY k.displayOrder ASC";

	@Override
	public List<Rank> getListRank(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANYID, KscmtRank.class).setParameter("companyId", companyId)
				.getList(x -> toDomain(x));
	}

	/**
	 * convert entity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private static Rank toDomain(KscmtRank entity) {
		Rank domain = Rank.convertFromJavaType(entity.kscmtRankPk.companyId, entity.kscmtRankPk.rankCode,
				entity.rankMemo, entity.displayOrder);
		return domain;
	}

}
