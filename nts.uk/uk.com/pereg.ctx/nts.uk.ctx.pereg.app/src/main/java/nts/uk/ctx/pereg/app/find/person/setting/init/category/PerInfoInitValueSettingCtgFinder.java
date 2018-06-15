package nts.uk.ctx.pereg.app.find.person.setting.init.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValueSettingCtg;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
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
		int salary = AppContexts.user().roles().forPayroll() != null ? 1 : 0;
		int personnel = AppContexts.user().roles().forPersonnel() != null ? 1 : 0;
		int employee = AppContexts.user().roles().forAttendance() != null ? 1 : 0;

		List<PerInfoInitValueSettingCtg> ctgLst = this.settingCtgRepo.getAllCategory(companyId, settingId, salary, personnel, employee);
		List<String> ctgId = ctgLst.stream().map(c ->  c.getPerInfoCtgId())
				.collect(Collectors.toList());
		List<String> ctgFilter = this.settingItemRepo.isExistItem(ctgId);
		
		List<PerInfoInitValueSettingCtg>  ctgList = new ArrayList<>();
		ctgLst.stream().forEach(c -> {
			ctgFilter.stream().forEach(  filter -> {
				if(c.getPerInfoCtgId().equals(filter)) {
					ctgList.add(c);
				}
			});
			
		});
		
		if (ctgList.size() > 0) {

			return ctgList.stream().map(c -> {
				PerInfoInitValueSettingCtg ctg = new PerInfoInitValueSettingCtg();
				ctg.setCategoryName(c.getCategoryName());
				ctg.setPerInfoCtgId(c.getPerInfoCtgId());
				ctg.setCategoryType(c.getCategoryType());
				
				ctg.setSetting(this.settingItemRepo.isExist(settingId, c.getPerInfoCtgId()));
				return ctg;

			}).collect(Collectors.toList());
		}

		return ctgList;
	}

	// sonnlb code start
	public List<SettingCtgDto> getAllCategoryBySetId(String settingId) {

		List<SettingCtgDto> settingList;
		settingList = this.settingCtgRepo.getAllCategoryBySetId(settingId).stream()
				.map(c -> SettingCtgDto.fromDomain(c)).collect(Collectors.toList());

		return settingList;

	}

	// sonnlb code end

}
