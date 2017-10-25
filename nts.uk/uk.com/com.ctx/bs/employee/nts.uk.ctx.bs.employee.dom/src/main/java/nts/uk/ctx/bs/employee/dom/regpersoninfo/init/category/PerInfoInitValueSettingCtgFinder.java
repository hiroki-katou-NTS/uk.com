package nts.uk.ctx.bs.employee.dom.regpersoninfo.init.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;

/**
 * The class PerInfoInitValueSettingCtgFinder
 * 
 * @author sonnlb
 *
 */
@Stateless
public class PerInfoInitValueSettingCtgFinder {

	@Inject
	private PerInfoInitValSetCtgRepository settingCtgRepo;


	public List<InitCtgDto> getAllCategoryBySetId(String settingId) {

		List<InitCtgDto> settingList;
		settingList = this.settingCtgRepo.getAllCategoryBySetId(settingId).stream().map(c -> InitCtgDto.fromDomain(c))
				.collect(Collectors.toList());

		return settingList;

	}

}
