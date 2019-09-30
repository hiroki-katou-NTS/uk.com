package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtRomanmRptSetting;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtRomanmRptSettingPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaRomajiNameNotiCreSettingRepository extends JpaRepository implements RomajiNameNotiCreSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtRomanmRptSetting f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.romanmRptSettingPk.cid =:cid AND  f.romanmRptSettingPk.userId =:userId ";

    private static final String ROMAJI_INFO = "SELECT f FROM QqsmtSocInsuNotiSet f " +
            "WHERE f.socInsuNotiSetPk.userId =:userId AND f.socInsuNotiSetPk.cid =:cid";

    @Override
    public Optional<RomajiNameNotiCreSetting> getRomajiNameNotiCreSettingById() {
        return this.queryProxy().query(ROMAJI_INFO, QrsmtRomanmRptSetting.class)
                .setParameter("userId", AppContexts.user().userId())
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomain());
    }

    @Override
    public void register(RomajiNameNotiCreSetting domain) {
        QrsmtRomanmRptSetting qqsmtSocInsuNotiSet =  this.getEntityManager().find(QrsmtRomanmRptSetting.class,new QrsmtRomanmRptSettingPk(AppContexts.user().userId(), AppContexts.user().companyId()));
        if(qqsmtSocInsuNotiSet != null){
            this.commandProxy().update(QrsmtRomanmRptSetting.toEntity(domain));
        } else {
            this.commandProxy().insert(QrsmtRomanmRptSetting.toEntity(domain));
        }
    }
}
