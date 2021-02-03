package nts.uk.screen.at.app.query.kdp.kdps01.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

/**
 * 
 * @author sonnlb
 *
 *
 *         実績値<勤怠項目ID,値,年月日>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemValueDto {

	/**
	 * 勤怠項目ID
	 */
	private int itemId;
	/**
	 * 値
	 */

	private String value;
	/**
	 * 年月日
	 */

	private GeneralDate date;

	public static ItemValueDto fromDomain(ItemValue domain, GeneralDate date) {
		return new ItemValueDto(domain.getItemId(), domain.getValue(), date);
	}
}
