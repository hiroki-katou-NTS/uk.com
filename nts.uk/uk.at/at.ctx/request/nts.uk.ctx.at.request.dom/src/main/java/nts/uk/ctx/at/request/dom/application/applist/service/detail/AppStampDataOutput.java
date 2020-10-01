package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppStampDataOutput {
	
	/**
	 * 申請内容
	 */
	private String appContent;
	
	/**
	 * 申請種類表示（Optional）
	 */
	private Optional<ApplicationTypeDisplay> opAppTypeDisplay;
	
}
