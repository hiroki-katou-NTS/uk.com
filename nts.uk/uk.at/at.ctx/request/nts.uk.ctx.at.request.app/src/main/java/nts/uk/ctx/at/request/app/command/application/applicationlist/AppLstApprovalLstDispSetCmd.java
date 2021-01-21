package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppLstApprovalLstDispSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Data
public class AppLstApprovalLstDispSetCmd {
	/**
	 * 開始日表示
	 */
	private String startDateDisp;
	
	/**
	 * 事前事後区分表示
	 */
	private int prePostAtrDisp;
	
	/**
	 * 終了日表示
	 */
	private String endDateDisp;
	
	/**
	 * 所属職場名表示
	 */
	private int workplaceNameDisp;
	
	/**
	 * 申請対象日に対して警告表示
	 */
	private int appDateWarningDisp;
	
	// AnhNM add to domain
	public AppLstApprovalLstDispSet toDomain() {
		return new AppLstApprovalLstDispSet(GeneralDate.fromString(startDateDisp, "yyyy/MM/dd"),
				prePostAtrDisp,
				GeneralDate.fromString(endDateDisp, "yyyy/MM/dd"),
				workplaceNameDisp,
				appDateWarningDisp);
	}
}
