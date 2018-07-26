package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;

/**
 * 出力項目(定型)
 */
public interface StandardOutputItemRepository {

	List<StandardOutputItem> getAllStdOutItem();

	List<StandardOutputItem> getStdOutItemByCidAndSetCd(String cid, String conditionSettingCode);

	Optional<StandardOutputItem> getStdOutItemById(String cid, String outputItemCode, String conditionSettingCode);

	void add(StandardOutputItem domain);

	void update(StandardOutputItem domain);

	void remove(String cid, String outputItemCode, String conditionSettingCode);
	
	void remove(List<StandardOutputItem> listStandardOutputItem);
	
	
	Optional<AwDataFormatSetting> getAwDataFormatSettingByID(String cid, String conditionSettingCode, String outputItemCode);
	Optional<CharacterDataFmSetting> getCharacterDataFmSettingByID(String cid, String conditionSettingCode, String outputItemCode);
	Optional<DateFormatSetting> getDateFormatSettingByID(String cid, String conditionSettingCode, String outputItemCode);
	Optional<InstantTimeDataFmSetting> getInstantTimeDataFmSettingByID(String cid, String conditionSettingCode, String outputItemCode);
	Optional<NumberDataFmSetting> getNumberDataFmSettingByID(String cid, String conditionSettingCode, String outputItemCode);
	Optional<TimeDataFmSetting> getTimeDataFmSettingByID(String cid, String conditionSettingCode, String outputItemCode);

	void add(AwDataFormatSetting domain);

	void update(AwDataFormatSetting domain);

	void add(CharacterDataFmSetting domain);

	void update(CharacterDataFmSetting domain);

	void add(DateFormatSetting domain);

	void update(DateFormatSetting domain);

	void add(InstantTimeDataFmSetting domain);

	void update(InstantTimeDataFmSetting domain);

	void add(NumberDataFmSetting domain);

	void update(NumberDataFmSetting domain);

	void add(TimeDataFmSetting domain);

	void update(TimeDataFmSetting domain);


}
