package nts.uk.ctx.bs.employee.app.find.person.item;

import java.util.List;

import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

public class SetCurJobPos extends CtgItemFixDto{
	private List<CurrentJobPosDto> lstCurrentJobPosDto;
	
	private SetCurJobPos(List<CurrentJobPosDto> lstCurrentJobPosDto){
		super();
		this.ctgItemType = CtgItemType.CURR_JOB_POS;
		this.lstCurrentJobPosDto = lstCurrentJobPosDto;
	}
	
	public static SetCurJobPos createFromJavaType(List<CurrentJobPosDto> lstCurrentJobPosDto){
		return new SetCurJobPos(lstCurrentJobPosDto);
	}
}
