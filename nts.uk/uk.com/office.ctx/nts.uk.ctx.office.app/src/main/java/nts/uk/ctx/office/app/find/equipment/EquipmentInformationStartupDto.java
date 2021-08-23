package nts.uk.ctx.office.app.find.equipment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EquipmentInformationStartupDto {
	
	// List<設備情報>
	private final List<EquipmentInformationDto> equipmentInformationList;
	
	// TODO: List<設備分類>
}
