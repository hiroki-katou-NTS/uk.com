package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.Data;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.CreateWorkLedgerDisplayContentQuery;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Optional;

/**
 * 勤怠項目表示内容
 */
@Data
public class AttendanceItemDisplayContents extends ValueObject {

    private Optional<PrimitiveValueOfAttendanceItem> primitiveValue;
    // 勤怠項目ID
    private int attendanceItemId;
    // 勤怠項目名称
    private String attendanceName;

    private CommonAttributesOfForms commonAttributesOfForms;
    // 順位
    private Integer rank;

    public AttendanceItemDisplayContents(Optional<PrimitiveValueOfAttendanceItem> primitiveValue,
                                         int attendanceItemId,
                                         String attendanceName,
                                         MonthlyAttendanceItemAtr monthlyAttendanceAtr,
                                         Integer rank) {
        this.primitiveValue = primitiveValue;
        this.attendanceItemId = attendanceItemId;
        this.attendanceName = attendanceName;
        this.commonAttributesOfForms = CreateWorkLedgerDisplayContentQuery.convertMonthlyToAttForms(monthlyAttendanceAtr.value);
        this.rank = rank;
    }
}
