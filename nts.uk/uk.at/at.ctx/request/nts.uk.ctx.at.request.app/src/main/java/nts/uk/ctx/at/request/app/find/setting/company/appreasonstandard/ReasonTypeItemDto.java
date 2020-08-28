package nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
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
	
	public ReasonTypeItem toDomain() {
		return new ReasonTypeItem(
				new AppStandardReasonCode(appStandardReasonCD), 
				displayOrder, 
				defaultValue, 
				new ReasonForFixedForm(reasonForFixedForm)
		);
	}
	
}
