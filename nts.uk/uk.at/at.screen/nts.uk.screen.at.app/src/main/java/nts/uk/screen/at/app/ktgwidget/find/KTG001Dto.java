package nts.uk.screen.at.app.ktgwidget.find;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;


@Data
@NoArgsConstructor
public class KTG001Dto {
	private boolean approve;
	private Integer currentOrNextMonth;
	private List<TargetDto> listTarget;
	
	public KTG001Dto(boolean approve, Integer currentOrNextMonth, List<TargetDto> listTarget) {
		super();
		this.approve = approve;
		this.currentOrNextMonth = currentOrNextMonth;
		this.listTarget = listTarget;
	}
	
}
