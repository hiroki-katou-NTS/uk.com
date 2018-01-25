package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;

public interface OvertimeFourProcess {
	//04-02_色表示チェック
	public List<CaculationTime> checkDisplayColor(List<CaculationTime> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID);
	
	//04-01_計算実績超過チェック(一覧)
	public CaculationTime checkCalculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,GeneralDate appDate,CaculationTime overTimeInput);
}
