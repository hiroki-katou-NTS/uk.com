package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.autosetting.storage.FindSelectedPatternCommand;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * アルゴリズム「パターン設定選択」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SelectedPatternFinder {

	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;

	@Inject
	private DataStorageSelectionCategoryRepository dataStorageSelectionCategoryRepository;

	@Inject
	private CategoryRepository categoryRepository;

	public SelectedPatternParameterDto findSelectedPattern(FindSelectedPatternCommand command) {
		SelectedPatternParameterDto dto = new SelectedPatternParameterDto();
		// オブジェクト「選択カテゴリ」を取得する
		List<DataStorageSelectionCategory> selectCategories = dataStorageSelectionCategoryRepository
				.findByPatternCdAndPatternAtrAndSystemTypes(command.getPatternCode(),
						command.getPatternClassification(), command.getCategories().stream()
								.map(CategoryDto::getSystemType).distinct().collect(Collectors.toList()),
						AppContexts.user().contractCode());
		// ドメインモデル「カテゴリ」を取得する
		List<Category> categories = categoryRepository.getCategoryByListId(
				selectCategories.stream().map(c -> c.getCategoryId().v()).collect(Collectors.toList()));
		Optional<DataStoragePatternSetting> op = dataStoragePatternSettingRepository
				.findByContractCdAndPatternCdAndPatternAtr(AppContexts.user().contractCode(), command.getPatternCode(),
						command.getPatternClassification());

		// List<選択カテゴリ名称＞を作成
		if (op.isPresent()) {
			DataStoragePatternSetting pattern = op.get();
			dto.setPattern(DataStoragePatternSettingDto.createFromDomain(pattern));
			dto.getPattern().setSelectCategories(selectCategories.stream().map(sc -> {
				return categories.stream().filter(u -> u.getCategoryId().equals(sc.getCategoryId()))
						.findFirst()
						.map(category -> SelectionCategoryNameDto.builder()
							.categoryId(sc.getCategoryId().v())
							.categoryName(category.getCategoryName().v())
							.retentionPeriod(category.getTimeStore().nameId)
							.systemType(sc.getSystemType().value)
							.build())
						.orElse(null);
			}).filter(Objects::nonNull).collect(Collectors.toList()));
		}

		// List<選択可能カテゴリ＞を作成
		dto.setSelectableCategories(getSelectable(dto.getPattern().getSelectCategories(), command.getCategories()));
		// オブジェクト「選択パターンパラメータ」を返す。
		return dto;
	}

	private List<CategoryDto> getSelectable(List<SelectionCategoryNameDto> selected, List<CategoryDto> categories) {
		return categories.stream()
				.filter(c -> !selected.stream().anyMatch(
						s -> s.getCategoryId().equals(c.getCategoryId()) && s.getSystemType() == c.getSystemType()))
				.collect(Collectors.toList());
	}
}
