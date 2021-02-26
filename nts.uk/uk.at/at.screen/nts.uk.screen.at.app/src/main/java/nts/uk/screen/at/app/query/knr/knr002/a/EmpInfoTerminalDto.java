package nts.uk.screen.at.app.query.knr.knr002.a;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Getter
@Setter
public class EmpInfoTerminalDto {

	private String empInfoTerCode;
	
	private String empInfoTerName;
	
	private int modelEmpInfoTer;
	
	private String workLocationCd;
	
	private String workLocationName;
	
	private String signalLastTime;
	
	private int terminalCurrentState;
	
	private Boolean requestFlag;
}
