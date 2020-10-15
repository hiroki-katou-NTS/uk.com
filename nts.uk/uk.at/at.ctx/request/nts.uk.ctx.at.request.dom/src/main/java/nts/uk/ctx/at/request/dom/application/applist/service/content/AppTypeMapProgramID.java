package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppTypeMapProgramID {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * プログラムID
	 */
	private String programID;
	
	/**
	 * 申請種類表示
	 */
	private ApplicationTypeDisplay applicationTypeDisplay;
}
