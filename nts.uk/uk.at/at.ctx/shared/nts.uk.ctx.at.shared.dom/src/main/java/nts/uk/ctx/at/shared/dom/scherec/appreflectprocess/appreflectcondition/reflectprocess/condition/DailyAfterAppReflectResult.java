package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;

/**
 * @author thanh_nx
 *
 *         申請反映後の日別勤怠
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyAfterAppReflectResult {

	private DailyRecordOfApplication domainDaily;

	private List<Integer> lstItemId;

}
