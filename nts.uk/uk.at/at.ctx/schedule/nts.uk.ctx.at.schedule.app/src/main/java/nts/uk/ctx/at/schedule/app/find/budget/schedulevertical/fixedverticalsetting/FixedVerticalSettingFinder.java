package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.custom.IInternationalization;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedItemAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalDetailedSettings;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.UseAtr;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class FixedVerticalSettingFinder {
	@Inject
	private FixedVerticalSettingRepository repository;
	@Inject
	private IInternationalization internationalization;
	
	/**
	 * Find Fixed Vertical by companyId
	 * @return
	 */
	public List<FixedVerticalSettingDto> findByCid() {
		List<FixedVerticalSettingDto> result = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<FixedVertical> fixedVerticalItems =  repository.findAll(companyId);
		Map<Integer, FixedVertical> fixedVerticalItemMap = fixedVerticalItems.stream().collect(Collectors.toMap(x-> {
			return x.getFixedItemAtr().value;
		}, Function.identity()));
		
		FixedItemAtr[] fixedItemAtrs = FixedItemAtr.values();
		for (FixedItemAtr item : fixedItemAtrs) {
			String paramName = item.paramNameId;
			if (!StringUtil.isNullOrEmpty(item.paramNameId, true)) {
				paramName = internationalization.getItemName(item.paramNameId).get();
			} 
			String name = internationalization.getItemName(item.nameId, paramName).get();
			FixedVerticalSettingDto verticalDto = new FixedVerticalSettingDto(companyId, item.value, name, UseAtr.DO_NOT_USE.value,  VerticalDetailedSettings.DO_NOT_USE.value);
			FixedVertical fixedVerticalDb = fixedVerticalItemMap.get(item.value);
			if (fixedVerticalDb != null) {
				verticalDto.setUseAtr(fixedVerticalDb.getUseAtr().value);
				verticalDto.setVerticalDetailedSettings(fixedVerticalDb.getVerticalDetailedSettings().value);
			}
			result.add(verticalDto);
		}
		
		return result;
	}

	private FixedVerticalSettingDto convertToDbType(FixedVertical fixedVertical) {
		FixedVerticalSettingDto verticalSettingDto = new FixedVerticalSettingDto();
		verticalSettingDto.setCompanyId(fixedVertical.getCompanyId());
		verticalSettingDto.setFixedItemAtr(fixedVertical.getFixedItemAtr().value);
		verticalSettingDto.setUseAtr(fixedVertical.getUseAtr().value);
		verticalSettingDto.setVerticalDetailedSettings(fixedVertical.getVerticalDetailedSettings().value);

		return verticalSettingDto;
	}
}
