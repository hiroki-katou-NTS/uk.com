package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.updateifnotmanaged;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
	
public class UpdateIfNotManagedOutput {
	private boolean checkUpdated ;
	
	private List<ErrorMessageInfo> listError = new ArrayList<>();
}
