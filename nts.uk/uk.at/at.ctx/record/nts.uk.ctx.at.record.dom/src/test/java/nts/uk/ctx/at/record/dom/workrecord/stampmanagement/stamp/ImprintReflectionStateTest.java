/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 * 打刻反映状態-UnitTest
 *
 */
public class ImprintReflectionStateTest {
	
	@Test
	public void getters() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true, Optional.of(GeneralDate.today()));
		NtsAssert.invokeGetters(imprintReflecState);
	}
	
	// test constructor
	@Test
	public void testImprintReflectionState() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true, Optional.of(GeneralDate.today()));
		assertThat(imprintReflecState.isReflectedCategory()).isTrue();
		NtsAssert.invokeGetters(imprintReflecState);
	}
	
	// test function [1] 対象日に反映できるか
	// case1: 反映済み区分 (reflectedCategory) = false
	@Test
	public void testFuncCanReflectedOnTargetDate_Case1() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(false, Optional.empty());
		GeneralDate baseDate = GeneralDate.today();
		assertThat(imprintReflecState.canReflectedOnTargetDate(baseDate)).isTrue();
	}
	
	// test function [1] 対象日に反映できるか
	// case2: 反映済み区分 (reflectedCategory) = true
	//        反映された年月日(reflectedDate) = Optional.empty()
	@Test
	public void testFuncCanReflectedOnTargetDate_Case2() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true, Optional.empty());
		GeneralDate baseDate = GeneralDate.today();
		assertThat(imprintReflecState.canReflectedOnTargetDate(baseDate)).isFalse();
	}
	
	// test function [1] 対象日に反映できるか
	// case3: 反映済み区分 (reflectedCategory) = true
	//        反映された年月日(reflectedDate) = GeneralDate.ymd(2021, 6, 6)
	//        baseDate = GeneralDate.ymd(2021, 8, 8)
	@Test
	public void testFuncCanReflectedOnTargetDate_Case3() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true, Optional.of(GeneralDate.ymd(2021, 6, 6)));
		GeneralDate baseDate = GeneralDate.ymd(2021, 8, 8);
		assertThat(imprintReflecState.canReflectedOnTargetDate(baseDate)).isFalse();
	}
	
	// test function [1] 対象日に反映できるか
	// case4: 反映済み区分 (reflectedCategory) = true
	//        反映された年月日(reflectedDate) = GeneralDate.ymd(2021, 6, 6)
	//        baseDate = GeneralDate.ymd(2021, 6, 6)
	@Test
	public void testFuncCanReflectedOnTargetDate_Case4() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true, Optional.of(GeneralDate.ymd(2021, 6, 6)));
		GeneralDate baseDate = GeneralDate.ymd(2021, 6, 6);
		assertThat(imprintReflecState.canReflectedOnTargetDate(baseDate)).isTrue();
	}
	
	// test function [2] 反映された年月日を更新する
	// case1: 反映済み区分 (reflectedCategory) = false
	//        反映された年月日(reflectedDate) = GeneralDate.ymd(2021, 6, 6)
	//        baseDate = GeneralDate.ymd(2021, 8, 8)
	@Test
	public void testFuncUpdateReflectedDate_Case1() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(false, Optional.of(GeneralDate.ymd(2021, 6, 6)));
		GeneralDate baseDate = GeneralDate.ymd(2021, 6, 6);
		imprintReflecState.updateReflectedDate(baseDate);
		assertThat(imprintReflecState.getReflectedDate().get()).isEqualTo(GeneralDate.ymd(2021, 6, 6));
	}

	// test function [2] 反映された年月日を更新する
	// case2: 反映済み区分 (reflectedCategory) = true
	// 反映された年月日(reflectedDate) = GeneralDate.ymd(2021, 6, 6)
	// baseDate = GeneralDate.ymd(2021, 8, 8)
	@Test
	public void testFuncUpdateReflectedDate_Case2() {
		ImprintReflectionState imprintReflecState = new ImprintReflectionState(true,Optional.of(GeneralDate.ymd(2021, 6, 6)));
		GeneralDate baseDate = GeneralDate.ymd(2021, 6, 6);
		imprintReflecState.updateReflectedDate(baseDate);
		assertThat(imprintReflecState.getReflectedDate().get()).isEqualTo(baseDate);
	}

}
