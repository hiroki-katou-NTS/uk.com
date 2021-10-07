package nts.uk.ctx.exio.app.find.exi.condset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCategoryDto;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCtgItemDatDto;
import nts.uk.ctx.exio.dom.exi.adapter.role.ExRoleAdapter;
import nts.uk.ctx.exio.dom.exi.adapter.role.OperableSystemImport;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.OiomtExAcpCategoryItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
/**
 * 受入条件設定（定型）
 */
public class StdAcceptCondSetFinder {

	@Inject
	private StdAcceptCondSetRepository stdConditionRepo;

	@Inject
	private ExRoleAdapter roleAdapter;
	
	@Inject
	private ExternalAcceptCategoryRepository categoryRep;
	
	@Inject
	private OiomtExAcpCategoryItemRepository categoryItemRep;

	public List<SystemTypeDto> getSystemTypes() {
		List<SystemTypeDto> result = new ArrayList<>();

		// list #325: get system code by employee id
		OperableSystemImport charge = roleAdapter.getOperableSystem();
		if (charge.isAttendance()) {
			result.add(new SystemTypeDto(SystemType.ATTENDANCE_SYSTEM.value,
					TextResource.localize("CMF001_601")));
		}
		if (charge.isHumanResource()) {
			result.add(new SystemTypeDto(SystemType.PERSON_SYSTEM.value,
					TextResource.localize("CMF001_600")));
		}
		if (charge.isOfficeHelper()) {
			result.add(new SystemTypeDto(SystemType.OFFICE_HELPER.value,
					TextResource.localize("CMF001_603")));
		}
		if (charge.isSalary()) {
			result.add(new SystemTypeDto(SystemType.PAYROLL_SYSTEM.value,
					TextResource.localize("CMF001_602")));
		}
		return result;
	}

	public List<StdAcceptCondSetDto> getAllStdAcceptCondSet() {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.getAllStdAcceptCondSet(companyId).stream()
				.map(item -> StdAcceptCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}
	
	public List<StdAcceptCondSetDto> getStdAcceptCondSetBySysType(int systemType) {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.getStdAcceptCondSetBySysType(companyId, systemType).stream()
				.map(item -> StdAcceptCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public StdAcceptCondSetDto getStdAccCondSet(String conditionSetCd) {
		String companyId = AppContexts.user().companyId();
		Optional<StdAcceptCondSet> optDomain = stdConditionRepo.getById(companyId, conditionSetCd);
		if (optDomain.isPresent())
			return StdAcceptCondSetDto.fromDomain(optDomain.get());
		else
			return null;

	}

	public boolean isCodeExist(String conditionCode) {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.isSettingCodeExist(companyId, conditionCode);
	}

	/**
	 * TODO: Dummies Data category => update after domain of category complete.
	 * @return
	 */
	public List<ExAcpCategoryDto> getCategoryBySystem(int sysType) {

		List<ExternalAcceptCategory> listCategory = categoryRep.getBySystem(EnumAdaptor.valueOf(sysType, SystemType.class),
				EnumAdaptor.valueOf(1, NotUseAtr.class));
		List<ExAcpCategoryDto> listCategoryDto = listCategory.stream().map(x -> new ExAcpCategoryDto(x.getCategoryId(), 
				x.getCategoryName(), x.getAtSysFlg().value, x.getPersSysFlg().value, x.getSalarySysFlg().value, x.getOfficeSysFlg().value))
				.collect(Collectors.toList());
		return listCategoryDto;
	}
	
	/**
	 * TODO: Dummies Data category => update after domain of category complete.
	 * @return
	 */
	public List<ExAcpCtgItemDatDto> getCategoryItemData(int categoryId) {
		
		List<ExAcpCtgItemDatDto> lstCategoryItemData = new ArrayList<ExAcpCtgItemDatDto>();
		
		List<ExternalAcceptCategoryItem> listDomain = categoryItemRep.getByCategory(categoryId);
		lstCategoryItemData.addAll(listDomain.stream().map(x -> new ExAcpCtgItemDatDto(x.getCategoryId(), x.getItemNo(), x.getItemName(), 
															x.getTableName(), x.getColumnName(), x.getDataType().value, 
															x.getAlphaUseFlg().isPresent() ? x.getAlphaUseFlg().get().value : null, 
															x.getPrimatyKeyFlg().value, x.getPrimitiveName().isPresent() ? x.getPrimitiveName().get() : null,
															x.getDecimalDigit().isPresent() ? x.getDecimalDigit().get() : null, 
															x.getDecimalUnit().isPresent() ? x.getDecimalUnit().get().value : null, 
															x.getRequiredFlg().value, x.getNumberRangeStart().isPresent() ? x.getNumberRangeStart().get() : null, 
															x.getNumberRangeEnd().isPresent() ? x.getNumberRangeEnd().get() : null, 
															x.getNumberRangeStart2().isPresent() ? x.getNumberRangeStart2().get() : null, 
															x.getNumberRangeEnd2().isPresent() ? x.getNumberRangeEnd2().get() : null, 
															x.getSpecialFlg().value, x.getRequiredNumber().isPresent() ? x.getRequiredNumber().get() : null, 
															x.getDisplayFlg().value, x.getHistoryFlg().isPresent() ? x.getHistoryFlg().get().value : null, 
															x.getHistoryContiFlg().isPresent() ? x.getHistoryContiFlg().get().value : null))
													.collect(Collectors.toList()));
		return lstCategoryItemData;
	}
	
}
