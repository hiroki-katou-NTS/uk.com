package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * スケジュール修正の機能制御 のテスト
 * @author hiroko_miura
 *
 */
public class ScheFunctionControlTest {

	/**
	 * case : changeableWorks.size > 3
	 * Expected : SystemError
	 */
	@Test
	public void test_create_oversize() {
		
		List<WorkTimeForm> lstWork = Stream.of(WorkTimeForm.values()).collect(Collectors.toList());
		
		NtsAssert.systemError(() -> {
			ScheFunctionControl.create(lstWork, true, NotUseAtr.NOT_USE, new ArrayList<>() );
		});
	}
	
	/**
	 * case : changeableWorksが重複している
	 * Expected : SystemError
	 */
	@Test
	public void test_create_overlap() {
		List<WorkTimeForm> lstWork = new ArrayList<>();
		lstWork.add(WorkTimeForm.FIXED);
		lstWork.add(WorkTimeForm.FIXED);
		
		NtsAssert.systemError(() -> {
			ScheFunctionControl.create(lstWork, true, NotUseAtr.NOT_USE, new ArrayList<>());
		});
	}
	
	/**
	 * case : displayWorkTypeControl = Use, displayableWorkTypeCodeList is empty
	 * Expected : BusinessException Msg_1690
	 */
	@Test
	public void test_create_1690() {
		
		List<WorkTimeForm> lstWork = new ArrayList<>();
		lstWork.add(WorkTimeForm.FIXED);
		
		NtsAssert.businessException("Msg_1690",() -> {
			ScheFunctionControl.create(lstWork, true, NotUseAtr.USE, new ArrayList<>());
		});
	}
	
	/**
	 * case : changeableWorks = empty
	 * Expected : エラーではない
	 */
	@Test
	public void test_create_lstempty() {
		List<WorkTimeForm> lstWork = new ArrayList<>();
		
		val actuals = ScheFunctionControl.create(lstWork, false, NotUseAtr.NOT_USE, new ArrayList<>());
		
		assertThat(actuals.getChangeableWorks()).isEmpty();
		assertThat(actuals.isDisplayActual()).isFalse();
	}

	/**
	 * case : changeableWorks.size = 3
	 * Expected : エラーではない
	 */
	@Test
	public void test_create_success() {
		List<WorkTimeForm> lstWork = new ArrayList<>();
		lstWork.add(WorkTimeForm.FIXED);
		lstWork.add(WorkTimeForm.FLEX);
		lstWork.add(WorkTimeForm.FLOW);
		
		val actuals = ScheFunctionControl.create(
				lstWork, 
				true,
				NotUseAtr.USE,
				Arrays.asList( 
					new WorkTypeCode("001"),
					new WorkTypeCode("002"),
					new WorkTypeCode("003")) );
		
		assertThat(actuals.getChangeableWorks()).containsOnly(WorkTimeForm.FIXED, WorkTimeForm.FLEX, WorkTimeForm.FLOW);
		assertThat(actuals.isDisplayActual()).isTrue();
		assertThat(actuals.getDisplayWorkTypeControl()).isEqualTo( NotUseAtr.USE );
		assertThat(actuals.getDisplayableWorkTypeCodeList())
			.extracting( d -> d.v())
			.containsExactly("001", "002", "003");
	}
	
	/**
	 * isChangeableForm
	 */
	public void changeable_test () {
		List<WorkTimeForm> lstWork = new ArrayList<>();
		lstWork.add(WorkTimeForm.FLEX);
		lstWork.add(WorkTimeForm.FLOW);
		
		val actuals = ScheFunctionControl.create(lstWork, true, NotUseAtr.NOT_USE, new ArrayList<>());
		
		assertThat(actuals.isChangeableForm(WorkTimeForm.FIXED)).isFalse();
		assertThat(actuals.isChangeableForm(WorkTimeForm.FLEX)).isTrue();
		assertThat(actuals.isChangeableForm(WorkTimeForm.FLOW)).isTrue();
	}
}
