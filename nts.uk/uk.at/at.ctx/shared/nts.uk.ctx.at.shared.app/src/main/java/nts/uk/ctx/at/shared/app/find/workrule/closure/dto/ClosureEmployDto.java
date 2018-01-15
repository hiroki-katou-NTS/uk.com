package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureEmployDto {
	//Employ data contain closure ID
	List<EmpCdNameDto> empCdNameList;
	//Check box data
	List<ClosureCdNameDto> closureCdNameList;
}
