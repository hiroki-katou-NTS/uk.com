package nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.WeeklyAgreegateResult;

import java.util.List;

@AllArgsConstructor
@Data
public class AppointmentDto<T> {
    //List<勤務予定（勤務情報）dto>
    private List<WorkScheduleWorkInforDto> workInforDtoList;

    //List<週目, 労働時間, 休日日数>
    private List<WeeklyAgreegateResult> agreegateResults;
}