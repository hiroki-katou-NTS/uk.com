package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtSocInsuNotiSet;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtSocInsuNotiSetPk;

import java.util.List;
import java.util.Optional;

public class JpaSocialInsurNotiCrSetRepository extends JpaRepository implements SocialInsurNotiCrSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSocInsuNotiSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.socInsuNotiSetPk.userId =:userId AND  f.socInsuNotiSetPk.cid =:cid ";

    @Override
    public List<SocialInsurNotiCreateSet> getAllSocialInsurNotiCreateSet() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtSocInsuNotiSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SocialInsurNotiCreateSet> getSocialInsurNotiCreateSetById(String userId, String cid) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSocInsuNotiSet.class)
                .setParameter("userId", userId)
                .setParameter("cid", cid)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SocialInsurNotiCreateSet domain) {
        this.commandProxy().insert(QqsmtSocInsuNotiSet.toEntity(domain));
    }

    @Override
    public void update(SocialInsurNotiCreateSet domain) {
        this.commandProxy().insert(QqsmtSocInsuNotiSet.toEntity(domain));
    }

    @Override
    public void remove(String userId, String cid) {
        this.commandProxy().remove(QqsmtSocInsuNotiSet.class, new QqsmtSocInsuNotiSetPk(userId, cid));
    }
}
