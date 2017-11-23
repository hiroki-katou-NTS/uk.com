package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

public interface OvertimeSixProcess {
	
	/**
	 * 06-01_色表示チェック
	 * @param overTimeInputs
	 * @param overtimeInputCaculations
	 */
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting);
	/**
	 * 06-02-1_事前申請を取得
	 * @return
	 */
	public AppOverTime getAppOvertimePre(String companyID,String employeeId, String appDate,int appType);
	/**
	 * 06-04_計算実績超過チェック
	 */
	public void checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting);
	
	/**
	 * 06-04-1_チェック条件
	 * @param prePostAtr
	 * @return
	 */
	public boolean checkCondition(int prePostAtr, int appType,String companyID);
}
