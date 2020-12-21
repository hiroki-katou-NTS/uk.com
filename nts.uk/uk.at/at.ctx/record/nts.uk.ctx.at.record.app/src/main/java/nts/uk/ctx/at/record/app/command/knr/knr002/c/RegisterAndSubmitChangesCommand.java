package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RegisterAndSubmitChangesCommand {
	
	private List<String> empInfoTerCode;
	
	private String empInfoTerName; 
	
	private String romVersion;
	
	private int modelEmpInfoTer;
	
	private List<TimeRecordSetUpdateDto> listTimeRecordSetUpdateDto;
	
}
