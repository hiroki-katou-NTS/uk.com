package nts.uk.ctx.at.function.dom.adapter.application.importclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class ApplicationDeadlineImport {

	/**
	 * 「申請締切設定」．利用区分
	 */
	private boolean useApplicationDeadline;
	
	/**
	 * 申請締切日
	 */
	private GeneralDate dateDeadline;
}
