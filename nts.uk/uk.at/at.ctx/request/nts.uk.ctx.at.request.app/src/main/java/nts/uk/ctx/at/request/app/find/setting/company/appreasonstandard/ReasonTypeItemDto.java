package nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
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
	private String opReasonForFixedForm;
	
	public static ReasonTypeItemDto fromDomain(ReasonTypeItem reasonTypeItem) {
		return new ReasonTypeItemDto(
				reasonTypeItem.getAppStandardReasonCD().v(), 
				reasonTypeItem.getDisplayOrder(), 
				reasonTypeItem.isDefaultValue(), 
				reasonTypeItem.getOpReasonForFixedForm().map(x -> x.v()).orElse(null));
	}
	
	public ReasonTypeItem toDomain() {
		return new ReasonTypeItem(
				new AppStandardReasonCode(appStandardReasonCD), 
				displayOrder, 
				defaultValue, 
				opReasonForFixedForm == null ? Optional.empty() : Optional.of(new ReasonForFixedForm(opReasonForFixedForm)));
	}
	
}
