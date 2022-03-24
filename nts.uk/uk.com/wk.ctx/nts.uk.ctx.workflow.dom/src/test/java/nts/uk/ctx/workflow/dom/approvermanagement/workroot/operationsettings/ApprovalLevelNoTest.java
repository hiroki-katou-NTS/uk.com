package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ApprovalLevelNoTest {

	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 1: 承認レベル数 = 1つレベル
	 */
	@Test
	public void testDetermineOrderOfPhases1() {
		ApprovalLevelNo data = ApprovalLevelNo.ONE_LEVEL;
		List<Integer> expected = Arrays.asList(5);
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 2: 承認レベル数 = 2つレベル
	 */
	@Test
	public void testDetermineOrderOfPhases2() {
		ApprovalLevelNo data = ApprovalLevelNo.TWO_LEVEL;
		List<Integer> expected = Arrays.asList(5, 4);
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 3: 承認レベル数 = 3つレベル
	 */
	@Test
	public void testDetermineOrderOfPhases3() {
		ApprovalLevelNo data = ApprovalLevelNo.THREE_LEVEL;
		List<Integer> expected = Arrays.asList(5, 4, 3);
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 4: 承認レベル数 = 4つレベル
	 */
	@Test
	public void testDetermineOrderOfPhases4() {
		ApprovalLevelNo data = ApprovalLevelNo.FOUR_LEVEL;
		List<Integer> expected = Arrays.asList(5, 4, 3, 2);
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 5: 承認レベル数 = 5つレベル
	 */
	@Test
	public void testDetermineOrderOfPhases5() {
		ApprovalLevelNo data = ApprovalLevelNo.FIVE_LEVEL;
		List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	/**
	 * Test [1] 表示される承認フェーズの順序を判断する	
	 * Case 6: 承認レベル数 = other value
	 */
	@Test
	public void testDetermineOrderOfPhases6() {
		ApprovalLevelNo data = ApprovalLevelNo.ONE_LEVEL;
		data.value = 10;
		List<Integer> expected = new ArrayList<>(); 
		
		List<Integer> actual = data.determineOrderOfPhases();
		
		assertThat(actual).isEqualTo(expected);
	}
}
