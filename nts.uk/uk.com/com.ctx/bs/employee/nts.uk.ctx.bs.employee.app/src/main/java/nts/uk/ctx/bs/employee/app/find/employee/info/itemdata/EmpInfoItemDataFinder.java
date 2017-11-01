package nts.uk.ctx.bs.employee.app.find.employee.info.itemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;

@Stateless
public class EmpInfoItemDataFinder {

	@Inject
	private EmpInfoItemDataRepository infoItemDataRepo;

	public List<SettingItemDto> loadInfoItemDataList(String categoryCd, String companyId, String employeeId) {
		return this.infoItemDataRepo.getAllInfoItem(categoryCd, companyId, employeeId).stream()
				.map(x -> SettingItemDto.fromInfoDataItem(x)).collect(Collectors.toList());
	}

}
