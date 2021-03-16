package nts.uk.ctx.at.schedule.app.command.workrequest;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmitWorkRequestCmd {

	private List<DataOneDateScreenKsuS02> listData = new ArrayList<>();

	private String startPeriod;

	private String endPeriod;

	public SubmitWorkRequestCmd(List<DataOneDateScreenKsuS02> listData, String startPeriod, String endPeriod) {
		super();
		this.listData = listData;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}
}
