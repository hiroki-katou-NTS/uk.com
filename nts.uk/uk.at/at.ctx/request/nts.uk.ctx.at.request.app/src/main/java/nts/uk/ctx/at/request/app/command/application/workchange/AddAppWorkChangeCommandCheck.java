package nts.uk.ctx.at.request.app.command.application.workchange;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
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
	private int isError;
}
