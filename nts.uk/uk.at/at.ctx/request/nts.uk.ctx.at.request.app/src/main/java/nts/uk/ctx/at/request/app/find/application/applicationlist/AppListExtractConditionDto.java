package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
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
	private List<Integer> opAppTypeLst;
	
	/**
	 * 申請種類リスト
	 */
	private List<ListOfAppTypesDto> opListOfAppTypes;
	
	public AppListExtractCondition convertDtotoDomain(AppListExtractConditionDto dto){
		return new AppListExtractCondition(
				GeneralDate.fromString(dto.getPeriodStartDate(), "yyyy/MM/dd"), 
				GeneralDate.fromString(dto.getPeriodEndDate(), "yyyy/MM/dd"), 
				dto.isPostOutput(), 
				dto.isPreOutput(), 
				EnumAdaptor.valueOf(dto.appListAtr, ApplicationListAtr.class), 
				EnumAdaptor.valueOf(dto.appDisplayOrder, ApplicationDisplayOrder.class), 
				dto.isTableWidthRegis(), 
				CollectionUtil.isEmpty(dto.opListEmployeeID) ? Optional.empty() : Optional.of(dto.getOpListEmployeeID()), 
				dto.getOpRemandStatus() == null ? Optional.empty() : Optional.of(dto.getOpRemandStatus()), 
				dto.getOpCancelStatus() == null ? Optional.empty() : Optional.of(dto.getOpCancelStatus()), 
				dto.getOpApprovalStatus() == null ? Optional.empty() : Optional.of(dto.getOpApprovalStatus()), 
				dto.getOpAgentApprovalStatus() == null ? Optional.empty() : Optional.of(dto.getOpAgentApprovalStatus()), 
				dto.getOpDenialStatus() == null ? Optional.empty() : Optional.of(dto.getOpDenialStatus()), 
				dto.getOpUnapprovalStatus() == null ? Optional.empty() : Optional.of(dto.getOpUnapprovalStatus()), 
				CollectionUtil.isEmpty(dto.getOpAppTypeLst()) 
					? Optional.empty()
					: Optional.of(dto.getOpAppTypeLst().stream().map(x -> EnumAdaptor.valueOf(x, ApplicationType.class)).collect(Collectors.toList())), 
				CollectionUtil.isEmpty(dto.getOpListOfAppTypes()) 
					? Optional.empty() 
					: Optional.of(dto.getOpListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList())));
	}
}
