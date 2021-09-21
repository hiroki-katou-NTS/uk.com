package nts.uk.ctx.office.dom.equipment.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.ItemClassification;
import nts.uk.ctx.office.dom.equipment.achievement.domain.EquipmentUsageRecordItemSettingTestHelper;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;

@RunWith(JMockit.class)
public class EquipmentDataTest {

	@Injectable
	private Require require;

	@Test
	public void getters() {
		// when
		EquipmentData domain = EquipmentDataTestHelper.mockDomain(GeneralDate.today());

		// then
		NtsAssert.invokeGetters(domain);
	}

	/**
	 * [C-1] 新規登録 
	 * $エラー情報.isEmpty() ＝＝ true
	 */
	@Test
	public void testCreateTempEquipmentDataNoErr() {
		// given
		GeneralDate useDate = GeneralDate.today();
		EquipmentData domain = EquipmentDataTestHelper.mockDomain(useDate);

		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "4");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("4", ItemClassification.NUMBER, 1000));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "7");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("7", ItemClassification.TIME, 1000));
			};
		};

		// when
		EquipmentUsageCreationResultTemp temp = EquipmentData.createData(require, EquipmentDataTestHelper.CID,
				new EquipmentClassificationCode(EquipmentDataTestHelper.EQUIPMENT_CLS_CD),
				new EquipmentCode(EquipmentDataTestHelper.EQUIPMENT_CD), EquipmentDataTestHelper.SID, useDate,
				EquipmentDataTestHelper.mockValueMap());

		// then
		assertThat(temp.getEquipmentData().isPresent()).isTrue();
		assertThat(temp.getEquipmentData().get().getEquipmentCode()).isEqualTo(domain.getEquipmentCode());
		assertThat(temp.getEquipmentData().get().getItemDatas()).isEqualTo(domain.getItemDatas());
	}

	/**
	 * [C-1] 新規登録 
	 * $エラー情報.isEmpty() ＝＝ false
	 */
	@Test
	public void testCreateTempEquipmentDataHasErr() {
		// given
		GeneralDate useDate = GeneralDate.today();

		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "4");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("4", ItemClassification.NUMBER, 10));
			};
		};

		// when
		EquipmentUsageCreationResultTemp temp = EquipmentData.createData(require, EquipmentDataTestHelper.CID,
				new EquipmentClassificationCode(EquipmentDataTestHelper.EQUIPMENT_CLS_CD),
				new EquipmentCode(EquipmentDataTestHelper.EQUIPMENT_CD), EquipmentDataTestHelper.SID, useDate,
				EquipmentDataTestHelper.mockValueMap());

		// then
		assertThat(temp.getEquipmentData().isPresent()).isFalse();
		assertThat(temp.getErrorMap().isEmpty()).isFalse();
	}

	/**
	 * [1] 変更登録 
	 * $エラー情報.isEmpty() ＝＝ true
	 */
	@Test
	public void testUpdateItemDatasTestNoErr() {
		// given
		GeneralDate useDate = GeneralDate.today();
		EquipmentData domain = EquipmentDataTestHelper.mockDomain(useDate);
		Map<EquipmentItemNo, ActualItemUsageValue> valuesMap = EquipmentDataTestHelper.mockValueMap();
		valuesMap.entrySet().forEach(e -> {
			switch (e.getKey().v()) {
			case "1":
				e.setValue(new ActualItemUsageValue("xyzz"));
				break;
			case "4":
				e.setValue(new ActualItemUsageValue("1"));
				break;
			case "7":
				e.setValue(new ActualItemUsageValue("720"));
				break;
			}
		});
		List<String> valuesToChange = valuesMap.values().stream().map(ActualItemUsageValue::v)
				.collect(Collectors.toList());

		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "4");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("4", ItemClassification.NUMBER, 1000));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "7");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("7", ItemClassification.TIME, 1000));
			};
		};

		// when
		EquipmentUsageCreationResultTemp temp = domain.updateItemDatas(require, EquipmentDataTestHelper.CID, valuesMap);
		List<String> newValues = domain.getItemDatas().stream()
				.map(data -> data.getActualValue().map(ActualItemUsageValue::v).orElse(null)).filter(Objects::nonNull)
				.collect(Collectors.toList());

		// then
		assertThat(temp.getEquipmentData().isPresent()).isTrue();
		assertThat(temp.getErrorMap().isEmpty()).isTrue();
		assertThat(newValues).isEqualTo(valuesToChange);
	}
	
	/**
	 * [1] 変更登録 
	 * $エラー情報.isEmpty() ＝＝ false
	 */
	@Test
	public void testUpdateItemDatasTestHasErr() {
		// given
		GeneralDate useDate = GeneralDate.today();
		EquipmentData domain = EquipmentDataTestHelper.mockDomain(useDate);
		Map<EquipmentItemNo, ActualItemUsageValue> valuesMap = EquipmentDataTestHelper.mockValueMap();
		List<String> valuesBeforeChange = valuesMap.values().stream().map(ActualItemUsageValue::v)
				.collect(Collectors.toList());
		valuesMap.entrySet().forEach(e -> {
			switch (e.getKey().v()) {
			case "1":
				e.setValue(new ActualItemUsageValue("xyzzbe"));
				break;
			case "4":
				e.setValue(new ActualItemUsageValue("0"));
				break;
			case "7":
				e.setValue(new ActualItemUsageValue("11000"));
				break;
			}
		});

		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "4");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("4", ItemClassification.NUMBER, 1000));
			};
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "7");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("7", ItemClassification.TIME, 1000));
			};
		};

		// when
		EquipmentUsageCreationResultTemp temp = domain.updateItemDatas(require, EquipmentDataTestHelper.CID, valuesMap);
		List<String> newValues = domain.getItemDatas().stream()
				.map(data -> data.getActualValue().map(ActualItemUsageValue::v).orElse(null)).filter(Objects::nonNull)
				.collect(Collectors.toList());

		// then
		assertThat(temp.getEquipmentData().isPresent()).isFalse();
		assertThat(temp.getErrorMap().isEmpty()).isFalse();
		assertThat(temp.getErrorMap().size()).isEqualTo(3);
		assertThat(newValues).isEqualTo(valuesBeforeChange);
	}
}
