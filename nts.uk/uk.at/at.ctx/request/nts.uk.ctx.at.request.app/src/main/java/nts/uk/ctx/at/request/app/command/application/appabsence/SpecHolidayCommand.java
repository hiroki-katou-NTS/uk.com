package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecHolidayCommand {
		//続柄コード
		private String relationCD;
		//喪主チェック
		private Boolean mournerCheck;
		//続柄理由
		private String relaReason;
}
