package nts.uk.ctx.at.function.infra.repository.anyperiodcorrection.formatsetting.columnwidthsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidth;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidthRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.DisplayItemColumnWidth;
import nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.columnwidthsetting.KfndtAnpGridColWidth;
import nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.columnwidthsetting.KfndtAnpGridColWidthPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaAnyPeriodCorrectionGridColumnWidthRepository extends JpaRepository implements AnyPeriodCorrectionGridColumnWidthRepository {
    private static final String GET_LIST_SETTING_QUERY = "select a from KfndtAnpGridColWidth a where a.pk.employeeId = :employeeId";

    @Override
    public void insert(AnyPeriodCorrectionGridColumnWidth setting) {
        this.commandProxy().insertAll(setting.getColumnWidths().stream().map(i -> new KfndtAnpGridColWidth(
                new KfndtAnpGridColWidthPk(setting.getEmployeeId(), i.getAttendanceItemId()),
                i.getColumnWidth()
        )).collect(Collectors.toList()));
    }

    @Override
    public void update(AnyPeriodCorrectionGridColumnWidth setting) {
        List<KfndtAnpGridColWidth> entities = this.queryProxy().query(GET_LIST_SETTING_QUERY, KfndtAnpGridColWidth.class)
                .setParameter("employeeId", setting.getEmployeeId()).getList();
        List<Integer> tmp = setting.getColumnWidths().stream().map(DisplayItemColumnWidth::getAttendanceItemId).collect(Collectors.toList());
        List<KfndtAnpGridColWidth> toBeRemoved = entities.stream().filter(e -> !tmp.contains(e.pk.attendanceItemId)).collect(Collectors.toList());
        if (!toBeRemoved.isEmpty()) {
            this.commandProxy().removeAll(toBeRemoved);
        }
        setting.getColumnWidths().forEach(s -> {
            Optional<KfndtAnpGridColWidth> exist = entities.stream().filter(e -> e.pk.attendanceItemId == s.getAttendanceItemId()).findFirst();
            if (exist.isPresent()) {
                KfndtAnpGridColWidth toBeUpdate = exist.get();
                toBeUpdate.columnWidth = s.getColumnWidth();
                this.commandProxy().update(toBeUpdate);
            } else {
                KfndtAnpGridColWidth toBeInsert = new KfndtAnpGridColWidth(
                        new KfndtAnpGridColWidthPk(setting.getEmployeeId(), s.getAttendanceItemId()),
                        s.getColumnWidth()
                );
                this.commandProxy().insert(toBeInsert);
            }
        });
    }

    @Override
    public void delete(String employeeId) {
        List<KfndtAnpGridColWidth> entities = this.queryProxy().query(GET_LIST_SETTING_QUERY, KfndtAnpGridColWidth.class)
                .setParameter("employeeId", employeeId).getList();
        this.commandProxy().removeAll(entities);
    }

    @Override
    public Optional<AnyPeriodCorrectionGridColumnWidth> get(String employeeId) {
        List<KfndtAnpGridColWidth> entities = this.queryProxy().query(GET_LIST_SETTING_QUERY, KfndtAnpGridColWidth.class)
                .setParameter("employeeId", employeeId).getList();
        return entities.isEmpty() ? Optional.empty() : Optional.of(new AnyPeriodCorrectionGridColumnWidth(
                employeeId,
                entities.stream().map(e -> new DisplayItemColumnWidth(
                        e.pk.attendanceItemId,
                        e.columnWidth
                )).collect(Collectors.toList())
        ));
    }
}
