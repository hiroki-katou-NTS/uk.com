package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class RadiusAtrTest {

	@Test
	public void getters() {
		RadiusAtr radiusAtr = RadiusAtr.toEnum(1);
		NtsAssert.invokeGetters(radiusAtr);
	}
	
	@Test
	public void test() {
		RadiusAtr radiusAtr = RadiusAtr.toEnum(0);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_50);
		radiusAtr = RadiusAtr.toEnum(1);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_100);
		radiusAtr = RadiusAtr.toEnum(2);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_200);
		radiusAtr = RadiusAtr.toEnum(3);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_300);
		radiusAtr = RadiusAtr.toEnum(4);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_400);
		radiusAtr = RadiusAtr.toEnum(5);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_500);
		radiusAtr = RadiusAtr.toEnum(6);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_600);
		radiusAtr = RadiusAtr.toEnum(7);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_700);
		radiusAtr = RadiusAtr.toEnum(8);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_800);
		radiusAtr = RadiusAtr.toEnum(9);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_900);
		radiusAtr = RadiusAtr.toEnum(10);
		assertThat(radiusAtr).isEqualTo(RadiusAtr.M_1000);
	}

	@Test
	public void testGetValue() {
		int check = RadiusAtr.M_50.getValue();
		assertThat(check).isEqualTo(50);
		check = RadiusAtr.M_100.getValue();
		assertThat(check).isEqualTo(100);
		check = RadiusAtr.M_200.getValue();
		assertThat(check).isEqualTo(200);
		check = RadiusAtr.M_300.getValue();
		assertThat(check).isEqualTo(300);
		check = RadiusAtr.M_400.getValue();
		assertThat(check).isEqualTo(400);
		check = RadiusAtr.M_500.getValue();
		assertThat(check).isEqualTo(500);
		check = RadiusAtr.M_600.getValue();
		assertThat(check).isEqualTo(600);
		check = RadiusAtr.M_700.getValue();
		assertThat(check).isEqualTo(700);
		check = RadiusAtr.M_800.getValue();
		assertThat(check).isEqualTo(800);
		check = RadiusAtr.M_900.getValue();
		assertThat(check).isEqualTo(900);
		check = RadiusAtr.M_1000.getValue();
		assertThat(check).isEqualTo(1000);
	}
}
