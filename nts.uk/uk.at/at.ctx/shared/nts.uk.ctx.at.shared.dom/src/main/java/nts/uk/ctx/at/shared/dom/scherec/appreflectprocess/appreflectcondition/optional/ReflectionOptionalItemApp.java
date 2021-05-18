package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.optional;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.optional.OptionalItemApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;

/**
 * @author thanh_nx
 *
 *
 *         任意項目申請の反映
 */
@AllArgsConstructor
public class ReflectionOptionalItemApp extends AggregateRoot {

	// 任意項目申請の反映
	public DailyRecordOfApplication reflect(OptionalItemApplicationShare optionalApp,
			DailyRecordOfApplication dailyApp) {

		// 任意項目の反映
		return ReflectOptional.reflect(optionalApp.getOptionalItems(), dailyApp);
	}

}
