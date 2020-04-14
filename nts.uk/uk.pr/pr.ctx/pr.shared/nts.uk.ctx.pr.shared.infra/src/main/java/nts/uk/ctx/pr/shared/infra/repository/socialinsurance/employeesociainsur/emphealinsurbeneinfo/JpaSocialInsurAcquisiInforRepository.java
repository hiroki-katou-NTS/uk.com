package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.sql.PreparedStatement;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtSocIsacquisiInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtSocIsacquisiInfoPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaSocialInsurAcquisiInforRepository extends JpaRepository implements SocialInsurAcquisiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSocIsacquisiInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.socIsacquisiInfoPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_STRINGS = "SELECT f FROM QqsmtSocIsacquisiInfo f WHERE  f.socIsacquisiInfoPk.employeeId =:employeeId AND f.socIsacquisiInfoPk.companyId =:companyId";
  //  private static final String UPDATE_SOCIAL_BY_KEY = "UPDATE QqsmtSocIsacquisiInfo f SET f.continReemAfterRetirement =:continReemAfterRetirement WHERE f.socIsacquisiInfoPk.employeeId =:employeeId";

    private static final String GET_PERSON_INFO = "SELECT BIRTHDAY " +
            " FROM (SELECT * " +
            " FROM BSYMT_EMP_DTA_MNG_INFO " +
            " WHERE SID = ? ) i " +
            " INNER JOIN BPSMT_PERSON p ON p.PID = i.PID";

    @Override
    public void add(SocialInsurAcquisiInfor domain){
        this.commandProxy().insert(QqsmtSocIsacquisiInfo.toEntity(domain));
    }

    @Override
    public void update(SocialInsurAcquisiInfor domain){
        this.commandProxy().update(QqsmtSocIsacquisiInfo.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String companyId){
        this.commandProxy().remove(QqsmtSocIsacquisiInfo.class, new QqsmtSocIsacquisiInfoPk(employeeId, companyId));
    }

    @Override
    public Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforByCIdEmpId( String companyId, String employeeId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRINGS, QqsmtSocIsacquisiInfo.class)
                .setParameter("employeeId", employeeId)
                .setParameter("companyId", companyId)
                .getSingle(c->c.toDomains());
    }

    @Override
    public void updated(SocialInsurAcquisiInfor domain) {
        return;
    }

    @Override
    public void updated(String employeeId, int continReemAfterRetirement) {
        String companyId = AppContexts.user().companyId();
        QqsmtSocIsacquisiInfo qqsmtSocIsacquisiInfo = this.getEntityManager().find(QqsmtSocIsacquisiInfo.class, new QqsmtSocIsacquisiInfoPk(companyId,employeeId));
        //check data
        if(qqsmtSocIsacquisiInfo != null){
            qqsmtSocIsacquisiInfo.continReemAfterRetirement = continReemAfterRetirement;
            this.commandProxy().update(qqsmtSocIsacquisiInfo);
        } else {
            qqsmtSocIsacquisiInfo = new QqsmtSocIsacquisiInfo(
                    new QqsmtSocIsacquisiInfoPk(companyId,employeeId),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    continReemAfterRetirement );
            this.commandProxy().insert(qqsmtSocIsacquisiInfo);
        }
    }

    @Override
    @SneakyThrows
    public Optional<GeneralDate> getPersonInfo(String employeeId) {

        try(PreparedStatement stmt = this.connection().prepareStatement(GET_PERSON_INFO)){
            stmt.setString(1,employeeId);

            return new NtsResultSet(stmt.executeQuery()).getSingle(x -> {
                  return x.getGeneralDate(1);

            });

        }

    }

}
