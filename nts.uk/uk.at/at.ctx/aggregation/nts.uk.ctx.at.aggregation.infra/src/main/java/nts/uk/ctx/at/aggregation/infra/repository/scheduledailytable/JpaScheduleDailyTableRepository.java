package nts.uk.ctx.at.aggregation.infra.repository.scheduledailytable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableCode;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTablePrintSetting;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableRepository;
import nts.uk.ctx.at.aggregation.infra.entity.scheduledailytable.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaScheduleDailyTableRepository extends JpaRepository implements ScheduleDailyTableRepository {
    @Override
    public Optional<ScheduleDailyTablePrintSetting> get(String companyId, ScheduleDailyTableCode code) {
        return this.queryProxy().find(new KagmtRptScherecPk(companyId, code.v()), KagmtRptScherec.class).map(KagmtRptScherec::toDomain);
    }

    @Override
    public List<ScheduleDailyTablePrintSetting> getList(String companyId) {
        return this.queryProxy().query("SELECT e FROM KagmtRptScherec e WHERE e.pk.companyId = :companyId", KagmtRptScherec.class)
                .setParameter("companyId", companyId)
                .getList(KagmtRptScherec::toDomain);
    }

    @Override
    public void insert(ScheduleDailyTablePrintSetting domain) {
        KagmtRptScherec entity = new KagmtRptScherec(domain);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(ScheduleDailyTablePrintSetting domain) {
        this.queryProxy().find(new KagmtRptScherecPk(AppContexts.user().companyId(), domain.getCode().v()), KagmtRptScherec.class)
                .ifPresent(entity -> {
                    entity.name = domain.getName().v();
                    entity.note = domain.getItemSetting().getComment().map(PrimitiveValueBase::v).orElse(null);
                    entity.signStampUseAtr = domain.getItemSetting().getInkanRow().getNotUseAtr().value;
                    if (domain.getItemSetting().getInkanRow().getNotUseAtr() == NotUseAtr.USE) {
                        entity.kagmtRptScherecSignStamps.clear();
                        for (int i = 0; i < domain.getItemSetting().getInkanRow().getTitleList().size(); i++) {
                            KagmtRptScherecSignStamp tmp = new KagmtRptScherecSignStamp();
                            tmp.pk = new KagmtRptScherecSignStampPk(entity.pk.companyId, entity.pk.code, i + 1);
                            tmp.title = domain.getItemSetting().getInkanRow().getTitleList().get(i).v();
                            entity.kagmtRptScherecSignStamps.add(tmp);
                        }
                    }
                    entity.personCounters.clear();
                    entity.workplaceCounters.clear();
                    entity.wkpMoveDispAtr = domain.getItemSetting().getTransferDisplay().value;
                    entity.supporterOutputAtrSche = domain.getItemSetting().getSupporterSchedulePrintMethod().value;
                    entity.supporterOutputAtrRec = domain.getItemSetting().getSupporterDailyDataPrintMethod().value;
                    this.commandProxy().update(entity);
                    this.getEntityManager().flush();

                    for (int i = 0; i < domain.getItemSetting().getPersonalCounter().size(); i++) {
                        KagmtRptScherecTallyByperson tmp = new KagmtRptScherecTallyByperson();
                        tmp.pk = new KagmtRptScherecSignStampPk(entity.pk.companyId, entity.pk.code, i);
                        tmp.totalTimesNo = domain.getItemSetting().getPersonalCounter().get(i);
                        entity.personCounters.add(tmp);
                    }
                    for (int i = 0; i < domain.getItemSetting().getWorkplaceCounter().size(); i++) {
                        KagmtRptScherecTallyBywkp tmp = new KagmtRptScherecTallyBywkp();
                        tmp.pk = new KagmtRptScherecSignStampPk(entity.pk.companyId, entity.pk.code, i);
                        tmp.totalTimesNo = domain.getItemSetting().getWorkplaceCounter().get(i);
                        entity.workplaceCounters.add(tmp);
                    }
                    this.commandProxy().update(entity);
                });
    }

    @Override
    public void delete(String companyId, ScheduleDailyTableCode code) {
        this.commandProxy().remove(KagmtRptScherec.class, new KagmtRptScherecPk(companyId, code.v()));
    }
}
