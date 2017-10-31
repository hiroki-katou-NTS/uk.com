package nts.uk.ctx.bs.employee.app.find.person.item;

import java.util.List;

import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

public class ItemSetCurJobPos extends CtgItemFixDto{
	private List<ItemCurrentJobPosDto> lstCurrentJobPosDto;
	
	private ItemSetCurJobPos(List<ItemCurrentJobPosDto> lstCurrentJobPosDto){
		super();
		this.ctgItemType = CtgItemType.CURR_JOB_POS;
		this.lstCurrentJobPosDto = lstCurrentJobPosDto;
	}
	
	public static ItemSetCurJobPos createFromJavaType(List<ItemCurrentJobPosDto> lstCurrentJobPosDto){
		return new ItemSetCurJobPos(lstCurrentJobPosDto);
	}
}
