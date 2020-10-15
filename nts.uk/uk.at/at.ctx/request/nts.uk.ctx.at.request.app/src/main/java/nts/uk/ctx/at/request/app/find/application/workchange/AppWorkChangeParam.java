package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeOutputCmd;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeParam {
//	画面モード
	private Boolean mode;
//	会社ID
	private String companyId;
//	社員ID
	private String employeeId;
//	申請対象日リスト
	private List<String> listDates;
//	勤務変更申請の表示情報
	private AppWorkChangeOutputDto appWorkChangeOutputDto;
	
//	勤務変更申請の表示情報
	private AppWorkChangeOutputCmd appWorkChangeOutputCmd;
	
//	勤務変更申請 
	private AppWorkChangeDto appWorkChangeDto;
}
