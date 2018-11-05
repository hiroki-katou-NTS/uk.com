package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.FixedItemClassificationList;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.FixedItemClassificationListRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.SpecificationOutputCls;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtFixedItemClsList;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaFixedItemClassificationListRepository extends JpaRepository implements FixedItemClassificationListRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFixedItemClsList f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
            + " WHERE  f.fixedItemClsListPk.itemNameCd =:itemNameCd";
    private static final String SELECT_BY_SPEC_OUTPUT_CLS = SELECT_ALL_QUERY_STRING
            + " WHERE  f.specOutPutCls =:specOutPutCls";

    @Override
    public Optional<FixedItemClassificationList> getFixedItemClassificationListById(String itemNameCd) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtFixedItemClsList.class)
                .setParameter("itemNameCd", itemNameCd)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<FixedItemClassificationList> getFixedItemClassificationListBySpecOutPutCls(int specOutPutCls) {
        return this.queryProxy().query(SELECT_BY_SPEC_OUTPUT_CLS, QpbmtFixedItemClsList.class)
                .setParameter("specOutPutCls", specOutPutCls)
                .getList(item -> item.toDomain());
    }

    @Override
    public void add(FixedItemClassificationList domain) {

    }

    @Override
    public void update(FixedItemClassificationList domain) {

    }

    @Override
    public void remove() {

    }
}
