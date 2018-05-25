package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordRepositoty;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaAttendanceRecordItemRepositoty extends JpaAttendanceRecordRepository implements AttendanceRecordRepositoty {

    @Override
    public void deleteAttendanceRecord(String companyId, ExportSettingCode exportSettingCode) {
        List<KfnstAttndRecItem> items = this.findAllAttendanceRecordItem(companyId, exportSettingCode);
        if (items != null && !items.isEmpty()) {
            this.removeAllAttndRecItem(items);
            this.getEntityManager().flush();
        }


    }
}
