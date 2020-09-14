package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordRepositoty;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

/**
 * The type JpaAttendanceRecordItemRepositoty.
 *
 * @author locph
 */
@Stateless
public class JpaAttendanceRecordItemRepositoty extends JpaAttendanceRecordRepository implements AttendanceRecordRepositoty {

    /**
     * deleteAttendanceRecord
     *
     * @param companyId         the company id
     * @param exportSettingCode the export setting code
     */
    @Override
    public void deleteAttendanceRecord(String layoutId) {
        List<KfnmtRptWkAtdOutatd> items = this.findAllAttendanceRecordItem(layoutId);
        if (items != null && !items.isEmpty()) {
            this.removeAllAttndRecItem(items);
            this.getEntityManager().flush();
        }


    }
}
