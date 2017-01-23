package nts.uk.ctx.core.app.insurance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryInfoDto extends HistoryInsuranceDto {
	private boolean takeover;

}
