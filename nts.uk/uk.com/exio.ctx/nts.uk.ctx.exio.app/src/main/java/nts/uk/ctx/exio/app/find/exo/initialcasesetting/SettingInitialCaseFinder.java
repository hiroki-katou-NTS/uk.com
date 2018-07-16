package nts.uk.ctx.exio.app.find.exo.initialcasesetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.DateFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.InTimeDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.NumberDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSetRepository;
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
	NumberDataFmSetRepository aWDataFmSetRepository;

	public List<Integer> getListType() {
		List<Integer> result = new ArrayList<>();
		String cId = AppContexts.user().companyId();
		numberDataFmSetRepository.getNumberDataFmSetById().ifPresent(x -> {
			result.add(0);
		});
		chacDataFmSetRepository.getChacDataFmSetById(cId).ifPresent(x -> {
			result.add(1);
		});
		dateFormatSetRepository.getDateFormatSetById().ifPresent(x -> {
			result.add(2);
		});
		inTimeDataFmSetRepository.getInTimeDataFmSetById().ifPresent(x -> {
			result.add(3);
		});
		timeDataFmSetRepository.getTimeDataFmSetByCid(cId).ifPresent(x -> {
			result.add(4);
		});
		aWDataFmSetRepository.getNumberDataFmSetById().ifPresent(x -> {
			result.add(5);
		});
		return result;
	}

}
