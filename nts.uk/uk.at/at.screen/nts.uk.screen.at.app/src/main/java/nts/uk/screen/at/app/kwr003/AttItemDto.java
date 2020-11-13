package nts.uk.screen.at.app.kwr003;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;


@Getter
@Setter
public class AttItemDto {
    private int attendanceItemId;

    private String attendanceItemName;

    private int attendanceItemDisplayNumber;

    private Integer typeOfAttendanceItem;

    private int masterType;

    private Integer attributes;

    private String attributeName;

    public AttItemDto(int attendanceItemId, String attendanceItemName, int attendanceItemDisplayNumber,
                      Integer typeOfAttendanceItem, Integer attributes) {
        this.attendanceItemId = attendanceItemId;
        this.attendanceItemName = attendanceItemName;
        this.attendanceItemDisplayNumber = attendanceItemDisplayNumber;
        this.typeOfAttendanceItem = typeOfAttendanceItem;
        this.attributes = attributes;
        this.attributeName = attributes != null ? EnumAdaptor.valueOf(attributes, CommonAttributesOfForms.class).name() : null;
    }

}
