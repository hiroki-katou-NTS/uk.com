package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import org.junit.Test;

/**
 * Java リフレクションテスト
 * @author masaaki_jinno
 *
 */
public class ReflectTest {

	@Test
	public void test1(){

		Class<nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor>
			aggClass = nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor.class;

		System.out.println(aggClass.getName());

		for( java.lang.reflect.Field f : aggClass.getFields() ) {
			System.out.println("--------------------------------------");
			System.out.println("getGenericType = " + f.getGenericType().toString());
			System.out.println("getName = " + f.getName().toString());
			System.out.println("getName = " + f.getName().toString());


		}



	}



}
