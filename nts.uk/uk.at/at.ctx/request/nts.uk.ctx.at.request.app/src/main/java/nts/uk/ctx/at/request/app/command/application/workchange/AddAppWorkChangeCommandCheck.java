package nts.uk.ctx.at.request.app.command.application.workchange;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddAppWorkChangeCommandCheck {
//	画面モード
	private Boolean mode;
//	会社ID
	private String companyId;
//	申請 
	private ApplicationDto applicationDto;
//	勤務変更申請 
	private AppWorkChangeDto appWorkChangeDto;
//	承認ルートエラー情報
	private List<MsgErrorOutput> opMsgErrorLst;
	// add param to handle 登録時チェック処理（全申請共通） 
	private AppDispInfoStartupDto appDispInfoStartupDto;
	
	private AppWorkChangeDispInfoCmd appWorkChangeDispInfo;
}
