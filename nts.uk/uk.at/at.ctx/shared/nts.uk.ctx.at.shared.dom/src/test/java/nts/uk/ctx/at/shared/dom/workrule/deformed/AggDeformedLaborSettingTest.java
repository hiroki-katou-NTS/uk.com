package nts.uk.ctx.at.shared.dom.workrule.deformed;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

public class AggDeformedLaborSettingTest {

	/**
	 * test [1] 変形労働に対応する日次の勤怠項目を取得する	
	 */
	@Test
	public void testGetDaiLyAttendanceId() {
		List<Integer> listAttdId = new ArrayList<>();
		AggDeformedLaborSetting domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.NOTUSE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(551,1122);
	}
	
	/**
	 * test [2] 変形労働に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceId() {
		List<Integer> listAttdId = new ArrayList<>();
		AggDeformedLaborSetting domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.NOTUSE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
				.containsExactly(588,1351,1352,1353,1354,1355);
	}

	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//@フレックス勤務を管理する == しない	
		AggDeformedLaborSetting domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.NOTUSE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(551,1122);
		//@フレックス勤務を管理する != しない	
		domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.USE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	
	/**
	 * test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//@フレックス勤務を管理する == しない	
		AggDeformedLaborSetting domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.NOTUSE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(588,1351,1352,1353,1354,1355);
		//@フレックス勤務を管理する != しない	
		domain = new AggDeformedLaborSetting(new CompanyId("companyId"), UseAtr.USE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
