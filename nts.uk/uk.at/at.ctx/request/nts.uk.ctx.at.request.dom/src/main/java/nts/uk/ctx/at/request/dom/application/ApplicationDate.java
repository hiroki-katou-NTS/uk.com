package nts.uk.ctx.at.request.dom.application;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.申請日
 * @author Doan Duy Hung
 *
 */
@Getter
public class ApplicationDate {
	
	/**
	 * 申請日
	 */
	private GeneralDate applicationDate;
	
	public ApplicationDate(GeneralDate applicationDate) {
		this.applicationDate = applicationDate;
	}
	
}
