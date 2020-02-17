package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.FrameHumanImport;

@Value
@AllArgsConstructor
public class ApprovalFrameForAppDto {

	private Integer frameOrder;

	private List<ApproverStateForAppDto> listApprover;

	public static ApprovalFrameForAppDto fromApprovalFrameImport(FrameHumanImport approvalFrameImport) {
		return new ApprovalFrameForAppDto(approvalFrameImport.getFrameOrder(), approvalFrameImport.getLstApproverInfo()
				.stream()
				.map(x -> new ApproverStateForAppDto(x.getApproverID(), Integer.valueOf(x.getApprovalAtr()), "approvalAtrName",
						"approverName", x.getApprovalDate() == null ? null : x.getApprovalDate().toString("yyyy/MM/dd"),
						x.getApprovalReason(), ""))
				.collect(Collectors.toList()));
	}

}
