package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.absenceleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class WorkTimeIsReflect {
	/**	・反映できるフラグ	 */
	private boolean isReflect;
	/**
	 * ・反映就業時間帯
	 */
	private String workTimeCode; 
}
