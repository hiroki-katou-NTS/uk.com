package nts.uk.ctx.at.request.dom.application.workchange.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppWorkChangeOutput {
//	勤務変更申請の表示情報
	private AppWorkChangeDispInfo appWorkChangeDispInfo;
//	勤務変更申請＜Optional＞
	private Optional<AppWorkChange> appWorkChange;
}
