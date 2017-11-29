package nts.uk.ctx.bs.employee.app.find.department.affiliate;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
class AffDeptHistDto extends PeregDomainDto{
	
	/** The history Id. */
	// 履歴ID
	private String historyId;
	
	/** The department code. */
	/* 部門コード */
	private String departmentCode;

	/** The Affiliation History Transfer type. */
	// 所属履歴異動種類
	private String affHistoryTranfsType;

	/** The Employee Id. */
	// 社員ID
	private String distributionRatio;
	
	//期間
	private String period;
	
	//発令日
	private GeneralDate startDate;
	
	//終了日
	private GeneralDate endDate;
	
	private AffDeptHistDto(String recordId, String employeeId) {
		super(recordId, employeeId, null);
	}
	
	public AffDeptHistDto getFirstFromDomain(AffDepartmentHistory affDeptHist, AffDepartmentHistoryItem affDeptHistItem){
		return getBaseOnDateHist(affDeptHistItem, affDeptHist.getHistoryItems().get(0).start(), affDeptHist.getHistoryItems().get(0).end());
	}
	
	public List<AffDeptHistDto> getListFromDomain(AffDepartmentHistory affDeptHist, AffDepartmentHistoryItem affDeptHistItem){
		return affDeptHist.getHistoryItems().stream().map(item -> getBaseOnDateHist(affDeptHistItem, item.start(), item.end())).collect(Collectors.toList());
	}
	
	private AffDeptHistDto getBaseOnDateHist( AffDepartmentHistoryItem affDeptHistItem, GeneralDate startDate, GeneralDate endDate){
		AffDeptHistDto dto = new AffDeptHistDto(affDeptHistItem.getHistoryId(), affDeptHistItem.getEmployeeId());
		dto.setHistoryId(affDeptHistItem.getHistoryId());
		dto.setDepartmentCode(affDeptHistItem.getDepartmentCode().v());
		dto.setAffHistoryTranfsType(affDeptHistItem.getAffHistoryTranfsType());
		dto.setDistributionRatio(affDeptHistItem.getDistributionRatio().v());
		dto.setStartDate(startDate);
		return dto;
	}
}
