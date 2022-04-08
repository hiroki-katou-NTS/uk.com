package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.i18n.I18NText;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalConfirmCache implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String sId;
	
	private List<String> employeeIds;
	
//	private DatePeriod period;
	private DateRange period;
	
	private Integer mode;
	
	private List<ConfirmStatusActualResultKDW003Dto> lstConfirm;
	
	private List<ApprovalStatusActualResultKDW003Dto> lstApproval;
	
	public void checkVer(ApprovalConfirmCache cacheNew) {
		
		List<ConfirmStatusActualResultKDW003Dto> lstConfirmNew = cacheNew.getLstConfirm();
		if (!lstConfirmNew.equals(lstConfirm)) {
			throw new OptimisticLockException(I18NText.getText("Msg_1528"));
		}

		List<ApprovalStatusActualResultKDW003Dto> lstApprovalNew = cacheNew.getLstApproval()
				.stream().sorted((x,y) -> x.getConfirmStatusActualResult().getDate().compareTo(y.getConfirmStatusActualResult().getDate())).collect(Collectors.toList());
		
		List<ApprovalStatusActualResultKDW003Dto> lstApprovalSort = lstApproval
				.stream().sorted((x,y) -> x.getConfirmStatusActualResult().getDate().compareTo(y.getConfirmStatusActualResult().getDate())).collect(Collectors.toList());
		
		if (!lstApprovalNew.equals(lstApprovalSort)) {
			throw new OptimisticLockException(I18NText.getText("Msg_1528"));
		}
		
		
	}
}
