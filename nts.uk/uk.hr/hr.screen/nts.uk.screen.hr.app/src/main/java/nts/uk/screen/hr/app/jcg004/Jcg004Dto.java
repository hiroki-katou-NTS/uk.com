package nts.uk.screen.hr.app.jcg004;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Jcg004Dto {

	private Boolean approvalOfApplication;
	
	private List<JCG004BusinessApprovalDto> businessApproval;
}
