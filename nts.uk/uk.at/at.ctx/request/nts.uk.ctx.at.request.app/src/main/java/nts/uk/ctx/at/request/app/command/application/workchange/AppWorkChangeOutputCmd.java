package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDetailScreenInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeOutputCmd {
	// 勤務変更申請の表示情報
	private AppWorkChangeDispInfoCmd appWorkChangeDispInfo;
	// 勤務変更申請＜Optional＞
	private AppWorkChangeDto appWorkChange;
	
	public AppWorkChangeOutput toDomain() {
		AppWorkChangeOutput appWorkChangeOutput = new AppWorkChangeOutput();
		appWorkChangeOutput.setAppWorkChangeDispInfo(appWorkChangeDispInfo.toDomain());
		Optional<AppWorkChange> appWorkChange = Optional.empty();
		AppDetailScreenInfoDto appDetailScreenInfoDto = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo();
		if (appDetailScreenInfoDto != null) {
			appWorkChange = Optional.ofNullable(this.appWorkChange.toDomain(appDetailScreenInfoDto.getApplication().toDomain()));
		}
		appWorkChangeOutput.setAppWorkChange(appWorkChange);
		return appWorkChangeOutput;
	}
}
