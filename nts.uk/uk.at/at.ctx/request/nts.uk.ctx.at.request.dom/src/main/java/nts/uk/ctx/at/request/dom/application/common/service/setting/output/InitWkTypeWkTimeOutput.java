package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class InitWkTypeWkTimeOutput {
	
	/**
	 * 勤務種類コード
	 */
	private String workTypeCD;
	
	/**
	 * 就業時間帯コード
	 */
	private String workTimeCD;
	
}
