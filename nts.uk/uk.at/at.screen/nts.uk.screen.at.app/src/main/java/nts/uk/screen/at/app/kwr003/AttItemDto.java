package nts.uk.screen.at.app.kwr003;


import com.sun.xml.internal.bind.v2.model.core.Adapter;
import lombok.AllArgsConstructor;
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

    private int attributes;

    private String attributeName;

    public AttItemDto(int attendanceItemId,String attendanceItemName,int attendanceItemDisplayNumber,
                      Integer typeOfAttendanceItem,int attributes){
        this.attendanceItemId = attendanceItemId;
        this.attendanceItemName = attendanceItemName;
        this.attendanceItemDisplayNumber = attendanceItemDisplayNumber;
        this.typeOfAttendanceItem = typeOfAttendanceItem;
        this.attributes = attributes;
        this.attributeName = EnumAdaptor.valueOf(attendanceItemId,CommonAttributesOfForms.class).name();
    }

}
