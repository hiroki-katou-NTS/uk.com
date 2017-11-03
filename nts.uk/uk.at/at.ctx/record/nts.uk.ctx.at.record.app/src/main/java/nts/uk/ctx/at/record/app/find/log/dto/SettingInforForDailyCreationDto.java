package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.log.PartResetClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.SettingInforForDailyCreation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingInforForDailyCreationDto {

	/**
	 *  0 :通常実行
	 *  1 :再実行
	 */
	private int executionType;
	/**
	 * executionType : name Japan
	 */
	private String executionTypeName;
	
	int creationType;
	
	PartResetClassification partResetClassification;
	
	
	public static SettingInforForDailyCreationDto fromDomain(SettingInforForDailyCreation domain) {
		return new SettingInforForDailyCreationDto(
				domain.getExecutionType().value,
				domain.getExecutionType().nameId,
				domain.getCreationType().value,
				domain.getPartResetClassification().get()
				
				);
		
	}
}


