package nts.uk.screen.at.app.query.knr.knr002.e;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Getter
@Setter
public class TimeRecordSetFormatBakEDto {

	private String backupDate;
	
	private String empInfoTerCode;
	
	private String empInfoTerName;
	
	private int modelEmpInfoTer;
}
