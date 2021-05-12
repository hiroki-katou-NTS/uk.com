package nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu;

import java.util.List;

public interface AlarmListWebMenuRepository {
    List<AlarmListWebMenu> getAll(String companyId);
}
