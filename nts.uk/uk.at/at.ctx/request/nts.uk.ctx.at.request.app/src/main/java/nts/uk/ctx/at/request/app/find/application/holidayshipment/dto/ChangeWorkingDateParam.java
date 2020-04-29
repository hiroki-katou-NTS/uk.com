package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanhpv
 * 振休振出申請起動時の表示情報
 */
@NoArgsConstructor
@Getter
@Setter
public class ChangeWorkingDateParam {

	public String workingDate;
	
	public String holidayDate;
	
	public DisplayInforWhenStarting displayInforWhenStarting;
}
