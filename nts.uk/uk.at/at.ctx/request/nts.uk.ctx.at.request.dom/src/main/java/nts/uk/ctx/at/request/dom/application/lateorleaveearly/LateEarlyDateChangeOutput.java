package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
public class LateEarlyDateChangeOutput {
	
	private AppDispInfoWithDateOutput appDispInfoWithDateOutput;
	
	private List<LateOrEarlyInfo> lateOrEarlyInfoLst;
	
	private Optional<String> errorInfo;
	
	public LateEarlyDateChangeOutput(AppDispInfoWithDateOutput appDispInfoWithDateOutput, List<LateOrEarlyInfo> lateOrEarlyInfoLst) {
		this.appDispInfoWithDateOutput = appDispInfoWithDateOutput;
		this.lateOrEarlyInfoLst = lateOrEarlyInfoLst;
		this.errorInfo = Optional.empty();
	}
}
