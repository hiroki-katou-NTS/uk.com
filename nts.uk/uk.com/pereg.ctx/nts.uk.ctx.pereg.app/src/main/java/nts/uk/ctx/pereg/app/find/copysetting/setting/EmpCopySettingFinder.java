package nts.uk.ctx.pereg.app.find.copysetting.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgMapDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.SettingCtgDto;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopySetting;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.system.config.InstalledProduct;

@Stateless
public class EmpCopySettingFinder {

	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Inject
	private PersonInfoCategoryAuthRepository PerInfoCtgRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	
	@Inject
	private PersonRoleRepository personRoleRepo;

	public List<SettingCtgDto> getEmpCopySetting() {

		String companyId = AppContexts.user().companyId();
		Optional<EmployeeCopySetting> employeeCopySettingOpt = this.empCopyRepo.findSetting(companyId);
		// check permission
		String roleId = AppContexts.user().roles().forPersonnel();
		if (!employeeCopySettingOpt.isPresent()) {
			if (roleId == "" || roleId == null) {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			}
		}
		
		List<SettingCtgDto> settingDtos =  this.PerInfoCtgRepo
				.getAllCategoryByCtgIdList(companyId, employeeCopySettingOpt.get().getCopySettingCategoryIdList())
				.stream().map(p -> new SettingCtgDto(p.getCategoryCode(), p.getCategoryName()))
				.collect(Collectors.toList());
		if (settingDtos.isEmpty()) {
			// check permission
			String role = AppContexts.user().roles().forPersonalInfo();
			if (role == "" || role == null) {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			}
		}
		return settingDtos;

	}

	public List<PerInfoCtgMapDto> getAllPerInfoCategoryWithCondition(String ctgName) {
		// get all perinforcategory by company id
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		List<PersonInfoCategory> lstPerInfoCtg = null;
		
		// EA修正履歴1219 - check Role lần giao hang 17.
		int forAttendance = NotUseAtr.NOT_USE.value;
		int forPayroll = NotUseAtr.NOT_USE.value;
		int forPersonnel = NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				forAttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				forPayroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				forPersonnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}
		
		
		if (ctgName.equals(""))
			lstPerInfoCtg = perInfoCtgRepositoty.getAllPerInfoCategoryNoMulAndDupHist(companyId, contractCode ,forAttendance,forPayroll,forPersonnel);
		else {
			lstPerInfoCtg = perInfoCtgRepositoty.getPerInfoCategoryByName(companyId, contractCode, ctgName);
		}
		List<PersonInfoCategory> lstFilter = new ArrayList<PersonInfoCategory>();

		// get all PersonInfoItemDefinition
		for (PersonInfoCategory obj : lstPerInfoCtg) {
			// check whether category has already copied or not
			// filter: category has items
			if (pernfoItemDefRep.countPerInfoItemDefInCategoryNo812(obj.getPersonInfoCategoryId(), companyId) > 0) {
				lstFilter.add(obj);
			}
		}
		List<PerInfoCtgMapDto> lstReturn = null;
		if (lstFilter.size() != 0) {
			lstReturn = PersonInfoCategory.getAllPerInfoCategoryWithCondition(lstFilter).stream().map(p -> {
				boolean alreadyCopy = empCopyRepo.checkPerInfoCtgAlreadyCopy(p.getPersonInfoCategoryId(), companyId);
				// boolean alreadyCopy = true;
				return new PerInfoCtgMapDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
						p.getCategoryName().v(), alreadyCopy);
			}).collect(Collectors.toList());
		}
		if (lstFilter.size() == 0 || lstReturn.size() == 0)
			throw new BusinessException("Msg_352");
		return lstReturn;
	}
}
