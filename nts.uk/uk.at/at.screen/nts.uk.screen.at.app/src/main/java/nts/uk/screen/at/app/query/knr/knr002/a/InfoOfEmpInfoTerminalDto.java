package nts.uk.screen.at.app.query.knr.knr002.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author dungbn
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoOfEmpInfoTerminalDto {
	
	private int numOfRegTerminals;
	
	private int numNormalState;
	
	private int numAbnormalState;
	
	private int numUntransmitted;
	
	private List<EmpInfoTerminalDto> listEmpInfoTerminalDto;
	
	private int managementSection;
	
}
