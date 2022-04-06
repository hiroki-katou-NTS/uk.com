package nts.uk.ctx.at.function.infra.repository.anyperiodcorrection.formatsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.DisplayTimeItem;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaAnyPeriodCorrectionFormatSettingRepository extends JpaRepository implements AnyPeriodCorrectionFormatSettingRepository {
    @Override
    public void insert(AnyPeriodCorrectionFormatSetting setting) {
        String companyId = AppContexts.user().companyId();
        KfnmtAnpForm format = new KfnmtAnpForm(
                new KfnmtAnpFormPk(companyId, setting.getCode().v()),
                setting.getName().v(),
                setting.getSheetSetting().getListSheetCorrectedMonthly().stream().map(s -> new KfnmtAnpFormSheet(
                        new KfnmtAnpFormSheetPk(companyId, setting.getCode().v(), s.getSheetNo()),
                        s.getSheetName().v(),
                        s.getListDisplayTimeItem().stream().map(i -> new KfnmtAnpFormItem(
                                new KfnmtAnpFormItemPk(companyId, setting.getCode().v(), s.getSheetNo(), i.getItemDaily()),
                                i.getDisplayOrder(),
                                i.getColumnWidthTable().orElse(null)
                        )).collect(Collectors.toList())
                )).collect(Collectors.toList())
        );
        format.pk = new KfnmtAnpFormPk(AppContexts.user().companyId(), setting.getCode().v());
        format.name = setting.getName().v();
        this.commandProxy().insert(format);
    }

    @Override
    public void update(AnyPeriodCorrectionFormatSetting setting) {
        String companyId = AppContexts.user().companyId();
        this.queryProxy().find(new KfnmtAnpFormPk(companyId, setting.getCode().v()), KfnmtAnpForm.class).ifPresent(entity -> {
            entity.name = setting.getName().v();
            entity.sheets.clear();
            entity.sheets.addAll(setting.getSheetSetting().getListSheetCorrectedMonthly().stream().map(s -> new KfnmtAnpFormSheet(
                    new KfnmtAnpFormSheetPk(companyId, setting.getCode().v(), s.getSheetNo()),
                    s.getSheetName().v(),
                    s.getListDisplayTimeItem().stream().map(i -> new KfnmtAnpFormItem(
                            new KfnmtAnpFormItemPk(companyId, setting.getCode().v(), s.getSheetNo(), i.getItemDaily()),
                            i.getDisplayOrder(),
                            i.getColumnWidthTable().orElse(null)
                    )).collect(Collectors.toList())
            )).collect(Collectors.toList()));
            this.commandProxy().update(entity);
        });
    }

    @Override
    public void delete(String companyId, String code) {
        this.commandProxy().remove(KfnmtAnpForm.class, new KfnmtAnpFormPk(companyId, code));
    }

    private static final String GET_LIST_FORMAT_QUERY = "select a from KfnmtAnpForm a where a.pk.companyId = :companyId";
    private static final String GET_LIST_SHEET_QUERY = "select a from KfnmtAnpFormSheet a where a.pk.companyId = :companyId";
    private static final String GET_LIST_ITEM_QUERY = "select a from KfnmtAnpFormItem a where a.pk.companyId = :companyId";
    @Override
    public List<AnyPeriodCorrectionFormatSetting> getAll(String companyId) {
        List<KfnmtAnpForm> formats = this.queryProxy().query(GET_LIST_FORMAT_QUERY, KfnmtAnpForm.class)
                .setParameter("companyId", companyId).getList();
        List<KfnmtAnpFormSheet> sheets = this.queryProxy().query(GET_LIST_SHEET_QUERY, KfnmtAnpFormSheet.class)
                .setParameter("companyId", companyId).getList();
        List<KfnmtAnpFormItem> items = this.queryProxy().query(GET_LIST_ITEM_QUERY, KfnmtAnpFormItem.class)
                .setParameter("companyId", companyId).getList();
        return formats.stream().map(f -> {
            return new AnyPeriodCorrectionFormatSetting(
                    f.pk.code,
                    f.name,
                    sheets.stream().filter(s -> s.pk.code.equals(f.pk.code))
                            .map(s -> new SheetCorrectedMonthly(
                                    s.pk.sheetNo,
                                    new DailyPerformanceFormatName(s.sheetName),
                                    items.stream().filter(i -> i.pk.code.equals(s.pk.code) && i.pk.sheetNo == s.pk.sheetNo)
                                            .map(i -> new DisplayTimeItem(
                                                    i.displayOrder,
                                                    i.pk.attendanceItemId,
                                                    i.columnWidth
                                            )).collect(Collectors.toList())
                            )).collect(Collectors.toList())
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<AnyPeriodCorrectionFormatSetting> get(String companyId, String code) {
        return this.queryProxy().find(new KfnmtAnpFormPk(companyId, code), KfnmtAnpForm.class).map(e -> {
            return new AnyPeriodCorrectionFormatSetting(
                    e.pk.code,
                    e.name,
                    e.sheets.stream().map(s -> new SheetCorrectedMonthly(
                            s.pk.sheetNo,
                            new DailyPerformanceFormatName(s.sheetName),
                            s.items.stream().map(i -> new DisplayTimeItem(
                                    i.displayOrder,
                                    i.pk.attendanceItemId,
                                    i.columnWidth
                            )).collect(Collectors.toList())
                    )).collect(Collectors.toList())
            );
        });
    }
}
