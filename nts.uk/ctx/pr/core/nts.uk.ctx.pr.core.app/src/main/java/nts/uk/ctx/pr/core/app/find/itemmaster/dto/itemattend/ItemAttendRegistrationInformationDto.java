package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemattend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemAttendRegistrationInformationDto {
	private ItemAttendDto itemAttend;
	private ItemMasterDto itemMaster;

	public static ItemAttendRegistrationInformationDto fromDomain(ItemAttend itemAttend, ItemMaster itemMaster) {
		return new ItemAttendRegistrationInformationDto(ItemAttendDto.fromDomain(itemAttend),
				ItemMasterDto.fromDomain(itemMaster));
	}
}
