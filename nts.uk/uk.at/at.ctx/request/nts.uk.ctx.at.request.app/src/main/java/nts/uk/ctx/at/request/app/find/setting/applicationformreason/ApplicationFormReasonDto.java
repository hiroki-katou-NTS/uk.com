package nts.uk.ctx.at.request.app.find.setting.applicationformreason;

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
public class ApplicationFormReasonDto {
	String companyId;
	/**
	 * 申請種類
	 */
	int appType;
	/**
	 * 表示順
	 */
	int displayOrder;
	/**
	 * 既定
	 */
	int defaultFlg;
}
