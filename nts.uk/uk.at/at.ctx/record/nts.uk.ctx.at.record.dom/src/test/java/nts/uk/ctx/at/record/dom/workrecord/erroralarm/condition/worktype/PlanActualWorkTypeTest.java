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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.shared.dom.WorkInformation;

public class PlanActualWorkTypeTest {

	@Test
	public void checkWorkTypeTest_True() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "2"));
		target.setWorkTypePlan(true, Arrays.asList("1", "2"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(true));

	}
	
	@Test
	public void checkWorkTypeTest_False() {
		val target = PlanActualWorkType.init(false, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "2"));
		target.setWorkTypePlan(true, Arrays.asList("1", "2"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}
	
	@Test
	public void checkWorkTypeTest_False1() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "2"));
		target.setWorkTypePlan(true, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}
	
	@Test
	public void checkWorkTypeTest_False2() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("2", "3"));
		target.setWorkTypePlan(false, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}
	
	@Test
	public void checkWorkTypeTest_False3() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("2", "3"));
		target.setWorkTypePlan(true, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}

	@Test
	public void checkWorkTypeTest_False4() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("2", "3"));
		target.setWorkTypePlan(true, Arrays.asList("1", "3"));
		target.chooseOperator(LogicalOperator.AND.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}
	
	@Test
	public void checkWorkTypeTest_False5() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("2", "2"));
		target.setWorkTypePlan(true, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.OR.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(false));

	}
	
	@Test
	public void checkWorkTypeTest_True1() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "3"));
		target.setWorkTypePlan(false, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.OR.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(true));

	}
	
	@Test
	public void checkWorkTypeTest_True2() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("1", "3"));
		target.setWorkTypePlan(true, Arrays.asList("2", "3"));
		target.chooseOperator(LogicalOperator.OR.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(true));

	}

	@Test
	public void checkWorkTypeTest_True3() {
		val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
		target.setworkTypeActual(true, Arrays.asList("2", "3"));
		target.setWorkTypePlan(true, Arrays.asList("1", "3"));
		target.chooseOperator(LogicalOperator.OR.value);
		WorkInfoOfDailyPerformance info = createWorkInfo();

		boolean actual = target.checkWorkType(info) == WorkCheckResult.ERROR;
		assertThat(actual, is(true));

	}
	
	private WorkInfoOfDailyPerformance createWorkInfo() {
		return new WorkInfoOfDailyPerformance("1", new WorkInformation("1", "1"), new WorkInformation("1", "1"),
				CalculationState.No_Calculated, NotUseAttribute.Not_use,
				NotUseAttribute.Not_use, GeneralDate.today(), new ArrayList<>());
	}
}
