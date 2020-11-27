package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamHdWorkDetail {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 申請ID
	 */
	private String applicationId;
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupDto appDispInfoStartup;
}
