package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.optionalitemappsetting.KrqmtAppAnyv;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.optionalitemappsetting.KrqmtAppAnyvPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaOptionalItemAppSetRepository extends JpaRepository implements OptionalItemAppSetRepository {
    @Override
    public Optional<OptionalItemApplicationSetting> findByCompanyAndCode(String companyId, String code) {
        return this.queryProxy().find(new KrqmtAppAnyvPk(companyId, code), KrqmtAppAnyv.class).map(KrqmtAppAnyv::toDomain);
    }

    @Override
    public List<OptionalItemApplicationSetting> findByCompany(String companyId) {
        return this.queryProxy().query("Select a from KrqmtAppAnyv a where a.pk.companyId = :companyId", KrqmtAppAnyv.class)
                .setParameter("companyId", companyId)
                .getList(KrqmtAppAnyv::toDomain);
    }

    @Override
    public void save(OptionalItemApplicationSetting domain) {
        Optional<KrqmtAppAnyv> optEntity = this.queryProxy().find(new KrqmtAppAnyvPk(domain.getCompanyId(), domain.getCode().v()), KrqmtAppAnyv.class);
        if (optEntity.isPresent()) {
            KrqmtAppAnyv entity = optEntity.get();
            entity.update(domain);
            this.commandProxy().update(entity);
        } else {
            this.commandProxy().insert(KrqmtAppAnyv.fromDomain(domain));
        }
    }

    @Override
    public void delete(String companyId, String code) {
        this.commandProxy().remove(KrqmtAppAnyv.class, new KrqmtAppAnyvPk(companyId, code));
    }
}
