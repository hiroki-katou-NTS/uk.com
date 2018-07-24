package nts.uk.ctx.exio.app.find.exo.initialcasesetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SettingInitialCaseFinder {

	@Inject
	DataFormatSettingRepository dataFormatSettingRepository;

	public List<ItemTypeDTO> getListType() {
		String cId = AppContexts.user().companyId();
		Optional<NumberDataFmSet> numberDataFmSet = dataFormatSettingRepository.getNumberDataFmSetById(cId);
		Optional<ChacDataFmSet> chacDataFmSet = dataFormatSettingRepository.getChacDataFmSetById(cId);
		Optional<DateFormatSet> dateFormatSet = dataFormatSettingRepository.getDateFormatSetById(cId);
		Optional<InTimeDataFmSet> inTimeDataFmSet = dataFormatSettingRepository.getInTimeDataFmSetById(cId);
		Optional<TimeDataFmSet> TimeDataFmSet = dataFormatSettingRepository.getTimeDataFmSetByCid(cId);
		Optional<AwDataFormatSet> AwDataFormatSet = dataFormatSettingRepository.getAwDataFormatSetById(cId);

		List<ItemTypeDTO> listItemType = EnumAdaptor.convertToValueNameList(ItemType.class).stream()
				.map(x -> new ItemTypeDTO(x.getValue(), x.getLocalizedName())).collect(Collectors.toList());
		listItemType.forEach(item -> {
			ItemType itemType = EnumAdaptor.valueOf(item.getValue(), ItemType.class);
			switch (itemType) {
			case NUMERIC:
				if (numberDataFmSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			case CHARACTER:
				if (chacDataFmSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			case DATE:
				if (dateFormatSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			case INS_TIME:
				if (inTimeDataFmSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			case TIME:
				if (TimeDataFmSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			case AT_WORK_CLS:
				if (AwDataFormatSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			}
		});

		return listItemType;
	}

}
