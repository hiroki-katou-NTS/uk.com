package nts.uk.ctx.at.record.app.find.standardtime.dto;

import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumConstant;

@Data
public class AgreementOperationSettingDto {
	
	private List<EnumConstant> startingMonthEnum;
	
	private List<EnumConstant> numberTimesOverLimitTypeEnum;
	
	private List<EnumConstant> closingDateTypeEnum;
	
	private List<EnumConstant> closingDateAtrEnum;
	
	private List<EnumConstant> yearlyWorkTableAtrEnum;
	
	private List<EnumConstant> alarmListAtrEnum;
	
	private AgreementOperationSettingDetailDto agreementOperationSettingDetailDto;
	

}
