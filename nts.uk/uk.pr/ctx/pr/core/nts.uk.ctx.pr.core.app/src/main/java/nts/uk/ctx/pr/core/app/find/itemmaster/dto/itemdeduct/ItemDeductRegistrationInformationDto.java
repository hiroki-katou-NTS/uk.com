package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeduct;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeductbd.ItemDeductBDDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDeductRegistrationInformationDto {
	private ItemDeductDto itemDeduct;
	private ItemMasterDto itemMaster;
	private List<ItemDeductBDDto> itemDeductBDList;

	public static ItemDeductRegistrationInformationDto fromDomain(ItemDeduct itemDeduct, ItemMaster itemMaster,
			List<ItemDeductBD> itemDeductBDList) {
		List<ItemDeductBDDto> itemDeductBDListDto = itemDeductBDList.stream()
				.map(item -> ItemDeductBDDto.fromDomain(item)).collect(Collectors.toList());
		return new ItemDeductRegistrationInformationDto(ItemDeductDto.fromDomain(itemDeduct),
				ItemMasterDto.fromDomain(itemMaster), itemDeductBDListDto);
	}
}
