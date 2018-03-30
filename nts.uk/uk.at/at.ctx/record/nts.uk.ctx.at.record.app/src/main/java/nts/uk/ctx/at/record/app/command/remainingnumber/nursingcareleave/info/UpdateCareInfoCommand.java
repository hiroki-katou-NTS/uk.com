package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info;

import lombok.Data;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;

@Data
public class UpdateCareInfoCommand {
	
	//社員ID
	private String sId;
	
	private LeaveType leaveType;
	
	//使用区分
	private boolean useClassification;
	
	//上限設定
	private int upperlimitSetting;	
	
	//本年度上限日数
	private Integer maxDayForThisFiscalYear;
	
	//次年度上限日数
	private Integer maxDayForNextFiscalYear;
}
