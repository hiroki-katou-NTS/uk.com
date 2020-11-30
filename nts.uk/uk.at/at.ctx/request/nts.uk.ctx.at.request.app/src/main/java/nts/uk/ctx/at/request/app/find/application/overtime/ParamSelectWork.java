package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@AllArgsConstructor
@NoArgsConstructor
public class ParamSelectWork {
	/*
	  	会社ID
		社員ID
		申請日<Optional>
		勤務種類コード
		就業時間帯コード
		SPR連携の開始時刻<Optional>
		SPR連携の終了時刻<Optional>
		表示する実績内容
		残業申請設定

	 */
	public String companyId;
	
	public String employeeId;
	
	public String date;
	
	public String workType;
	
	public String workTime;
	
	public Integer startSPR;
	
	public Integer endSPR;
	
	public AppDispInfoStartupDto appDispInfoStartupDto;
	
	public OvertimeAppSetCommand overtimeAppSet;
	
	
}
