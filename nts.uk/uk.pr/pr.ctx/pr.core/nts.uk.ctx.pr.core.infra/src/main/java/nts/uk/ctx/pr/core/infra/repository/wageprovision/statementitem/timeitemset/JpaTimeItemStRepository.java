package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.timeitemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.timeitemset.QpbmtTimeItemSt;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.timeitemset.QpbmtTimeItemStPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTimeItemStRepository extends JpaRepository implements TimeItemSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtTimeItemSt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.timeItemStPk.cid =:cid" +
            " AND  f.timeItemStPk.categoryAtr =:categoryAtr" +
            " AND  f.timeItemStPk.itemNameCd =:itemNameCd ";
	private static final String UPDATE_IN_LIST = "UPDATE QpbmtTimeItemSt f SET f.averageWageAtr = 1 "
			+ " WHERE f.timeCountAtr = 1 AND f.timeItemStPk.cid =:cid AND f.timeItemStPk.itemNameCd IN :lstCode";
    private static final String UPDATE_NOT_IN_LIST = " UPDATE QpbmtTimeItemSt f SET f.averageWageAtr = 0 "
			+ " WHERE f.timeCountAtr = 1 AND f.timeItemStPk.cid =:cid AND f.timeItemStPk.itemNameCd NOT IN :lstCode ";
    private static final String UPDATE_LIST_TIME = " UPDATE QpbmtTimeItemSt f SET f.averageWageAtr = 0 "
            + " WHERE f.timeItemStPk.cid =:cid AND f.timeCountAtr = 1";

    @Override
    public List<TimeItemSet> getAllTimeItemSt() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtTimeItemSt.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<TimeItemSet> getTimeItemStById(String cid, int categoryAtr, String itemNameCd) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtTimeItemSt.class)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(TimeItemSet domain) {
        this.commandProxy().insert(QpbmtTimeItemSt.toEntity(domain));
    }

    @Override
    public void update(TimeItemSet domain) {
        this.commandProxy().update(QpbmtTimeItemSt.toEntity(domain));
    }

	@Override
	public void updateAll(List<String> lstCode) {
        String cid = AppContexts.user().companyId();

		if(lstCode.size() > 0){
			this.getEntityManager().createQuery(UPDATE_IN_LIST).setParameter("cid", cid).setParameter("lstCode", lstCode).executeUpdate();
            this.getEntityManager().createQuery(UPDATE_NOT_IN_LIST).setParameter("cid", cid).setParameter("lstCode", lstCode).executeUpdate();
		}
		else{
            this.getEntityManager().createQuery(UPDATE_LIST_TIME).setParameter("cid", cid).executeUpdate();
        }
	}
    @Override
    public void remove(String cid, int categoryAtr, String itemNameCd) {
        this.commandProxy().remove(QpbmtTimeItemSt.class, new QpbmtTimeItemStPk(cid, categoryAtr, itemNameCd));
    }
}
