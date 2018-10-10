package nts.uk.ctx.pr.core.infra.repository.emprsdttaxinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfoRepository;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtPayeeInfo;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtPayeeInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPayeeInfoRepository extends JpaRepository implements PayeeInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayeeInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
            + " WHERE  f.payeeInfoPk.histId =:histId ";

    @Override
    public List<PayeeInfo> getAllPayeeInfo() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPayeeInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PayeeInfo> getPayeeInfoById(String histId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayeeInfo.class)
                .setParameter("histId", histId)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(PayeeInfo domain) {
        this.commandProxy().insert(QpbmtPayeeInfo.toEntity(domain));
    }

    @Override
    public void update(PayeeInfo domain) {
        this.commandProxy().update(QpbmtPayeeInfo.toEntity(domain));
    }

    @Override
    public void remove(String histId) {
        this.commandProxy().remove(QpbmtPayeeInfo.class, new QpbmtPayeeInfoPk(histId));
    }
}
