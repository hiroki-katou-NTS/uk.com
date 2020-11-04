package nts.uk.ctx.at.function.ws.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateConfigdetailDto {
    private String code;
    private String name;
    private int settingCategory;
    private List<OutputItemDto> outputItemList;

    @AllArgsConstructor
    @Getter
    @Setter
    public class OutputItemDto {
        private int rank;
        private String name;
        private boolean printTargetFlag;
        private int independentCalculaClassification;
        private int dailyMonthlyClassification;
        private int itemDetailAttributes;
        private List<SelectionAttendanceItemDto> selectedAttendanceItemList;
    }
    @AllArgsConstructor
    @Getter
    @Setter
    public class SelectionAttendanceItemDto {
        private int operator;

        private int attendanceItemId;
    }
}

