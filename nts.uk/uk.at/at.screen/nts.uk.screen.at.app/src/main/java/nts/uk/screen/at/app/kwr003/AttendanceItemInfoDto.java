package nts.uk.screen.at.app.kwr003;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AttendanceItemInfoDto {
    private List<AttItemDto>listDaily;
    private List<AttItemDto>listMonthly;



}
