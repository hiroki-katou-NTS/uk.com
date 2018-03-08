package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.shared.dom.WorkInformation;

public class PlanActualWorkTypeTest {

	@Test
	public void checkWorkTypeTest() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "2"));
		target.setWorkTypePlan(true, Arrays.asList("1", "2"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info);
		assertThat(actual, is(true));

	}

	private WorkInfoOfDailyPerformance createWorkInfo() {
		return new WorkInfoOfDailyPerformance("1", new WorkInformation("1", "1"), new WorkInformation("1", "1"),
				CalculationState.No_Calculated, NotUseAttribute.Not_use,
				NotUseAttribute.Not_use, GeneralDate.today(), new ArrayList<>());
	}
}
