package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmpInfoItemDataDto;

@Data
public class CtgItemOptionalDto {
	private EmpInfoCtgDataDto empInfoCtgData;
	private List<ItemEmpInfoItemDataDto> lstEmpInfoItemData;
	
	public void addToItemEmpInfoItemDataDto(ItemEmpInfoItemDataDto obj){
		if(lstEmpInfoItemData == null) lstEmpInfoItemData = new ArrayList<>();
		lstEmpInfoItemData.add(obj);
	}
}
