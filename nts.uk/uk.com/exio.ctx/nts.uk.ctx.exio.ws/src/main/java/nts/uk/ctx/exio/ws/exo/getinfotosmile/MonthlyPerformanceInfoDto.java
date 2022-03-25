package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyPerformanceInfoDto {
	// 締めID
	private int closureId;
	
	// 締め日
	private int closureDay;
	
	// 末日とする
	private boolean isLastDay;

	public MonthlyPerformanceInfoDto(int closureId, int closureDay, boolean isLastDay) {
		super();
		this.closureId = closureId;
		this.closureDay = closureDay;
		this.isLastDay = isLastDay;
	}
	
}
