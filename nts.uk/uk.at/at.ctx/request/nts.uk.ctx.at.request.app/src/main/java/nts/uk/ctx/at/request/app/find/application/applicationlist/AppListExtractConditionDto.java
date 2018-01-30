package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppTypeList;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationDisplayAtr;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppListExtractCondition;
@Getter
public class AppListExtractConditionDto {

	/**期間開始日付*/
	private String startDate;
	/**期間終了日付*/
	private String emdDate;
	/**申請一覧区分*/
	private int appListAtr;
	/**申請種類*/
	private int appType;
	/**承認状況＿未承認*/
	private boolean unapprovalStatus;
	/**承認状況＿承認済*/
	private boolean approvalStatus;
	/**承認状況＿否認*/
	private boolean denialStatus;
	/**承認状況＿代行承認済*/
	private boolean agentApprovalStatus;
	/**承認状況＿差戻*/
	private boolean remandStatus;
	/**承認状況＿取消*/
	private boolean cancelStatus;
	/**申請表示対象*/
	private  int appDisplayAtr;
	/**社員IDリスト*/
	private List<String> listEmployeeId;
	/**社員絞込条件*/
	private String empRefineCondition;
	
	public AppListExtractCondition convertDtotoDomain(AppListExtractConditionDto dto){
		String dateFormat = "yyyy-MM-dd";
		return new AppListExtractCondition(
				GeneralDate.fromString(dto.getStartDate(), dateFormat),
				GeneralDate.fromString(dto.getEmdDate(), dateFormat),
				EnumAdaptor.valueOf(dto.getAppListAtr(),ApplicationListAtr.class),
				EnumAdaptor.valueOf(dto.getAppType(), AppTypeList.class),
				dto.isUnapprovalStatus(),
				dto.isApprovalStatus(),
				dto.isDenialStatus(),
				dto.isAgentApprovalStatus(),
				dto.isRemandStatus(),
				dto.isCancelStatus(),
				EnumAdaptor.valueOf(dto.getAppDisplayAtr(), ApplicationDisplayAtr.class),
				dto.getListEmployeeId(),
				dto.getEmpRefineCondition());
	}
}
