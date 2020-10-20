package nts.uk.ctx.at.shared.dom.application.common;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *         申請日
 */
@Getter
public class ApplicationDateShare {
	/**
	 * 申請日
	 */
	private GeneralDate applicationDate;

	public ApplicationDateShare(GeneralDate applicationDate) {
		this.applicationDate = applicationDate;
	}
}
