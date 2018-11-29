package nts.uk.ctx.at.schedule.pub.appreflectprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkChangeCommonReflectSchePubParam {
	private CommonReflectSchePubParam common;
	private Integer excludeHolidayAtr;
}
