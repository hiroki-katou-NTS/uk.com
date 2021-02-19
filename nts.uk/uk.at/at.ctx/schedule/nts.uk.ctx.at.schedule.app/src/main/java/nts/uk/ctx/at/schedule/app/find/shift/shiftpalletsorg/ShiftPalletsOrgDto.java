package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftPalletsOrgDto {
	private String workplaceId;
	private int groupNo;
	private String groupName;
	private int groupUsageAtr;
	private String note;
	private List<ShiftPalletDto> patternItem;
	
	public ShiftPalletsOrgDto(ShiftPaletteOrg org, String workplaceId) {
		super();
		this.workplaceId = workplaceId;
		this.groupNo = org.getPage();
		this.groupName = org.getShiftPallet().getDisplayInfor().getShiftPalletName().toString();
		this.groupUsageAtr =org.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value;
		this.note = org.getShiftPallet().getDisplayInfor().getRemarks().toString();
		this.patternItem = org.getShiftPallet()
					.getCombinations()
					.stream()
					.map(c -> new ShiftPalletDto(c))
					.collect(Collectors.toList());
	
	}
}
