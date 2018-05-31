package nts.uk.ctx.at.request.pub.screen;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ApplicationDeadlineExport {
	/**
	 * 「申請締切設定」．利用区分
	 */
	private boolean useApplicationDeadline;
	
	/**
	 * 申請締切日
	 */
	private GeneralDate dateDeadline;
}
