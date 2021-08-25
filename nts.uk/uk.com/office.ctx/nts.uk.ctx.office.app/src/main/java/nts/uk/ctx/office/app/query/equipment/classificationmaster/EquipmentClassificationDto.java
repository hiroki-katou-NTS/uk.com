package nts.uk.ctx.office.app.query.equipment.classificationmaster;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipmentClassificationDto {
	// コード
	private String code;
	
	// 名称
	private String name;
}
