package nts.uk.ctx.at.request.app.find.setting.applicationreason;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 申請定型理由
 * 
 * @author ducpm
 *
 */
@AllArgsConstructor
@Value
public class ApplicationReasonDto {
	String companyId;
	/**
	 * 申請種類
	 */
	int appType;
	
	/**
	 * 理由ID
	 */
	String reasonID;
	/**
	 * 表示順
	 */
	int displayOrder;
	/**
	 * 定型理由
	 */
	String reasonTemp;
	/**
	 * 既定
	 */
	int defaultFlg;
	
		
}
