package nts.uk.file.com.app.equipment.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipmentDataQuery {

	/**
	 * Optional<設備分類コード>
	 */
	private String equipmentClsCode;
	
	/**
	 * Optional<設備コード>
	 */
	private String equipmentCode;
	
	/**
	 * 年月
	 */
	private int ym;
}
