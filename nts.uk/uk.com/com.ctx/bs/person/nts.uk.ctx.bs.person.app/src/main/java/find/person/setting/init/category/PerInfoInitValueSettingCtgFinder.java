package find.person.setting.init.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValueSettingCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class PerInfoInitValueSettingCtgFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PerInfoInitValueSettingCtgFinder {

	@Inject
	private PerInfoInitValSetCtgRepository settingCtgRepo;

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	public List<PerInfoInitValueSettingCtg> getAllCategory(String settingId) {
		String companyId = AppContexts.user().companyId();

		List<PerInfoInitValueSettingCtg> ctgLst = this.settingCtgRepo.getAllCategory(companyId, settingId);

		if (ctgLst.size() > 0) {

			return ctgLst.stream().filter(c -> this.settingItemRepo.isExist(c.getPerInfoCtgId()))
					.collect(Collectors.toList());
		}

		return ctgLst;
	}

	// sonnlb

	public List<PerInfoInitValueSettingCtgDto> getAllCategoryBySetId(String settingId) {

		List<PerInfoInitValueSettingCtgDto> settingList = this.settingCtgRepo.getAllCategoryBySetId(settingId).stream()
				.map(c -> PerInfoInitValueSettingCtgDto.fromDomain(c)).collect(Collectors.toList());

		return settingList;

	}

	// sonnlb

}
