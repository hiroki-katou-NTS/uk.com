package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author dungbn
 *
 */

@RunWith(JMockit.class)
public class GrantDateTblTest {

	@Test
	public void getters() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl();
		NtsAssert.invokeGetters(grantDateTbl);
	}
	
	// elapseYear.size() < numOfElapsedYears
	@Test
	public void deleteMoreTableThanElapsedYearsTableTest1() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(3);
		
		assertThat(grantDateTbl.getElapseYear())
			.extracting(
					d -> d.getElapseNo(),
					d -> d.getGrantedDays().v())
			.containsExactly(
					tuple(1, 6));
			
	}
	
	// elapseYear.size() = numOfElapsedYears
	//　境界値 = 1
	@Test
	public void deleteMoreTableThanElapsedYearsTableTest4() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(1); 
		
		assertThat(grantDateTbl.getElapseYear())
			.extracting(
					d -> d.getElapseNo(),
					d -> d.getGrantedDays().v())
			.containsExactly(
					tuple(1, 6));
			
	}
	
	// elapseYear.size() > numOfElapsedYears
	// delete elapseNo > numOfElapsedYears
	@Test
	public void deleteMoreTableThanElapsedYearsTableTest3() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl2();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(3);
		
		assertThat(grantDateTbl.getElapseYear())
			.extracting(
					d -> d.getElapseNo(),
					d -> d.getGrantedDays().v())
			.containsExactly(
					tuple(1, 5));
	}
	
	// numOfGrants > numOfElapsedYears
	@Test
	public void addLessTableThanElapsedYearsTable1() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl1();
		grantDateTbl.addLessTableThanElapsedYearsTable(3);
		
		assertThat(grantDateTbl.getElapseYear())
			.extracting(
					d -> d.getElapseNo(),
					d -> d.getGrantedDays().v())
			.containsExactly(
					tuple(1, 6),
					tuple(2, 3),
					tuple(3, 3),
					tuple(4, 5));
	}
	
	// numOfGrants <= numOfElapsedYears
	@Test
	public void addLessTableThanElapsedYearsTable2() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl1();
		grantDateTbl.addLessTableThanElapsedYearsTable(6);
		
		assertThat(grantDateTbl.getElapseYear())
			.extracting(
					d -> d.getElapseNo(),
					d -> d.getGrantedDays().v())
			.containsExactly(
					tuple(1, 6),
					tuple(2, 3),
					tuple(3, 3),
					tuple(4, 5),
					tuple(5, 0),
					tuple(6, 0));
	}
}
