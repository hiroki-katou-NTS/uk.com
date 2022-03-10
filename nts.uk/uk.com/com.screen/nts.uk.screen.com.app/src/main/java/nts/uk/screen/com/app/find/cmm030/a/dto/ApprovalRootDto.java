package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalRoot;

@Data
@AllArgsConstructor
public class ApprovalRootDto {

	/** システム区分 */
	private Integer sysAtr;
	/** 承認ルート区分 */
	private Integer employmentRootAtr;
	/** 履歴 */
	private List<EmploymentAppHistoryItemDto> historyItems;
	/** 申請種類 */
	private Integer applicationType;
	/** 確認ルート種類 */
	private Integer confirmRootType;
	/** 届出ID */
	private Integer noticeId;
	/** 各業務エベントID */
	private String busEventId;

	public static ApprovalRootDto fromDomain(ApprovalRoot domain) {
		return new ApprovalRootDto(domain.getSysAtr().value, domain.getEmploymentRootAtr().value,
				domain.getHistoryItems().stream().map(EmploymentAppHistoryItemDto::fromDomain)
						.collect(Collectors.toList()),
				domain.getApplicationType().map(x -> x.value).orElse(null),
				domain.getConfirmationRootType().map(x -> x.value).orElse(null),
				domain.getNoticeId().orElse(null), domain.getBusEventId().orElse(null));
	}
}
