package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReasonTypeItemDto {
	/**
	 * 理由コード
	 */
	private int appStandardReasonCD; 
	
	/**
	 * 表示順
	 */
	private int displayOrder;
	
	/**
	 * 既定
	 */
	private boolean defaultValue;
	
	/**
	 * 定型理由
	 */
	private String reasonForFixedForm;
	
	public static ReasonTypeItemDto fromDomain(ReasonTypeItem reasonTypeItem) {
		return new ReasonTypeItemDto(
				reasonTypeItem.getAppStandardReasonCD().v(), 
				reasonTypeItem.getDisplayOrder(), 
				reasonTypeItem.isDefaultValue(), 
				reasonTypeItem.getReasonForFixedForm().v()
		);
	}
}
