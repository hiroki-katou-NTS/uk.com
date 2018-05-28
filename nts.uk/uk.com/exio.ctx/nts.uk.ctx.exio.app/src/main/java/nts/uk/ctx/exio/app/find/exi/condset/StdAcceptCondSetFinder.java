package nts.uk.ctx.exio.app.find.exi.condset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exi.category.ExAcpCategoryDto;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCtgItemDatDto;
import nts.uk.ctx.exio.dom.exi.adapter.role.ExRoleAdapter;
import nts.uk.ctx.exio.dom.exi.adapter.role.OperableSystemImport;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.shr.com.context.AppContexts;
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

	public List<StdAcceptCondSetDto> getStdAcceptCondSetBySysType(int systemType) {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.getStdAcceptCondSetBySysType(companyId, systemType).stream()
				.map(item -> StdAcceptCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public StdAcceptCondSetDto getStdAccCondSet(int sysType, String conditionSetCd) {
		String companyId = AppContexts.user().companyId();
		Optional<StdAcceptCondSet> optDomain = stdConditionRepo.getStdAcceptCondSetById(companyId, sysType,
				conditionSetCd);
		if (optDomain.isPresent())
			return StdAcceptCondSetDto.fromDomain(optDomain.get());
		else
			return null;

	}

	public boolean isCodeExist(int systemType, String conditionCode) {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.isSettingCodeExist(companyId, systemType, conditionCode);
	}

	/**
	 * TODO: Dummies Data category => update after domain of category complete.
	 * @return
	 */
	public List<ExAcpCategoryDto> getAllCategory() {

		List<ExAcpCategoryDto> lstDataCategory = new ArrayList<ExAcpCategoryDto>();
		for (int i = 1; i <= 4; i++) {
			lstDataCategory.add(new ExAcpCategoryDto("1dfsdffs" + i, "カテゴリ名　" + i));
		}
		return lstDataCategory;
	}
	/**
	 * TODO: Dummies Data category => update after domain of category complete.
	 * @return
	 */
	public List<ExAcpCtgItemDatDto> getCategoryItemData(String categoryId) {
		List<ExAcpCtgItemDatDto> lstCategoryItemData = new ArrayList<ExAcpCtgItemDatDto>();
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j < 11; j++) {
				lstCategoryItemData
						.add(new ExAcpCtgItemDatDto("1dfsdffs" + i, j, "カテゴリ項目データ" + "" + i + "" + j, j % 2));
			}
		}

		return lstCategoryItemData.stream().filter(item -> {
			return item.getCategoryId().equals(categoryId);
		}).collect(Collectors.toList());
	}
}
