package nts.uk.ctx.office.dom.equipment.achievement.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentFormSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentPerformInputFormatSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
import nts.uk.ctx.office.dom.equipment.achievement.domainservice.RegisterEquipmentItemSettingMaster.Require;

@RunWith(JMockit.class)
public class RegisterEquipmentItemSettingMasterTest {

	@Injectable
	Require require;
	
	/**
	 * [1] 登録する
	 *  $項目NODel.isEmpty() == true; 項目設定List.isEmpty() == true;
	 *  $フォーマット設定DB.isEmpty() == true; フォーマット設定.isPresent() == false;
	 *  $設備帳票設定DB.isPresent() == false; 設備帳票設定.isPresent() == false;
	 */
	@Test
	public void register1() {
		String cid = "dummy-cid";
		List<EquipmentUsageRecordItemSetting> items = new ArrayList<EquipmentUsageRecordItemSetting>();
		Optional<EquipmentPerformInputFormatSetting> format = Optional.empty();
		Optional<EquipmentFormSetting> formSetting = Optional.empty();
				
		new Expectations() {
			{
				require.getEURItemSettings(cid);
				result = new ArrayList<>();
			}
			{
				require.getEURInputFormatSetting(cid);
			}
			{
				require.getEquipmentFormSettings(cid);
			}
		};
		
		PersistenceProcess result = RegisterEquipmentItemSettingMaster.register(require, cid, items, format, formSetting);
		assertThat(result.getInputFormatSettingTasks()).isEmpty();
		assertThat(result.getItemSettingTasks()).isEmpty();
		assertThat(result.getFormSettingTasks()).isEmpty();
	}
	
	/**
	 * [1] 登録する
	 *  $項目NODel.isEmpty() == false; 項目設定List.isEmpty() == true;
	 *  $フォーマット設定DB.isEmpty() == false; フォーマット設定.isPresent() == false;
	 *  $設備帳票設定DB.isPresent() == true; 設備帳票設定.isPresent() == false;
	 */
	@Test
	public void register2() {
		String cid = "dummy-cid";
		List<EquipmentUsageRecordItemSetting> items = new ArrayList<EquipmentUsageRecordItemSetting>();
		Optional<EquipmentPerformInputFormatSetting> format = Optional.empty();
		Optional<EquipmentFormSetting> formSetting = Optional.empty();

		new Expectations() {
			{
				require.getEURItemSettings(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEURItemSettings();
			}
			{
				require.getEURInputFormatSetting(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEURInputFormatSetting();
			}
			{
				require.getEquipmentFormSettings(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEquipmentFormSettings();
			}
		};
		PersistenceProcess result = RegisterEquipmentItemSettingMaster.register(require, cid, items, format, formSetting);
		
		assertThat(result.getItemSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getItemSettingTasks().get(0),
			any -> require.deleteAllEURItemSettings(cid, any.get())
		);
		
		assertThat(result.getInputFormatSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getInputFormatSettingTasks().get(0),
			any -> require.deleteEURInputFormatSetting(cid)
		);
		
		assertThat(result.getFormSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getFormSettingTasks().get(0),
			any -> require.deleteEquipmentFormSettings(cid)
		);
	}
	
	/**
	 * [1] 登録する
	 *  $項目NODel.isEmpty() == true; 項目設定List.isEmpty() == false;
	 *  $フォーマット設定DB.isEmpty() == true; フォーマット設定.isPresent() == true;
	 *  $設備帳票設定DB.isPresent() == false; 設備帳票設定.isPresent() == true;
	 */
	@Test
	public void register3() {
		String cid = "dummy-cid";
		List<EquipmentUsageRecordItemSetting> items = RegisterEquipmentItemSettingMasterTestHelper.recordItemSettings();
		Optional<EquipmentPerformInputFormatSetting> format = RegisterEquipmentItemSettingMasterTestHelper.inputFormatSetting();
		Optional<EquipmentFormSetting> formSetting = RegisterEquipmentItemSettingMasterTestHelper.formSettings();

		new Expectations() {
			{
				require.getEURItemSettings(cid);
				result = new ArrayList<>();
			}
			{
				require.getEURInputFormatSetting(cid);
			}
			{
				require.getEquipmentFormSettings(cid);
			}
		};
		PersistenceProcess result = RegisterEquipmentItemSettingMaster.register(require, cid, items, format, formSetting);
		
		assertThat(result.getItemSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getItemSettingTasks().get(0),
			any -> require.insertAllEURItemSettings(any.get())
		);
		
		assertThat(result.getInputFormatSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getInputFormatSettingTasks().get(0),
			any -> require.insertEURInputFormatSetting(any.get())
		);
		
		assertThat(result.getFormSettingTasks().size()).isEqualTo(1);
		NtsAssert.atomTask(
			() -> result.getFormSettingTasks().get(0),
			any -> require.insertEuipmentFormSettings(any.get())
		);
	}
	
	/**
	 * [1] 登録する
	 *  $項目NODel.isEmpty() == false; 項目設定List.isEmpty() == false;
	 *  $フォーマット設定DB.isEmpty() == false; フォーマット設定.isPresent() == true;
	 *  $設備帳票設定DB.isPresent() == true; 設備帳票設定.isPresent() == true;
	 */
	@Test
	public void register4() {
		String cid = "dummy-cid";
		List<EquipmentUsageRecordItemSetting> items = RegisterEquipmentItemSettingMasterTestHelper.recordItemSettings();
		Optional<EquipmentPerformInputFormatSetting> format = RegisterEquipmentItemSettingMasterTestHelper.inputFormatSetting();
		Optional<EquipmentFormSetting> formSetting = RegisterEquipmentItemSettingMasterTestHelper.formSettings();

		new Expectations() {
			{
				require.getEURItemSettings(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEURItemSettings();
			}
			{
				require.getEURInputFormatSetting(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEURInputFormatSetting();
			}
			{
				require.getEquipmentFormSettings(cid);
				result = RegisterEquipmentItemSettingMasterTestHelper.getEquipmentFormSettings();
			}
		};
		PersistenceProcess result = RegisterEquipmentItemSettingMaster.register(require, cid, items, format, formSetting);
		
		assertThat(result.getItemSettingTasks().size()).isEqualTo(2);
		NtsAssert.atomTask(
			() -> result.getItemSettingTasks().get(0),
			any -> require.deleteAllEURItemSettings(cid, any.get())
		);
		NtsAssert.atomTask(
			() -> result.getItemSettingTasks().get(1),
			any -> require.insertAllEURItemSettings(any.get())
		);
		
		assertThat(result.getInputFormatSettingTasks().size()).isEqualTo(2);
		NtsAssert.atomTask(
			() -> result.getInputFormatSettingTasks().get(0),
			any -> require.deleteEURInputFormatSetting(cid)
		);
		NtsAssert.atomTask(
			() -> result.getInputFormatSettingTasks().get(1),
			any -> require.insertEURInputFormatSetting(any.get())
		);
		
		assertThat(result.getFormSettingTasks().size()).isEqualTo(2);
		NtsAssert.atomTask(
			() -> result.getFormSettingTasks().get(0),
			any -> require.deleteEquipmentFormSettings(cid)
		);
		NtsAssert.atomTask(
			() -> result.getFormSettingTasks().get(1),
			any -> require.insertEuipmentFormSettings(any.get())
		);
	}
}
