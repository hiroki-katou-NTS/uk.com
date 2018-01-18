package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * 変形労働時間勤務の法定内集計設定　（テスト）
 * @author shuichu_ishida
 */
public class LegalAggrSetOfIrgTest {

	private LegalAggrSetOfIrg legalAggrSetOfIrg;
	
	private void setup(){
		this.legalAggrSetOfIrg = LegalAggrSetOfIrg.of(
				new AggregateTimeSet(),
				new ExcessOutsideTimeSet(),
				new CalcSettingOfIrregular(),
				new SettlementPeriodOfIrg());
		
		// 精算期間
		val settlementPeriods = this.legalAggrSetOfIrg.getSettlementPeriod().getSettlementPeriods();
		settlementPeriods.add(new SettlementPeriod(new Month(11), new Month(2)));
		settlementPeriods.add(new SettlementPeriod(new Month(3), new Month(3)));
		settlementPeriods.add(new SettlementPeriod(new Month(4), new Month(6)));
		settlementPeriods.add(new SettlementPeriod(new Month(7), new Month(10)));
	}

	/**
	 * 判定年月が含まれる精算期間の過去年月リストを取得する　（テスト：年をまたがる期間に該当し、期間内過去月も年をまたがる）
	 */
	@Test
	public void getPastSettlementYearMonthsTest01(){
		
		// setup
		this.setup();
		
		// exercize
		val nowYearMonth = YearMonth.of(201702);
		val actualList = this.legalAggrSetOfIrg.getPastSettlementYearMonths(nowYearMonth);
		String actual = nowYearMonth.toString() + " = ";
		boolean isExist = false;
		for (YearMonth yearMonth : actualList){
			actual += yearMonth.toString() + ";";
			isExist = true;
		}
		if (!isExist) actual += "No Match.";
		
		// verify
		assertEquals("201702 = 201611;201612;201701;", actual);
	}

	/**
	 * 判定年月が含まれる精算期間の過去年月リストを取得する　（テスト：単月期間とマッチし、期間内過去月がない）
	 */
	@Test
	public void getPastSettlementYearMonthsTest02(){
		
		// setup
		this.setup();
		
		// exercize
		val nowYearMonth = YearMonth.of(201703);
		val actualList = this.legalAggrSetOfIrg.getPastSettlementYearMonths(nowYearMonth);
		String actual = nowYearMonth.toString() + " = ";
		boolean isExist = false;
		for (YearMonth yearMonth : actualList){
			actual += yearMonth.toString() + ";";
			isExist = true;
		}
		if (!isExist) actual += "No Match.";
		
		// verify
		assertEquals("201703 = No Match.", actual);
	}

	/**
	 * 判定年月が含まれる精算期間の過去年月リストを取得する　（テスト：複数月通常期間に該当し、期間内過去月がある）
	 */
	@Test
	public void getPastSettlementYearMonthsTest03(){
		
		// setup
		this.setup();
		
		// exercize
		val nowYearMonth = YearMonth.of(201709);
		val actualList = this.legalAggrSetOfIrg.getPastSettlementYearMonths(nowYearMonth);
		String actual = nowYearMonth.toString() + " = ";
		boolean isExist = false;
		for (YearMonth yearMonth : actualList){
			actual += yearMonth.toString() + ";";
			isExist = true;
		}
		if (!isExist) actual += "No Match.";
		
		// verify
		assertEquals("201709 = 201707;201708;", actual);
	}

	/**
	 * 判定年月と同じ精算終了月があるか判定する　（テスト：該当終了月がある）
	 */
	@Test
	public void isSameSettlementEndMonthTest01(){
		
		// setup
		this.setup();
		
		// exercize
		val nowYearMonth = YearMonth.of(201702);
		val isSame = this.legalAggrSetOfIrg.isSameSettlementEndMonth(nowYearMonth);
		String actual = nowYearMonth.toString() + " = " + (isSame ? "Match." : "No Match.");
		
		// verify
		assertEquals("201702 = Match.", actual);
	}

	/**
	 * 判定年月と同じ精算終了月があるか判定する　（テスト：該当終了月がない）
	 */
	@Test
	public void isSameSettlementEndMonthTest02(){
		
		// setup
		this.setup();
		
		// exercize
		val nowYearMonth = YearMonth.of(201704);
		val isSame = this.legalAggrSetOfIrg.isSameSettlementEndMonth(nowYearMonth);
		String actual = nowYearMonth.toString() + " = " + (isSame ? "Match." : "No Match.");
		
		// verify
		assertEquals("201704 = No Match.", actual);
	}
}
