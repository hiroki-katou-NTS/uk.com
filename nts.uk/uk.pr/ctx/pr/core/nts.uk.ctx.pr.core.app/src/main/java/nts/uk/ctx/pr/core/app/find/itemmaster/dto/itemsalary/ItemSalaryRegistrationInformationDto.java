package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalary;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalarybd.ItemSalaryBDDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSalaryRegistrationInformationDto {
	private ItemSalaryDto itemSalary;
	private ItemMasterDto itemMaster;
	private List<ItemSalaryBDDto> itemSalaryBDList;

	public static ItemSalaryRegistrationInformationDto fromDomain(ItemSalary itemSalary,
			ItemMaster itemMaster, List<ItemSalaryBD> itemSalaryBDList) {
		List<ItemSalaryBDDto> itemSalaryBDListDto = itemSalaryBDList.stream()
				.map(item -> ItemSalaryBDDto.fromDomain(item)).collect(Collectors.toList());
		return new ItemSalaryRegistrationInformationDto(ItemSalaryDto.fromDomain(itemSalary),
				ItemMasterDto.fromDomain(itemMaster), itemSalaryBDListDto);
	}

}
