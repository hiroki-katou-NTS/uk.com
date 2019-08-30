package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtSocIsacquisiInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtSocIsacquisiInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSocialInsurAcquisiInforRepository extends JpaRepository implements SocialInsurAcquisiInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSocIsacquisiInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.socIsacquisiInfoPk.companyId =:companyId AND f.socIsacquisiInfoPk.employeeId =:employeeId ";


    @Override
    public Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforById(String cid, String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSocIsacquisiInfo.class)
                .setParameter("companyId",cid)
                .setParameter("employeeId", employeeId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SocialInsurAcquisiInfor domain){
        this.commandProxy().insert(QqsmtSocIsacquisiInfo.toEntity(domain));
    }

    @Override
    public void update(SocialInsurAcquisiInfor domain){
        this.commandProxy().update(QqsmtSocIsacquisiInfo.toEntity(domain));
    }

    @Override
    public void remove(String cid, String employeeId){
        this.commandProxy().remove(QqsmtSocIsacquisiInfo.class, new QqsmtSocIsacquisiInfoPk(cid,employeeId));
    }
}
