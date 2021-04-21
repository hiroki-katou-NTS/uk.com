package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.specialholiday.algorithm.service.MatchingElapsedYearsTblWithNumDaysGrantedTblService;

/**
 * 
 * @author dungbn
 *
 */
@RunWith(JMockit.class)
public class MatchingElapsedYearsTblWithNumDaysGrantedTblServiceTest {

	@Injectable
	private MatchingElapsedYearsTblWithNumDaysGrantedTblService.Require require;

	// grantDateTbl.isSpecified() = true
	// require.findBySphdCd() is not empty
	// checkExist = 1
	@Test
	public void testConsistentGrantDaysTbl() {

		// input
		ElapseYear elapseYear = ElapseYearHelper.createElapseYear1();
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl();

		List<GrantDateTbl> listGrantDateTbl = new ArrayList<GrantDateTbl>();
		GrantDateTbl grantDateTbl1 = GrantDateTblHelper.createGrantDateTbl1();
		GrantDateTbl grantDateTbl4 = GrantDateTblHelper.createGrantDateTbl4();
		listGrantDateTbl.add(grantDateTbl1);
		listGrantDateTbl.add(grantDateTbl4);

		new Expectations() {
			{
				require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
				result = listGrantDateTbl;
			}
		};

		val result = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, elapseYear, grantDateTbl);
		
		assertThat(result)
			.extracting(
					d -> d.getCompanyId(),
					d -> d.getSpecialHolidayCode().v(),
					d -> d.getGrantDateCode().v(),
					d -> d.getGrantDateName().v(),
					d -> d.getElapseYear(),
					d -> d.isSpecified(),
					d -> d.getGrantedDays().get().v())
			.containsExactly(
					tuple("companyId", grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v(), grantDateTbl.getGrantDateName().v(), grantDateTbl.getElapseYear(), grantDateTbl.isSpecified(), grantDateTbl.getGrantedDays().get().v()),
					tuple("companyId", grantDateTbl4.getSpecialHolidayCode().v(), grantDateTbl4.getGrantDateCode().v(), grantDateTbl4.getGrantDateName().v(), grantDateTbl4.getElapseYear(), false, grantDateTbl4.getGrantedDays().get().v()));
	}
	
		// grantDateTbl.isSpecified() = false
	    // require.findBySphdCd() is not empty
		// checkExist = 1
		@Test
		public void testConsistentGrantDaysTbl2() {

			// input
			ElapseYear elapseYear = ElapseYearHelper.createElapseYear1();
			GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl3();

			List<GrantDateTbl> listGrantDateTbl = new ArrayList<GrantDateTbl>();
			GrantDateTbl grantDateTbl1 = GrantDateTblHelper.createGrantDateTbl1();
			GrantDateTbl grantDateTbl4 = GrantDateTblHelper.createGrantDateTbl4();
			listGrantDateTbl.add(grantDateTbl1);
			listGrantDateTbl.add(grantDateTbl4);

			new Expectations() {
				{
					require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
					result = listGrantDateTbl;
				}
			};

			val result = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, elapseYear, grantDateTbl);
			
			assertThat(result)
				.extracting(
						d -> d.getCompanyId(),
						d -> d.getSpecialHolidayCode().v(),
						d -> d.getGrantDateCode().v(),
						d -> d.getGrantDateName().v(),
						d -> d.getElapseYear(),
						d -> d.isSpecified(),
						d -> d.getGrantedDays().get().v())
				.containsExactly(
						tuple("companyId", grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v(), grantDateTbl.getGrantDateName().v(), grantDateTbl.getElapseYear(), grantDateTbl.isSpecified(), grantDateTbl.getGrantedDays().get().v()),
						tuple("companyId", grantDateTbl4.getSpecialHolidayCode().v(), grantDateTbl4.getGrantDateCode().v(), grantDateTbl4.getGrantDateName().v(), grantDateTbl4.getElapseYear(), grantDateTbl4.isSpecified(), grantDateTbl4.getGrantedDays().get().v()));
		}
		
	    // require.findBySphdCd() is empty => checkExist.get() == 0
		@Test
		public void testConsistentGrantDaysTbl3() {

			// input
			ElapseYear elapseYear = ElapseYearHelper.createElapseYear1();
			GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl3();
			List<GrantDateTbl> listGrantDateTbl = new ArrayList<GrantDateTbl>();
			new Expectations() {
				{
					require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
					result = listGrantDateTbl;
				}
			};

			val result = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, elapseYear, grantDateTbl);
			
			assertThat(result)
				.extracting(
						d -> d.getCompanyId(),
						d -> d.getSpecialHolidayCode().v(),
						d -> d.getGrantDateCode().v(),
						d -> d.getGrantDateName().v(),
						d -> d.getElapseYear(),
						d -> d.isSpecified(),
						d -> d.getGrantedDays().get().v())
				.containsExactly(
						tuple("companyId", grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v(), grantDateTbl.getGrantDateName().v(), grantDateTbl.getElapseYear(), grantDateTbl.isSpecified(), grantDateTbl.getGrantedDays().get().v()));
		}
		
		// require.findBySphdCd() is not empty and !(e.getGrantDateCode().v().equals(grantDateTbl.getGrantDateCode().v())) => checkExist.get() == 0
		// grantDateTbl.isSpecified() = true
		@Test
		public void testConsistentGrantDaysTbl4() {

			// input
			ElapseYear elapseYear = ElapseYearHelper.createElapseYear1();
			GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl();
			List<GrantDateTbl> listGrantDateTbl = new ArrayList<GrantDateTbl>();
			GrantDateTbl grantDateTbl4 = GrantDateTblHelper.createGrantDateTbl4();
			listGrantDateTbl.add(grantDateTbl4);

			new Expectations() {
				{
					require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
					result = listGrantDateTbl;
				}
			};

			val result = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, elapseYear, grantDateTbl);
			
			assertThat(result)
				.extracting(
						d -> d.getCompanyId(),
						d -> d.getSpecialHolidayCode().v(),
						d -> d.getGrantDateCode().v(),
						d -> d.getGrantDateName().v(),
						d -> d.getElapseYear(),
						d -> d.isSpecified(),
						d -> d.getGrantedDays().get().v())
				.containsExactly(
						tuple("companyId", grantDateTbl4.getSpecialHolidayCode().v(), grantDateTbl4.getGrantDateCode().v(), grantDateTbl4.getGrantDateName().v(), grantDateTbl4.getElapseYear(), false, grantDateTbl4.getGrantedDays().get().v()),
						tuple("companyId", grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v(), grantDateTbl.getGrantDateName().v(), grantDateTbl.getElapseYear(), grantDateTbl.isSpecified(), grantDateTbl.getGrantedDays().get().v()));
		}
		
		// require.findBySphdCd() is not empty and !(e.getGrantDateCode().v().equals(grantDateTbl.getGrantDateCode().v())) => checkExist.get() == 0
		// grantDateTbl.isSpecified() = false
		@Test
		public void testConsistentGrantDaysTbl5() {

			// input
			ElapseYear elapseYear = ElapseYearHelper.createElapseYear1();
			GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl3();
			List<GrantDateTbl> listGrantDateTbl = new ArrayList<GrantDateTbl>();
			GrantDateTbl grantDateTbl4 = GrantDateTblHelper.createGrantDateTbl4();
			listGrantDateTbl.add(grantDateTbl4);

			new Expectations() {
				{
					require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
					result = listGrantDateTbl;
				}
			};

			val result = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, elapseYear, grantDateTbl);
			
			assertThat(result)
				.extracting(
						d -> d.getCompanyId(),
						d -> d.getSpecialHolidayCode().v(),
						d -> d.getGrantDateCode().v(),
						d -> d.getGrantDateName().v(),
						d -> d.getElapseYear(),
						d -> d.isSpecified(),
						d -> d.getGrantedDays().get().v())
				.containsExactly(
						tuple("companyId", grantDateTbl4.getSpecialHolidayCode().v(), grantDateTbl4.getGrantDateCode().v(), grantDateTbl4.getGrantDateName().v(), grantDateTbl4.getElapseYear(), grantDateTbl4.isSpecified(), grantDateTbl4.getGrantedDays().get().v()),
						tuple("companyId", grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v(), grantDateTbl.getGrantDateName().v(), grantDateTbl.getElapseYear(), grantDateTbl.isSpecified(), grantDateTbl.getGrantedDays().get().v()));
		}
		
}
