package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddAppWorkChangeCommand {
	
//	画面モード
	private Boolean mode;
//	会社ID
	private String companyId;
//	申請 
	private ApplicationDto applicationDto;
//	勤務変更申請 
	private AppWorkChangeDto appWorkChangeDto;
//	休日の申請日<List>
	private List<String> holidayDates;
//	メールサーバ設定済区分
	private Boolean isMail;
	
	private AppDispInfoStartupDto appDispInfoStartupDto;
}
