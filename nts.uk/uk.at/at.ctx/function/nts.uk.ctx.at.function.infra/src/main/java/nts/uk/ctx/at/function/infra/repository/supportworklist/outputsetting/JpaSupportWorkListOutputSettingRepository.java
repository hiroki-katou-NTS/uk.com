package nts.uk.ctx.at.function.infra.repository.supportworklist.outputsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSettingRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputCode;
import nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting.KfnmtRptWkSup;
import nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting.KfnmtRptWkSupItem;
import nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting.KfnmtRptWkSupItemPk;
import nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting.KfnmtRptWkSupPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaSupportWorkListOutputSettingRepository extends JpaRepository implements SupportWorkListOutputSettingRepository {
    @Override
    public void insert(SupportWorkListOutputSetting domain) {
        String companyId = AppContexts.user().companyId();
        KfnmtRptWkSup entity = new KfnmtRptWkSup(
                new KfnmtRptWkSupPk(companyId, domain.getCode().v()),
                domain.getName().v(),
                domain.getDetailLayoutSetting().getExtractCondition().value,
                domain.getDetailLayoutSetting().getDetailDisplaySetting().getDisplayDetail().value,
                domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayOneDayTotal().value,
                domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value,
                domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplaySupportDetail().value,
                domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceTotal().value,
                domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value,
                domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayGrandTotal().value,
                domain.getDetailLayoutSetting().getPageBreak().value,
                domain.getDetailLayoutSetting().getDetailDisplaySetting().getOutputItems().stream().map(i -> {
                    KfnmtRptWkSupItem item = new KfnmtRptWkSupItem();
                    item.pk = new KfnmtRptWkSupItemPk(companyId, domain.getCode().v(), i.getAttendanceItemId());
                    item.displayOrder = i.getDisplayOrder();
                    return item;
                }).collect(Collectors.toList())
        );
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(SupportWorkListOutputSetting domain) {
        String companyId = AppContexts.user().companyId();
        this.queryProxy().find(new KfnmtRptWkSupPk(companyId, domain.getCode().v()), KfnmtRptWkSup.class).ifPresent(entity -> {
            entity.name = domain.getName().v();
            entity.employeeExtractCondition = domain.getDetailLayoutSetting().getExtractCondition().value;
            entity.detailAtr = domain.getDetailLayoutSetting().getDetailDisplaySetting().getDisplayDetail().value;
            entity.sumDayAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayOneDayTotal().value;
            entity.sumSupDetailAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplaySupportDetail().value;
            entity.sumSupWplAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value;
            entity.sumWplAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceTotal().value;
            entity.totalSumAtr = domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayGrandTotal().value;
            entity.totalSumSupWplAtr = domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value;
            entity.breakPageAtr = domain.getDetailLayoutSetting().getPageBreak().value;
            entity.items.clear();
            entity.items.addAll(domain.getDetailLayoutSetting().getDetailDisplaySetting().getOutputItems().stream().map(i -> {
                KfnmtRptWkSupItem item = new KfnmtRptWkSupItem();
                item.pk = new KfnmtRptWkSupItemPk(companyId, domain.getCode().v(), i.getAttendanceItemId());
                item.displayOrder = i.getDisplayOrder();
                return item;
            }).collect(Collectors.toList()));
            this.commandProxy().update(entity);
        });
    }

    @Override
    public void delete(String companyId, SupportWorkOutputCode code) {
        this.commandProxy().remove(KfnmtRptWkSup.class, new KfnmtRptWkSupPk(companyId, code.v()));
    }

    @Override
    public List<SupportWorkListOutputSetting> get(String companyId) {
        List<KfnmtRptWkSupItem> items = this.queryProxy()
                .query("select i from KfnmtRptWkSupItem i where i.pk.companyId = :companyId", KfnmtRptWkSupItem.class)
                .setParameter("companyId", companyId)
                .getList();
        return this.queryProxy()
                .query("select s from KfnmtRptWkSup s where s.pk.companyId = :companyId", KfnmtRptWkSup.class)
                .setParameter("companyId", companyId)
                .getList(e -> e.toDomain(items.stream().filter(i -> i.pk.code.equals(e.pk.code)).collect(Collectors.toList())));
    }

    @Override
    public Optional<SupportWorkListOutputSetting> get(String companyId, SupportWorkOutputCode code) {
        List<KfnmtRptWkSupItem> items = this.queryProxy()
                .query("select i from KfnmtRptWkSupItem i where i.pk.companyId = :companyId and i.pk.code = :code", KfnmtRptWkSupItem.class)
                .setParameter("companyId", companyId)
                .setParameter("code", code.v())
                .getList();
        return this.queryProxy().find(new KfnmtRptWkSupPk(companyId, code.v()), KfnmtRptWkSup.class).map(e -> e.toDomain(items));
    }
}
