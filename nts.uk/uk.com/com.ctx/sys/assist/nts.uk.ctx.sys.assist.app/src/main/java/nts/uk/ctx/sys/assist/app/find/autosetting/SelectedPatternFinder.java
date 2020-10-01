package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.autosetting.FindSelectedPatternCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.SelectionCategoryNameDto;
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
		//オブジェクト「選択カテゴリ」を取得する
		List<DataStorageSelectionCategory> selectCategories = dataStorageSelectionCategoryRepository
				.findByPatternCdAndPatternAtrAndSystemTypes(
						command.getPatternCode(), 
						command.getPatternClassification(), 
						command.getCategories().stream().map(CategoryDto::getSystemType).collect(Collectors.toList()));
		//ドメインモデル「カテゴリ」を取得する
		List<Category> categories = categoryRepository.getCategoryByListId(selectCategories.stream()
																			.map(c -> c.getCategoryId().v())
																			.collect(Collectors.toList()));
		Optional<DataStoragePatternSetting> op = dataStoragePatternSettingRepository
				.findByContractCdAndPatternCdAndPatternAtr(
						AppContexts.user().contractCode(),
						command.getPatternCode(),
						command.getPatternClassification());
		
		//List<選択カテゴリ名称＞を作成
		dto.setSelectedCategories(selectCategories.stream()
				.map(sc -> {
					SelectionCategoryNameDto obj = new SelectionCategoryNameDto();
					obj.setCategoryId(sc.getCategoryId().v());
					obj.setCategoryName(command.getCategories().stream()
							.filter(u -> u.getCategoryId().equals(sc.getCategoryId().v()))
							.filter(u -> u.getSystemType() == sc.getSystemType().value)
							.findFirst()
							.map(CategoryDto::getCategoryName)
							.orElse(null));
					obj.setPatternClassification(sc.getPatternClassification().value);
					obj.setPatternCode(sc.getPatternCode().v());
					obj.setRetentionPeriod(categories.stream()
							.filter(u -> u.getCategoryId().v().equals(sc.getCategoryId().v()))
							.findFirst()
							.map(c -> c.getTimeStore().nameId)
							.get());
					obj.setSystemType(sc.getSystemType().value);
					op.ifPresent(pattern -> {
						DataStoragePatternSettingDto patternDto = new DataStoragePatternSettingDto();
						pattern.setMemento(patternDto);
						obj.setPattern(patternDto);
					});
					return obj;
				})
				.sorted(Comparator.comparing(SelectionCategoryNameDto::getCategoryId))
				.collect(Collectors.toList()));
		
		//List<選択可能カテゴリ＞を作成
		List<String> ids = selectCategories.stream()
				   .map(sc -> sc.getCategoryId().v())
				   .collect(Collectors.toList());
		dto.setSelectableCategories(command.getCategories().stream()
									.filter(c -> !ids.contains(c.getCategoryId()))
									.sorted(Comparator.comparing(CategoryDto::getCategoryId))
									.collect(Collectors.toList()));
		//オブジェクト「選択パターンパラメータ」を返す。
		return dto;
	}
}
