package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationDisplayOrder;
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
	
	public AppListExtractCondition convertDtotoDomain(){
		return new AppListExtractCondition(
				Strings.isNotBlank(this.getPeriodStartDate()) ? GeneralDate.fromString(this.getPeriodStartDate(), "yyyy/MM/dd") : null, 
				Strings.isNotBlank(this.getPeriodEndDate()) ? GeneralDate.fromString(this.getPeriodEndDate(), "yyyy/MM/dd") : null, 
				this.isPostOutput(), 
				this.isPreOutput(), 
				EnumAdaptor.valueOf(this.appListAtr, ApplicationListAtr.class), 
				EnumAdaptor.valueOf(this.appDisplayOrder, ApplicationDisplayOrder.class), 
				this.isTableWidthRegis(), 
				CollectionUtil.isEmpty(this.opListEmployeeID) ? Optional.empty() : Optional.of(this.getOpListEmployeeID()), 
				this.getOpRemandStatus() == null ? Optional.empty() : Optional.of(this.getOpRemandStatus()), 
				this.getOpCancelStatus() == null ? Optional.empty() : Optional.of(this.getOpCancelStatus()), 
				this.getOpApprovalStatus() == null ? Optional.empty() : Optional.of(this.getOpApprovalStatus()), 
				this.getOpAgentApprovalStatus() == null ? Optional.empty() : Optional.of(this.getOpAgentApprovalStatus()), 
				this.getOpDenialStatus() == null ? Optional.empty() : Optional.of(this.getOpDenialStatus()), 
				this.getOpUnapprovalStatus() == null ? Optional.empty() : Optional.of(this.getOpUnapprovalStatus()), 
				CollectionUtil.isEmpty(this.getOpAppTypeLst()) 
					? Optional.empty()
					: Optional.of(this.getOpAppTypeLst().stream().map(x -> x.toDomain()).collect(Collectors.toList())), 
				CollectionUtil.isEmpty(this.getOpListOfAppTypes()) 
					? Optional.empty() 
					: Optional.of(this.getOpListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList())));
	}
	
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
