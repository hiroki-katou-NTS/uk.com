package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.absenceleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author do_dt
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class StartEndTimeIsReflect {
	/**反映できるフラグ	 */
	private boolean chkReflect;
	/**反映開始時刻	 */
	private Integer startTime;
	/**反映終了時刻	 */
	private Integer endTime;
}
