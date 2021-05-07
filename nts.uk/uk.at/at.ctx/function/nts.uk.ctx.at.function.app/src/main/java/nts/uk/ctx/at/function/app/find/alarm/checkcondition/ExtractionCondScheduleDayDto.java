package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtractionCondScheduleDayDto {
	// ID
	private String errorAlarmId;
	
	// 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private String name;
    
    private String errorAlarmMessage;
}
