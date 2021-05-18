package nts.uk.ctx.at.function.infra.repository.alarm.alarmlist.webmenu;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenu;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenuRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaAlarmListWebMenuRepository extends JpaRepository implements AlarmListWebMenuRepository {
    @Override
    public List<AlarmListWebMenu> getAll(String companyId) {
        return new ArrayList<>();
    }
}
