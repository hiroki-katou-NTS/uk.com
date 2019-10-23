package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NumberOfRemainOutput {
	//年休残数
	private Double yearRemain;
	//代休残数
	private Double subHdRemain;
	//振休残数
	private Double subVacaRemain;
	//ストック休暇残数
	private Double stockRemain;
	
	public static NumberOfRemainOutput init(Double yearRemain, Double subHdRemain, Double subVacaRemain, Double stockRemain){
		return new NumberOfRemainOutput(yearRemain == null ? new Double(0L) : yearRemain,
						subHdRemain == null ? new Double(0L) : subHdRemain,
						subVacaRemain == null ? new Double(0L) : subVacaRemain,
						stockRemain == null ? new Double(0L) : stockRemain);
	}
}
