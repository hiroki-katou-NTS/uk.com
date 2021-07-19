package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScheFunctionControlDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScheModifyAuthCtrlByPersonDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScheModifyAuthCtrlCommonDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IndividualDisplayControlDto {

	public List<ScheModifyAuthCtrlCommonDto> scheModifyAuthCtrlCommon;
	
	public List<ScheModifyAuthCtrlByPersonDto> scheModifyAuthCtrlByPerson;
	
	public ScheFunctionControlDto scheFunctionControl;
	
}
