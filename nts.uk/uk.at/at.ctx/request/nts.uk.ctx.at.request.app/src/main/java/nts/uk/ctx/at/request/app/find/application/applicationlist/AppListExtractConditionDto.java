package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppListExtractConditionDto {
	
	/**
	 * 期間開始日
	 */
	private String periodStartDate;
	
	/**
	 * 期間終了日
	 */
	private String periodEndDate;
	/**
	 * 事後出力
	 */
	private boolean postOutput;
	
	/**
	 * 事前出力
	 */
	private boolean preOutput;
	
	/**
	 * 申請一覧区分
	 */
	private int appListAtr;
	
	/**
	 * 申請表示順
	 */
	private int appDisplayOrder;
	
	/**
	 * 表の幅登録
	 */
	private boolean tableWidthRegis;
	
	/**
	 * 社員IDリスト
	 */
	private List<String> opListEmployeeID;
	
	/**
	 * 承認状況＿差戻
	 */
	private Boolean opRemandStatus;
	
	/**
	 * 承認状況＿取消
	 */
	private Boolean opCancelStatus;
	
	/**
	 * 承認状況＿承認済
	 */
	private Boolean opApprovalStatus;
	
	/**
	 * 承認状況＿代行承認済
	 */
	private Boolean opAgentApprovalStatus;
	
	/**
	 * 承認状況＿否認
	 */
	private Boolean opDenialStatus;
	
	/**
	 * 承認状況＿未承認
	 */
	private Boolean opUnapprovalStatus;
	
	/**
	 * 申請種類
	 */
	private List<ListOfAppTypesDto> opAppTypeLst;
	
	/**
	 * 申請種類リスト
	 */
	private List<ListOfAppTypesDto> opListOfAppTypes;
	
	public static AppListExtractConditionDto fromDomain(AppListExtractCondition appListExtractCondition) {
		return new AppListExtractConditionDto(
				appListExtractCondition.getPeriodStartDate().toString(), 
				appListExtractCondition.getPeriodEndDate().toString(), 
				appListExtractCondition.isPostOutput(), 
				appListExtractCondition.isPreOutput(), 
				appListExtractCondition.getAppListAtr().value, 
				appListExtractCondition.getAppDisplayOrder().value, 
				appListExtractCondition.isTableWidthRegis(), 
				appListExtractCondition.getOpListEmployeeID().orElse(null), 
				appListExtractCondition.getOpRemandStatus().orElse(null), 
				appListExtractCondition.getOpCancelStatus().orElse(null), 
				appListExtractCondition.getOpApprovalStatus().orElse(null), 
				appListExtractCondition.getOpAgentApprovalStatus().orElse(null), 
				appListExtractCondition.getOpDenialStatus().orElse(null), 
				appListExtractCondition.getOpUnapprovalStatus().orElse(null), 
				appListExtractCondition.getOpAppTypeLst().map(x -> x.stream().map(y -> ListOfAppTypesDto.fromDomain(y)).collect(Collectors.toList())).orElse(null), 
				appListExtractCondition.getOpListOfAppTypes().map(x -> x.stream().map(y -> ListOfAppTypesDto.fromDomain(y)).collect(Collectors.toList())).orElse(null));
	}
}
