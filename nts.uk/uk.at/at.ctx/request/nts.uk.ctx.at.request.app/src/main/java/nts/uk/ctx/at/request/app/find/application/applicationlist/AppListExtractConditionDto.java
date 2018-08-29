package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationDisplayAtr;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppListExtractConditionDto {

	/**期間開始日付*/
	@Setter
	private String startDate;
	/**期間終了日付*/
	@Setter
	private String endDate;
	/**申請一覧区分*/
	/**0: 申請*/
	/**1: 承認*/
	private Integer appListAtr;
	/**申請種類*/
	private Integer appType;
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
		String dateFormat = "yyyy/MM/dd";
		return new AppListExtractCondition(
				GeneralDate.fromString(dto.getStartDate(), dateFormat),
				GeneralDate.fromString(dto.getEndDate(), dateFormat),
				EnumAdaptor.valueOf(dto.getAppListAtr(),ApplicationListAtr.class),
				dto.getAppType() == -1 ? null : EnumAdaptor.valueOf(dto.getAppType(), ApplicationType.class),
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
