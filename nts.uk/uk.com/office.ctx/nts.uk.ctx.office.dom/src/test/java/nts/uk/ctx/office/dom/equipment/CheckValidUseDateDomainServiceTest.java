package nts.uk.ctx.office.dom.equipment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.equipment.CheckValidUseDateDomainService.Require;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformationTestHelper;

@RunWith(JMockit.class)
public class CheckValidUseDateDomainServiceTest {

	private static final String CID = "cid";
	private static final String EQUIPMENT_CODE = "00001";

	@Injectable
	private Require require;

	/**
	 * 利用日が有効期限内かチェックする = true
	 */
	@Test
	public void testValidateTrue() {
		// given
		GeneralDate useDate = GeneralDate.today();
		new Expectations() {
			{
				require.getEquipmentInfo(CID, EQUIPMENT_CODE);
				result = Optional.of(EquipmentInformationTestHelper.mockDomain(""));
			}
		};

		// when
		boolean isValid = CheckValidUseDateDomainService.validate(require, CID, new EquipmentCode(EQUIPMENT_CODE), useDate);
		
		// then
		assertThat(isValid).isTrue();
	}
	
	/**
	 * 利用日が有効期限内かチェックする = false
	 * $設備情報.isEmpty()
	 */
	@Test
	public void testValidateFalse1() {
		// given
		GeneralDate useDate = GeneralDate.today().addDays(1);

		// when
		boolean isValid = CheckValidUseDateDomainService.validate(require, CID, new EquipmentCode(EQUIPMENT_CODE), useDate);
		
		// then
		assertThat(isValid).isFalse();
	}
	
	/**
	 * 利用日が有効期限内かチェックする = false
	 * $設備情報.有効期限内か(利用日)　==　false
	 */
	@Test
	public void testValidateFalse2() {
		// given
		GeneralDate useDate = GeneralDate.today().addDays(1);
		new Expectations() {
			{
				require.getEquipmentInfo(CID, EQUIPMENT_CODE);
				result = Optional.of(EquipmentInformationTestHelper.mockDomain(""));
			}
		};

		// when
		boolean isValid = CheckValidUseDateDomainService.validate(require, CID, new EquipmentCode(EQUIPMENT_CODE), useDate);
		
		// then
		assertThat(isValid).isFalse();
	}
}
