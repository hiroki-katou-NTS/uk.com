package nts.uk.ctx.office.app.command.equipment.data;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class DeleteEquipmentDataCommand {

	/**
	 * 設備コード
	 */
	private final String equipmentCode;
	
	/**
	 * 利用日
	 */
	private final GeneralDate useDate;
	
	/**
	 * 入力日
	 */
	private final GeneralDateTime inputDate;
}
