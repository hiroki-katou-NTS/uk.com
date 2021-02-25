package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface BentomenuAdapter {

    Optional<SWkpHistExport> findBySid(String employeeId, GeneralDate baseDate);
}
