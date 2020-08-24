package nts.uk.ctx.at.request.app.find.application.employment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RsvLeaGrantRemainingImportDto {
	/** 付与日1 */
	private String grantDate;
	/** 期限日 5*/
	private String deadline;
	/** 付与日数2 */
	private Double grantNumber;
	/** 使用数 3*/
	private Double usedNumber;
	/** 残日数4 */
	private Double remainingNumber;
	/** 使用期限 */
	private boolean expiredInCurrentMonth;
}