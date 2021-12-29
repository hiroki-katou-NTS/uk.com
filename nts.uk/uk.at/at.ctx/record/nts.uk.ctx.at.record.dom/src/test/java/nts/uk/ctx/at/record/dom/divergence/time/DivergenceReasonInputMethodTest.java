package nts.uk.ctx.at.record.dom.divergence.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

public class DivergenceReasonInputMethodTest {

	/**
	 * test [1] 乖離理由に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//乖離時間NO = 1
		DivergenceReasonInputMethod domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(438,439))).isTrue();
		//乖離時間NO = 2
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(2, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(443,444))).isTrue();
		//乖離時間NO = 3
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(3, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(448,449))).isTrue();
		//乖離時間NO = 4
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(4, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(453,454))).isTrue();
		//乖離時間NO = 5
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(5, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(458,459))).isTrue();
		//乖離時間NO = 6
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(6, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(801,802))).isTrue();
		//乖離時間NO = 7
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(7, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(806,807))).isTrue();
		//乖離時間NO = 8
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(8, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(811,812))).isTrue();
		//乖離時間NO = 9
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(9, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(816,817))).isTrue();
		//乖離時間NO = 10
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(10, true, true);
		listAttdId = domain.getDaiLyAttendanceIdByNo();
		assertThat(listAttdId.containsAll(Arrays.asList(821,822))).isTrue();
	}
	
	/**
	 * test [2] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		/**
		 * 乖離時間NO = 1
		 * [3] 乖離理由入力を利用するか(乖離時間の使用区分) == true
		 * [4] 利用できない日次の勤怠項目を取得する(乖離時間の使用区分) == true
		 */
		UseClassification useAtr = UseClassification.UseClass_Use;
		DivergenceReasonInputMethod domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);
		listAttdId = domain.getDaiLyAttendanceIdNotAvailable(useAtr);
		assertThat(listAttdId).isEmpty();
		
		/**
		 * 乖離時間NO = 1
		 * [3] 乖離理由入力を利用するか(乖離時間の使用区分) == false
		 * [4] 利用できない日次の勤怠項目を取得する(乖離時間の使用区分) == true
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, false, true);
		listAttdId = domain.getDaiLyAttendanceIdNotAvailable(useAtr);
		assertThat(listAttdId.containsAll(Arrays.asList(439))).isTrue();
		
		/**
		 * 乖離時間NO = 1
		 * [3] 乖離理由入力を利用するか(乖離時間の使用区分) == true
		 * [4] 利用できない日次の勤怠項目を取得する(乖離時間の使用区分) == false
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, false);
		listAttdId = domain.getDaiLyAttendanceIdNotAvailable(useAtr);
		assertThat(listAttdId.containsAll(Arrays.asList(438))).isTrue();
		
		/**
		 * 乖離時間NO = 1
		 * [3] 乖離理由入力を利用するか(乖離時間の使用区分) == false
		 * [4] 利用できない日次の勤怠項目を取得する(乖離時間の使用区分) == false
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, false, false);
		listAttdId = domain.getDaiLyAttendanceIdNotAvailable(useAtr);
		assertThat(listAttdId.containsAll(Arrays.asList(438,439))).isTrue();
	}
	
	/**
	 * test [3] 乖離理由入力を利用するか
	 */
	@Test
	public void testUseDivergenceReasonInput() {
		//乖離時間NO = 1
		DivergenceReasonInputMethod domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);// createByNoAndUseAtr(NO,乖離理由を入力する,	乖離理由を選択肢から選ぶ)
		/**
		 * @乖離時間の使用区分 == 使用しない
		 */
		UseClassification useAtr = UseClassification.UseClass_NotUse;
		boolean check = domain.useDivergenceReasonInput(useAtr);
		assertThat(check).isFalse();
		
		/**
		 * @乖離時間の使用区分 != 使用しない
		 * @乖離理由を入力する	 == true;
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);
		check = domain.useDivergenceReasonInput(useAtr);
		assertThat(check).isTrue();
		
		/**
		 * @乖離時間の使用区分 != 使用しない
		 * @乖離理由を入力する	 == false;
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, false, true);
		check = domain.useDivergenceReasonInput(useAtr);
		assertThat(check).isFalse();
	}
	
	/**
	 * test [3] 乖離理由入力を利用するか
	 */
	@Test
	public void testUseDivergenceReasonSelected() {
		//乖離時間NO = 1
		DivergenceReasonInputMethod domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);// createByNoAndUseAtr(NO,乖離理由を入力する,	乖離理由を選択肢から選ぶ)
		/**
		 * @乖離時間の使用区分 == 使用しない
		 */
		UseClassification useAtr = UseClassification.UseClass_NotUse;
		boolean check = domain.useDivergenceReasonSelected(useAtr);
		assertThat(check).isFalse();
		
		/**
		 * @乖離時間の使用区分 != 使用しない
		 * @乖離理由を選択肢から選ぶ == true;
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, true);
		check = domain.useDivergenceReasonSelected(useAtr);
		assertThat(check).isTrue();
		
		/**
		 * @乖離時間の使用区分 != 使用しない
		 * @乖離理由を選択肢から選ぶ == false;
		 */
		useAtr = UseClassification.UseClass_Use;
		domain = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, true, false);
		check = domain.useDivergenceReasonSelected(useAtr);
		assertThat(check).isFalse();
	}

}
