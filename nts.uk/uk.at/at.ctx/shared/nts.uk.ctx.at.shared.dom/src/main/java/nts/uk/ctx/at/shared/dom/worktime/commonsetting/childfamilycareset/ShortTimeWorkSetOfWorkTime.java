package nts.uk.ctx.at.shared.dom.worktime.commonsetting.childfamilycareset;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 就業時間帯の短時間勤務設定
 * @author ken_takasu
 *
 */
@Getter
@AllArgsConstructor
public class ShortTimeWorkSetOfWorkTime {

	private boolean childCareWorkUse;
	private boolean nursTimezoneWorkUse;
	private boolean employmentTimeDeduct;
	
}
