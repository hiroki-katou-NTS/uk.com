package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.StartupParameterDto;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelService;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInChargeService;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * アルゴリズム「カテゴリ選択初期表示」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteCategoryInitDisplayFinder {

	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;

	@Inject
	private LoginPersonInChargeService picService;

	@Inject
	private CategoryForDelService categoryForDelService;

	public StartupParameterDto<DeleteCategoryDto, DataDeletionPatternSettingDto> initDisplay() {
		LoginUserContext user = AppContexts.user();
		StartupParameterDto<DeleteCategoryDto, DataDeletionPatternSettingDto> dto = new StartupParameterDto<>();

		// １．ドメインモデル「削除パターン設定」を取得する。
		List<DataDeletionPatternSetting> patterns = dataDeletionPatternSettingRepository
				.findByContractCd(user.contractCode());

		// ２．ログイン者が担当者か判断する
		LoginPersonInCharge pic = picService.getPic();
		dto.setPic(pic);

		// List <削除カテゴリマスタ>を取得する
		List<DeleteCategoryDto> categories = getCategoryList(pic).stream()
				.sorted(Comparator.comparing(DeleteCategoryDto::getCategoryId))
				.collect(Collectors.toList());

		// List<カテゴリマスタ>をチェックする。
		if (!categories.isEmpty()) {
			dto.setCategories(categories);
			if (!patterns.isEmpty()) {
				dto.setPatterns(patterns.stream().map(domain -> {
					DataDeletionPatternSettingDto patternDto = new DataDeletionPatternSettingDto();
					domain.setMemento(patternDto);
					return patternDto;
				}).collect(Collectors.toList()));
			}
		} else
			throw new BusinessException("Msg_1740");
		return dto;
	}

	private List<DeleteCategoryDto> getCategoryList(LoginPersonInCharge pic) {
		List<SystemType> systemTypes = picService.getSystemTypes(pic);
		return systemTypes.stream()
				.map(type -> categoryForDelService.categoriesBySystemType(type.value).stream()
						.map(domain -> DeleteCategoryDto.fromDomain(domain, type.value)).collect(Collectors.toList()))
				.flatMap(List::stream).collect(Collectors.toList());
	}
}
