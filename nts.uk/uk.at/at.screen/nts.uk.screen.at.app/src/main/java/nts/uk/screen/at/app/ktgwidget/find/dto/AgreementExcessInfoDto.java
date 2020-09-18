package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgreementExcessInfoDto {
	/** 超過回数 */
	private int excessTimes;
	/** 年月リスト */
	private List<Integer> yearMonths;
}
