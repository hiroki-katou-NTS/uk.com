package nts.uk.ctx.at.function.dom.adapter.alarm;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface EmployeeAlarmListAdapter {
    List<String> getListEmployeeId(String cid, GeneralDate referenceDate);
}
