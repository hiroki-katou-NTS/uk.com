package nts.uk.screen.at.app.kdw013.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.EncouragedTargetApplication;

/**
 * 
 * @author Sonnlb
 *
 */
@AllArgsConstructor
@Data
public class EncouragedTargetApplicationDto {
	// 年月日
	private GeneralDate date;

	// 申請種類
	private Integer appType;

	public static EncouragedTargetApplicationDto fromDomain(EncouragedTargetApplication domain) {
		return new EncouragedTargetApplicationDto(domain.getDate(), domain.getAppType().value);
	}
}
