package nts.uk.ctx.at.schedule.app.find.schedule.task.taskpalette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ksu001bRequest {
	private Integer targetUnit;
	private String targetId;
	private Integer page;
	private String referenceDate;
}
