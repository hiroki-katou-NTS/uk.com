package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectionItemsDto {

	private int majorNo;
	
	private String majorClassification;
	
	private int smallNo;
	
	private String smallClassification;
	
	private String variableName;
	
	private int inputType;
	
	private int numberOfDigits;
	
	private String settingValue;
	
	private String inputRange;
	
	private String currentValue;
	
	private String updateValue;
	
	private String empInfoTerName; 
	
	private String romVersion;
	
	private int modelEmpInfoTer;
	
	public static List<SelectionItemsDto> toDto(TimeRecordSetFormatList timeRecordSetFormatList, Optional<TimeRecordSetUpdateList> timeRecordSetUpdateList) {
		List<SelectionItemsDto> listDto = timeRecordSetFormatList.getLstTRSetFormat().stream()
													.map(e -> new SelectionItemsDto(e.getMajorNo().v(),
															e.getMajorClassification().v(), e.getSmallNo().v(), e.getSmallClassification().v(),
															e.getVariableName().v(), e.getType().value, e.getNumberOfDigits().v() == null ? 0 : e.getNumberOfDigits().v(),
															e.getSettingValue().v(), e.getInputRange().v(),getCurrentAndUpdateValueFormat(e.getCurrentValue().v()),
															getUpdateValueByName(e.getVariableName().v(), timeRecordSetUpdateList),
															timeRecordSetFormatList.getEmpInfoTerName().v(),
															timeRecordSetFormatList.getRomVersion().v(),
															timeRecordSetFormatList.getModelEmpInfoTer().value))
													.collect(Collectors.toList());
		return listDto;
	}
	
	public static String getUpdateValueByName(String variableName, Optional<TimeRecordSetUpdateList> timeRecordSetUpdateList) {
		if (!timeRecordSetUpdateList.isPresent()) {
			return "";
		}
		List<TimeRecordSetUpdate> filteredList = timeRecordSetUpdateList.get().getLstTRecordSetUpdate().stream().filter(e -> e.getVariableName().v().equals(variableName)).collect(Collectors.toList());
		if (filteredList.isEmpty()) {
			return "";
		}
		return getCurrentAndUpdateValueFormat(filteredList.stream().findFirst().get().getUpdateValue().v());
	}
	
	public static String getCurrentAndUpdateValueFormat(String currentValue) {
		String str = currentValue.replaceAll("^0+(?!$)", "");
		return str;
	}
}
