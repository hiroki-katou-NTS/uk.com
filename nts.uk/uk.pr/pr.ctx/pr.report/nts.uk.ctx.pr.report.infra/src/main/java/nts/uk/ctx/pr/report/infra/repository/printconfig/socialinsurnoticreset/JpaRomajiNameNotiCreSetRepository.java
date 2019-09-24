package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QqsmtSocInsuNotiSet;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QqsmtSocInsuNotiSetPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaRomajiNameNotiCreSetRepository extends JpaRepository implements RomajiNameNotiCreSetRepository {
    private static final String ROMAJI_INFO = "SELECT f FROM QqsmtSocInsuNotiSet f " +
            "WHERE f.socInsuNotiSetPk.userId =:userId AND f.socInsuNotiSetPk.cid =:cid";
    private static final String SOCIAL_INFO = "SELECT f FROM QpbmtSocialInsuranceOffice f WHERE f.socialInsuranceOfficePk.cid =:cid";

    @Override
    public Optional<RomajiNameNotiCreSetting> getRomajiNameNotiCreSettingById() {
        return this.queryProxy().query(ROMAJI_INFO, QqsmtSocInsuNotiSet.class)
                .setParameter("userId", AppContexts.user().userId())
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomainRomaji());
    }

    @Override
    public void register(RomajiNameNotiCreSetting domain) {
        QqsmtSocInsuNotiSet qqsmtSocInsuNotiSet =  this.getEntityManager().find(QqsmtSocInsuNotiSet.class,new QqsmtSocInsuNotiSetPk(AppContexts.user().userId(), AppContexts.user().companyId()));
        if(qqsmtSocInsuNotiSet != null){
            this.commandProxy().update(QqsmtSocInsuNotiSet.toEntitys(domain));
        } else {
            this.commandProxy().insert(QqsmtSocInsuNotiSet.toEntitys(domain));
        }
    }
}
