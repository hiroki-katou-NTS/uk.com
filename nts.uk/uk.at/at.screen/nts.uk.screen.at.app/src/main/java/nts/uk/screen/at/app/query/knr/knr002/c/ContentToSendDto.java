package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentToSendDto {

	private int majorNo;
	
	private String majorClassification;
	
	private int smallNo;
	
	private String smallClassification;
	
	private String variableName;
	
	private String inputRange;
	
	private String updateValue;
	
	public static List<ContentToSendDto> toDto(TimeRecordSetFormatList timeRecordSetFormatList, TimeRecordSetUpdateList timeRecordSetUpdateList) {
		
		List<ContentToSendDto> listDto = new ArrayList<ContentToSendDto>();
		
		List<String> variableNameList = timeRecordSetFormatList.getLstTRSetFormat().stream()
															   .map(e -> e.getVariableName().v())
															   .collect(Collectors.toList());
		
		for (int i = 0; i < timeRecordSetUpdateList.getLstTRecordSetUpdate().size(); i++) {
			
			String variableName = timeRecordSetUpdateList.getLstTRecordSetUpdate().get(i).getVariableName().v();
			if (variableNameList.contains(variableName)) {
				TimeRecordSetFormat timeRecordSetFormat = timeRecordSetFormatList.getLstTRSetFormat().stream()
																.filter(e -> e.getVariableName().v().equals(variableName))
																.findFirst().get();
				
				ContentToSendDto dto = new ContentToSendDto(
						timeRecordSetFormat.getMajorNo().v(),
						timeRecordSetFormat.getMajorClassification().v(),
						timeRecordSetFormat.getSmallNo().v(),
						timeRecordSetFormat.getSmallClassification().v(),
						timeRecordSetFormat.getVariableName().v(),
						timeRecordSetFormat.getInputRange().v(),
						getUpdateValueFormat(timeRecordSetUpdateList.getLstTRecordSetUpdate().get(i).getUpdateValue().v()));
				
				listDto.add(dto);
			}
		}
		return listDto;
	}
	
	public static String getUpdateValueFormat(String updateValue) {
		String str = updateValue.replaceAll("^0+(?!$)", "");
		return str;
	}
}
