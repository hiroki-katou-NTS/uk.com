package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

@RunWith(JMockit.class)
public class MedicalTimeOfEmployeeTest {

	@Test
	public void testGetter(
			@Injectable Form9OutputMedicalTime dayShiftHours
		,	@Injectable Form9OutputMedicalTime nightShiftHours
		,	@Injectable Form9OutputMedicalTime totalNightShiftHours) {

		val empMedicalTime = new MedicalTimeOfEmployee(
					"employeeId"
				,	GeneralDate.ymd(2021, 01, 01)
				,	ScheRecAtr.RECORD
				,	dayShiftHours
				,	nightShiftHours
				,	totalNightShiftHours
					);

		NtsAssert.invokeGetters( empMedicalTime );

	}

	@Test
	public void testCreate() {

		val dailyWork = Helper.createDailyWorks( "sid1", GeneralDate.ymd(2021, 01, 01) );

		//Act
		val empMedicalTime = MedicalTimeOfEmployee.create( dailyWork, ScheRecAtr.RECORD );

		//Assert
		assertThat( empMedicalTime.getEmployeeId() ).isEqualTo( "sid1" );
		assertThat( empMedicalTime.getYmd() ).isEqualTo( GeneralDate.ymd(2021, 01, 01) );
		assertThat( empMedicalTime.getScheRecAtr() ).isEqualTo( ScheRecAtr.RECORD );

		/** 日勤時間 **/
		assertThat( empMedicalTime.getDayShiftHours().getTime().v() ).isEqualTo( 480 );
		assertThat( empMedicalTime.getDayShiftHours().isDeductionDateFromDeliveryTime() ).isTrue();

		/** 夜勤時間 **/
		assertThat( empMedicalTime.getNightShiftHours().getTime().v() ).isEqualTo( 480 );
		assertThat( empMedicalTime.getNightShiftHours().isDeductionDateFromDeliveryTime() ).isTrue();

		/** 総夜勤時間 **/
		assertThat( empMedicalTime.getTotalNightShiftHours().getTime().v() ).isEqualTo( 480 );
		assertThat( empMedicalTime.getTotalNightShiftHours().isDeductionDateFromDeliveryTime() ).isTrue();

	}

	private static class Helper {

		@Injectable
		private static WorkInfoOfDailyAttendance workInfo;

		/**
		 * 日別実績(Work)を作る
		 * @param sid 社員ID
		 * @param ymd 年月日
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd) {
			return new IntegrationOfDaily(
					 	sid, ymd, workInfo
					,	CalAttrOfDailyAttd.defaultData()
					,	null
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd(Collections.emptyList())
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Optional.empty()
				);
		}
	}

}
