package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

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
		int beforeDelete = grantDateTbl.getElapseYear().size();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(3);
		int afterDelete  = grantDateTbl.getElapseYear().size();
		
		assertThat(beforeDelete).isEqualTo(afterDelete);
	}
	
	// elapseYear.size() > numOfElapsedYears
	// elapseNo < numOfElapsedYears 
	@Test
	public void deleteMoreTableThanElapsedYearsTableTest2() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl1();
		int beforeDelete = grantDateTbl.getElapseYear().size();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(3);
		int afterDelete  = grantDateTbl.getElapseYear().size();
		
		assertThat(beforeDelete).isEqualTo(afterDelete);
	}
	
	// elapseYear.size() > numOfElapsedYears
	// elapseNo > numOfElapsedYears 
	@Test
	public void deleteMoreTableThanElapsedYearsTableTest3() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl2();
		int beforeDelete = grantDateTbl.getElapseYear().size();
		grantDateTbl.deleteMoreTableThanElapsedYearsTable(3);
		int afterDelete  = grantDateTbl.getElapseYear().size();
		
		assertThat(beforeDelete).isNotEqualTo(afterDelete);
	}
	
	// !(numOfGrants < numOfElapsedYears)
	@Test
	public void addLessTableThanElapsedYearsTable1() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl1();
		int beforeAdd = grantDateTbl.getElapseYear().size();
		grantDateTbl.addLessTableThanElapsedYearsTable(3);
		int afterAdd  = grantDateTbl.getElapseYear().size();
		
		assertThat(beforeAdd).isEqualTo(afterAdd);
	}
	
	// numOfGrants < numOfElapsedYears
	@Test
	public void addLessTableThanElapsedYearsTable2() {
		GrantDateTbl grantDateTbl = GrantDateTblHelper.createGrantDateTbl1();
		int beforeAdd = grantDateTbl.getElapseYear().size();
		grantDateTbl.addLessTableThanElapsedYearsTable(7);
		int afterAdd  = grantDateTbl.getElapseYear().size();
		
		assertThat(beforeAdd).isNotEqualTo(afterAdd);
	}
}
