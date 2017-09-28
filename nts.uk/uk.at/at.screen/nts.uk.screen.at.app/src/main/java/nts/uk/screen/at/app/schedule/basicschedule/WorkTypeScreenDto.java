package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTypeScreenDto {
	/* 勤務種類コード */
	private String workTypeCode;
	/* 勤務種類名称 */
	private String name;
	/* 勤務種類略名 */
	private String abbreviationName;
	/* 勤務種類記号名 */
	private String symbolicName;
	/* 勤務種類備考 */
	private String memo;
}
