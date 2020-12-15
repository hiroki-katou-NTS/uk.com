package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;

@AllArgsConstructor
@Getter
@Setter
public class RemoteSettingsDto {

	private int majorNo;
	
	private String majorClassification;
	
	private int smallNo;
	
	private String smallClassification;
	
	private String variableName;
	
	private int inputType;
	
	private int numberOfDigits;
	
	private String inputRange;
	
	private String currentValue;
	
	private String updateValue;
	
	public static List<RemoteSettingsDto> toDto(List<TimeRecordSetFormat> lstTRSetFormat, List<TimeRecordSetUpdate> lstTRecordSetUpdate) {
		List<RemoteSettingsDto> listDto = lstTRSetFormat.stream()
													.map(e -> new RemoteSettingsDto(e.getMajorNo().v(),
																e.getMajorClassification().v(), e.getSmallNo().v(), e.getSmallClassification().v(),
																e.getVariableName().v(), e.getType().value, e.getNumberOfDigits().v(),
																e.getInputRange().v(), e.getSettingValue().v(),
																getUpdateValueByName(e.getVariableName().v(), lstTRecordSetUpdate)))
													.collect(Collectors.toList());
		return listDto;
	}
	
	public static String getUpdateValueByName(String variableName, List<TimeRecordSetUpdate> lstTRecordSetUpdate) {
		return lstTRecordSetUpdate.stream().filter(e -> e.getVariableName().equals(variableName)).findFirst().get().getUpdateValue().v();
	}


}
