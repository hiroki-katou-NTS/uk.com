package nts.uk.ctx.at.function.infra.repository.alarm.alarmlist.webmenu;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenu;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenuRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.webmenu.KfndtAlarmWebMenu;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaAlarmListWebMenuRepository extends JpaRepository implements AlarmListWebMenuRepository {
    @Override
    public List<AlarmListWebMenu> getAll(String companyId) {
        List<AlarmListWebMenu> result = new ArrayList<>();
        this.queryProxy().query("select a from KfndtAlarmWebMenu a where a.pk.cid = :companyId", KfndtAlarmWebMenu.class)
                .setParameter("companyId", companyId)
                .getList()
                .stream()
                .collect(Collectors.groupingBy(KfndtAlarmWebMenu::getGroupKey)).forEach((key, value) -> {
            result.add(KfndtAlarmWebMenu.toDomain(value));
        });
        return result;
    }
}
