/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.employment.dto.CommonMaterItemDto;
import nts.uk.ctx.bs.employee.app.find.employment.dto.EmploymentDto;
import nts.uk.ctx.bs.employee.app.find.employment.dto.EmploymentFindDto;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterExportDto;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.IGroupCommonMaster;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class DefaultEmploymentFinder.
 */
@Stateless
public class DefaultEmploymentFinder implements EmploymentFinder {

	/** The repository. */
	@Inject
	private EmploymentRepository repository;

	@Inject
	private IGroupCommonMaster groupCommonMaster;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.find.employment.EmploymentFinder#findAll()
	 */
	public List<EmploymentDto> findAll() {

		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();

		// Get Company Id
		String companyId = loginUserContext.companyId();

		// Get All Employment
		List<Employment> empList = this.repository.findAll(companyId);

		// Save to Memento
		return empList.stream().map(employment -> {
			EmploymentDto dto = new EmploymentDto();
			dto.setCode(employment.getEmploymentCode().v());
			dto.setName(employment.getEmploymentName().v());
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.find.employment.EmploymentFinder#findByCode(java.lang.String)
	 */
	@Override
	public EmploymentFindDto findByCode(String employmentCode) {
		String companyId = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		EmploymentFindDto dto = new EmploymentFindDto();
		Optional<Employment> employment = this.repository.findEmployment(companyId, employmentCode);
		if (!employment.isPresent()) {
			return null;
		}
		dto.setCode(employmentCode);
		dto.setName(employment.get().getEmploymentName().v());
		dto.setEmpExternalCode(employment.get().getEmpExternalCode());
		dto.setMemo(employment.get().getMemo());

		int x = 1;
		if (x == 0) {
			dto.setShowsGroupCompany(false);
			return dto;
		}
		// アルゴリズム「使用している共通マスタの取得」を実行する --- (thực hiện thuật toán [lấy
		// CommonMaster đang sử dụng])
		/*
		 * [Input] ・契約コード//(contract code) ・共通マスタID = M000031//(common master
		 * ID=M000031) ・会社ID//(company ID) ・基準日 = システム日付//(baseDate= System
		 * Date)
		 */
		GroupCommonMasterExportDto data = groupCommonMaster.getGroupCommonMasterEnableItem(contractCd, "M000031", companyId,
				GeneralDate.today());
		if (data.getCommonMasterItems().isEmpty()) {
			dto.setErrMessage("Msg_1580");
			return dto;
		}
		if(employment.get().getEmpCommonMasterItemId().isPresent()){
			dto.setEmpCommonMasterItemId(employment.get().getEmpCommonMasterItemId().get());
		}
		dto.setShowsGroupCompany(true);
		dto.setCommonMasterName(data.getCommonMasterName());
		dto.setCommonMasterItems(data.getCommonMasterItems().stream().map(
				item -> new CommonMaterItemDto(item.getCommonMasterItemCode().v(), item.getCommonMasterItemName().v()))
				.collect(Collectors.toList()));
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.app.find.employment.EmploymentFinder#findByCodes(
	 * java.util.List)
	 */
	@Override
	public List<EmploymentDto> findByCodes(List<String> empCodes) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// Get All Employment
		List<Employment> empList = this.repository.findByEmpCodes(companyId, empCodes);

		// Save to Memento
		return empList.stream().map(employment -> {
			EmploymentDto dto = new EmploymentDto();
			dto.setCode(employment.getEmploymentCode().v());
			dto.setName(employment.getEmploymentName().v());
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.app.find.employment.EmploymentFinder#
	 * findByCodesWithNull(java.util.List)
	 */
	@Override
	public List<EmploymentDto> findByCodesWithNull(List<String> empCodes) {
		// Get Company Id

		List<EmploymentDto> result = new ArrayList<EmploymentDto>();

		String companyId = AppContexts.user().companyId();
		if (CollectionUtil.isEmpty(empCodes)) {
			return result;
		}

		empCodes.forEach(code -> {
			Optional<Employment> optEmp = this.repository.findEmployment(companyId, code);
			String itemName;
			if (optEmp.isPresent()) {
				itemName = optEmp.get().getEmploymentName().v();
			} else {
				itemName = code + I18NText.getText("KMF004_163");
			}

			result.add(new EmploymentDto(code, itemName));
		});
		return result;
	}

}
