package nts.uk.ctx.at.function.app.find.dailyfix;

import java.util.List;

import lombok.Data;
@Data
public class AppliCalDaiCorrecDto {
	/** 呼び出す申請一覧 */
	private List<Integer> appTypes;

	public AppliCalDaiCorrecDto(List<Integer> appTypes) {
		super();
		this.appTypes = appTypes;
	}
}
