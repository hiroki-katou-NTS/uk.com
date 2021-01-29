package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.Data;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 勤怠項目表示内容
 */
@Data
public class AttendanceItemDisplayContents extends ValueObject {

    private PrimitiveValueOfAttendanceItem primitiveValue;
    // 勤怠項目ID
    private int attendanceItemId;
    // 勤怠項目名称
    private String attendanceName;

    private CommonAttributesOfForms commonAttributesOfForms;
    // 順位
    private int rank;


}
