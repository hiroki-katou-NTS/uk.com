package nts.uk.ctx.exio.app.find.exo.initialcasesetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SettingInitialCaseFinder {

	@Inject
	NumberDataFmSetRepository numberDataFmSetRepository;

	@Inject
	ChacDataFmSetRepository chacDataFmSetRepository;

	@Inject
	DateFormatSetRepository dateFormatSetRepository;

	@Inject
	InTimeDataFmSetRepository inTimeDataFmSetRepository;

	@Inject
	TimeDataFmSetRepository timeDataFmSetRepository;

	@Inject
	AwDataFormatSetRepository aWDataFmSetRepository;

	public List<ItemTypeDTO> getListType() {
		String cId = AppContexts.user().companyId();
		Optional<NumberDataFmSet> numberDataFmSet = numberDataFmSetRepository.getNumberDataFmSetById(cId);
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository.getChacDataFmSetById(cId);
		Optional<DateFormatSet> dateFormatSet = dateFormatSetRepository.getDateFormatSetById(cId);
		Optional<InTimeDataFmSet> inTimeDataFmSet = inTimeDataFmSetRepository.getInTimeDataFmSetById(cId);
		Optional<TimeDataFmSet> TimeDataFmSet = timeDataFmSetRepository.getTimeDataFmSetByCid(cId);
		Optional<AwDataFormatSet> AwDataFormatSet = aWDataFmSetRepository.getAwDataFormatSetById(cId);

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
			case ATWORK:
				if (AwDataFormatSet.isPresent()) {
					item.setLocalizedName("x" + item.getLocalizedName());
				}
				break;
			}
		});

		return listItemType;
	}

}
