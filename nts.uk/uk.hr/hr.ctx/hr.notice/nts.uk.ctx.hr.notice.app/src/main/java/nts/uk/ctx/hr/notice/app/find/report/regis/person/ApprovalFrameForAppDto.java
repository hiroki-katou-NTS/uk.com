package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalBehaviorAtrHrExport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.FrameHumanImport;

@Value
@AllArgsConstructor
public class ApprovalFrameForAppDto {

	private Integer frameOrder;

	private List<ApproverStateForAppDto> listApprover;

	public static ApprovalFrameForAppDto fromApprovalFrameImport(FrameHumanImport approvalFrameImport,
			
			Map<String, List<EmployeeInformationImport>> employeeInfoMaps) {
		
		return new ApprovalFrameForAppDto(approvalFrameImport.getFrameOrder(),
				
				approvalFrameImport.getLstApproverInfo().stream().map(x -> {

					ApprovalBehaviorAtrHrExport status = EnumAdaptor.valueOf(x.getApprovalAtr(), ApprovalBehaviorAtrHrExport.class);
					
					ApproverStateForAppDto  appDto = new ApproverStateForAppDto(
							
							StringUtil.isNullOrEmpty(x.getAgentID(), true) == true ? x.getApproverID() : x.getAgentID(),
									
							Integer.valueOf(x.getApprovalAtr()), status.nameId, "approverName",
							
							x.getApprovalDate() == null ? null : x.getApprovalDate().toString("yyyy/MM/dd"),
									
							x.getApprovalReason(), "");
					
					List<EmployeeInformationImport> employeeLst = employeeInfoMaps.get(appDto.getApproverID());
					
					if(!CollectionUtil.isEmpty(employeeLst)) {
						
						appDto.setApproverName(employeeLst.get(0).getBusinessName());
						
					}
					
					
					return appDto;

				}).collect(Collectors.toList()));
	}

}
