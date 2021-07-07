package nts.uk.ctx.at.record.dom.stamp.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * 
 * @author chungnt
 *
 */

public class SettingsUsingEmbossingTest {

	@Test
	public void getters() {
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingDefault();
		NtsAssert.invokeGetters(settingsUsingEmbossing);
	}
	
	/**
	 * case FINGER_AUTHC:
	 */
	@Test
	public void testSettingsUsingEmbossingTest() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingFingerAuthc();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.FINGER_AUTHC)).isTrue();
		
	}
	
	/**
	 * case IC_CARD:
	 */
	@Test
	public void testSettingsUsingEmbossingTest_1() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingIcCard();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.IC_CARD)).isTrue();
		
	}

	
	/**
	 * case INDIVITION:
	 */
	@Test
	public void testSettingsUsingEmbossingTest_2() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingIndivition();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.INDIVITION)).isTrue();
		
	}

	
	/**
	 * case NAME_SELECTION:
	 */
	@Test
	public void testSettingsUsingEmbossingTest_3() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingDefault();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.NAME_SELECTION)).isTrue();
		
	}

	
	/**
	 * case PORTAL:
	 */
	@Test
	public void testSettingsUsingEmbossingTest_4() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingPortal();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.PORTAL)).isTrue();
		
	}

	
	/**
	 * case smart_phone;
	 */
	@Test
	public void testSettingsUsingEmbossingTest_5() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingSmartPhone();
		
		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.SMART_PHONE)).isTrue();
		
	}
	
	/**
	 * case その他;
	 */
	@Test
	public void testSettingsUsingEmbossingTest_6() {
		
		SettingsUsingEmbossing settingsUsingEmbossing = SettingsUsingEmbossingHelper.getSettingsUsingEmbossingRicohStamp();

		assertThat(settingsUsingEmbossing.canUsedStamping(StampMeans.RICOH_COPIER)).isTrue();
		
	}
		
	

}
