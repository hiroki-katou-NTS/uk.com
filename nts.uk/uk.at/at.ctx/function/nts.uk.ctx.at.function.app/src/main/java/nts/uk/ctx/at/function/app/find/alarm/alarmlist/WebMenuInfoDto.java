package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebMenuInfoDto {
    private String programId;
    private String menuName;
    private String url;
    private String queryString;
}
