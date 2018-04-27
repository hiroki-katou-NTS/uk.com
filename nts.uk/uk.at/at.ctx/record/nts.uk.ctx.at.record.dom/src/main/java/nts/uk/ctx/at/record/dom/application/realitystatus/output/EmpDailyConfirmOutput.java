package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class EmpDailyConfirmOutput {
	List<DailyConfirmOutput> listDailyConfirm;
	SumCountOutput sumCount;
}
