package nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNX
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseCategoryDto {

	/**
	 * 会社ID
	 */
	private String code;

	/**
	 * 看護区分コード
	 */
	private String name;
}
