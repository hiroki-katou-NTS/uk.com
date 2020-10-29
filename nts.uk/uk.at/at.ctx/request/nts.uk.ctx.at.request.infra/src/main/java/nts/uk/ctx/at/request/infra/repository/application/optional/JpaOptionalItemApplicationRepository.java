package nts.uk.ctx.at.request.infra.repository.application.optional;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.infra.entity.application.optional.KrqdtAppAnyv;
import nts.uk.ctx.at.request.infra.entity.application.optional.KrqdtAppAnyvPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaOptionalItemApplicationRepository extends JpaRepository implements OptionalItemApplicationRepository {

    @Override
    public void save(OptionalItemApplication optItemApp) {
        List<KrqdtAppAnyv> entities = toEntity(optItemApp);
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(OptionalItemApplication optItemApp) {

    }

    private List<KrqdtAppAnyv> toEntity(OptionalItemApplication domain) {
        String cid = AppContexts.user().companyId();
        List<KrqdtAppAnyv> entities = new ArrayList<>();
        KrqdtAppAnyv entity = new KrqdtAppAnyv();
        domain.getOptionalItems().forEach(anyItemValue -> {
            entity.setKrqdtAppAnyvPk(new KrqdtAppAnyvPk(cid, domain.getAppID(), domain.getCode().v(), anyItemValue.getItemNo().v() + 640));
            entity.setTimes(anyItemValue.getRowTimes());
            entity.setTime(anyItemValue.getRowTime());
            entity.setMoneyValue(anyItemValue.getRowAmount());
            entities.add(entity);
        });
        return entities;
    }

}
