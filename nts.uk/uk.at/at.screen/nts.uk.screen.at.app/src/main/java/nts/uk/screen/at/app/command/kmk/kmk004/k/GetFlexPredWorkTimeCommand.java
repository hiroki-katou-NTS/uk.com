package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.ReferencePredTimeOfFlex;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFlexPredWorkTimeCommand {

	/** 参照先 */
	private int reference;

	public GetFlexPredWorkTime toDomain() {
		return GetFlexPredWorkTime.of(AppContexts.user().companyId(),
				EnumAdaptor.valueOf(reference, ReferencePredTimeOfFlex.class));
	}

}
