package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SettingNo65;

@Getter
public class ParamCheckRegister {
	private SettingNo65 setNo65;
	//代休残数
	private int numberSubHd;
	//振休残数
	private int numberSubVaca;
}
