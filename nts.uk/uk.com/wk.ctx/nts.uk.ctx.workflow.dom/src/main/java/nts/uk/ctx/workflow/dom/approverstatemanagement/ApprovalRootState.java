package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 承認ルートインスタンス
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApprovalRootState extends AggregateRoot {
	
	private String rootStateID;
	
	private RootType rootType;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	@Setter
	private List<ApprovalPhaseState> listApprovalPhaseState;
	
	public static ApprovalRootState createFromFirst(String companyID, String appID, RootType rootType,
			GeneralDate date, String employeeID, ApprovalRootState approvalRootState){
		if(Strings.isBlank(approvalRootState.getRootStateID())){
			return ApprovalRootState.builder()
					.rootStateID(appID)
					.rootType(rootType)
					.approvalRecordDate(date)
					.employeeID(employeeID)
					.listApprovalPhaseState(approvalRootState.getListApprovalPhaseState().stream()
							.map(x -> ApprovalPhaseState.createFromFirst(date, x)).collect(Collectors.toList()))
					.build();
		}
		return approvalRootState;
	}
	
	public static ApprovalRootState createFromCache(String appID, GeneralDate date, String employeeID, List<ApprovalPhaseState> listApprovalPhaseState) {
		return new ApprovalRootState(
				appID, 
				RootType.EMPLOYMENT_APPLICATION, 
				date, 
				employeeID, 
				listApprovalPhaseState);
	}
}
