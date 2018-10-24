package nts.uk.ctx.at.schedule.infra.repository.shift.rank.ranksetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSet;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSetRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.rank.ranksetting.KscstRankSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.rank.ranksetting.KscstRankSetPk;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class JpaRankSetRepository extends JpaRepository implements RankSetRepository {
	private static final String SELECT_NO_WHERE = "SELECT k FROM KscstRankSet k ";
	private static final String SELECT_BY_LIST_EMPLOYEEID = SELECT_NO_WHERE + "WHERE  k.kscstRankSetPk.sId IN :sIds";
	private static final String DELETE_BY_SID = "DELETE from KscstRankSet k WHERE k.kscstRankSetPk.sId = :sId ";

	@Override
	public List<RankSet> getListRankSet(List<String> employeeIds) {
		List<RankSet> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LIST_EMPLOYEEID, KscstRankSet.class)
								  .setParameter("sIds", subList)
								  .getList(x -> toDomain(x)));
		});
		return resultList;
	}

	@Override
	public void removeRankSet(String sId) {
		this.getEntityManager().createQuery(DELETE_BY_SID).setParameter("sId", sId).executeUpdate();
	}

	@Override
	public void updateRankSet(RankSet rankSet) {
		KscstRankSetPk kscstRankSetPk = new KscstRankSetPk(rankSet.getRankCode().v(), rankSet.getSId());
		KscstRankSet entity = new KscstRankSet(kscstRankSetPk);
        //this.getEntityManager().merge(entity);
		this.commandProxy().update(entity);  
	}

	@Override
	public void addRankSet(RankSet rankSet) {
		this.commandProxy().insert(toEntity(rankSet));
	}

	private RankSet toDomain(KscstRankSet entity) {
		RankSet domain = RankSet.createFromJavaType(entity.kscstRankSetPk.rankCode, entity.kscstRankSetPk.sId);
		return domain;
	}

	private KscstRankSet toEntity(RankSet domain) {
		val entity = new KscstRankSet();
		KscstRankSetPk kscstRankSetPk = new KscstRankSetPk(domain.getRankCode().v(), domain.getSId());
		entity.kscstRankSetPk = kscstRankSetPk;
		return entity;
	}

}
