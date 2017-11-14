package nts.uk.ctx.pereg.app.find.reginfo.additionaldata.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.LayoutPersonInfoClsFinder;
import find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.app.find.reginfo.copysetting.item.CopySetItemFinder;
import nts.uk.ctx.pereg.app.find.reginfo.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.reginfo.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.reginfo.initsetting.item.SettingItemDto;

@Stateless
public class EmpInfoItemDataFinder {

	// sonnlb start code
	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private PerInfoInitValueSettingCtgFinder initCtgSettingFinder;

	@Inject
	private InitValueSetItemFinder initItemSettingFinder;

	@Inject
	private EmpCopySettingFinder copySettingFinder;

	private CopySetItemFinder copySetItemFinder;
	// sonnlb end

	@Inject
	private EmpInfoItemDataRepository infoItemDataRepo;

	public List<SettingItemDto> loadInfoItemDataList(String categoryCd, String companyId, String employeeId) {
		return this.infoItemDataRepo.getAllInfoItem(categoryCd, companyId, employeeId).stream()
				.map(x -> SettingItemDto.fromInfoDataItem(x)).collect(Collectors.toList());
	}

}
