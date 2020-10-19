package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.FindDelSelectedPatternCommand;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategory;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SelectedDelPatternFinder {
	
	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;
	
	@Inject
	private DataDeletionSelectionCategoryRepository dataDeletionSelectionCategoryRepository;
	
	@Inject
	private CategoryRepository categoryRepository;
	
	public SelectedDelPatternParameterDto findSelectedPattern(FindDelSelectedPatternCommand command) {
		SelectedDelPatternParameterDto dto = new SelectedDelPatternParameterDto();
		//オブジェクト「選択カテゴリ」を取得する
		List<DataDeletionSelectionCategory> selectCategories = dataDeletionSelectionCategoryRepository
				.findByPatternCdAndPatternAtrAndSystemTypes(
						command.getPatternCode(), 
						command.getPatternClassification(), 
						command.getCategories().stream().map(DeleteCategoryDto::getSystemType).collect(Collectors.toList()));
		//ドメインモデル「カテゴリ」を取得する
		List<Category> categories = categoryRepository.getCategoryByListId(selectCategories.stream()
																			.map(c -> c.getCategoryId().v())
																			.collect(Collectors.toList()));
		Optional<DataDeletionPatternSetting> op = dataDeletionPatternSettingRepository
				.findByPk(
						AppContexts.user().contractCode(),
						command.getPatternCode(),
						command.getPatternClassification());
		
		//List<選択カテゴリ名称＞を作成
		dto.setSelectedCategories(selectCategories.stream()
				.map(sc -> {
					SelectionDelCategoryNameDto obj = new SelectionDelCategoryNameDto();
					obj.setCategoryId(sc.getCategoryId().v());
					obj.setCategoryName(command.getCategories().stream()
							.filter(u -> u.getCategoryId().equals(sc.getCategoryId().v()))
							.filter(u -> u.getSystemType() == sc.getSystemType().value)
							.findFirst()
							.map(DeleteCategoryDto::getCategoryName)
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
						DataDeletionPatternSettingDto patternDto = new DataDeletionPatternSettingDto();
						pattern.setMemento(patternDto);
						obj.setPattern(patternDto);
					});
					return obj;
				})
				.sorted(Comparator.comparing(SelectionDelCategoryNameDto::getCategoryId))
				.collect(Collectors.toList()));
		
		//List<選択可能カテゴリ＞を作成
		dto.setSelectableCategories(getSelectable(dto.getSelectedCategories(), command.getCategories()));
		//オブジェクト「選択パターンパラメータ」を返す。
		return dto;
	}
	
	private List<DeleteCategoryDto> getSelectable(List<SelectionDelCategoryNameDto> selected, List<DeleteCategoryDto> categories) {
		return categories.stream()
				.filter(c -> !selected.stream().anyMatch(s -> s.getCategoryId().equals(c.getCategoryId())
															&& s.getSystemType() == c.getSystemType()))
				.collect(Collectors.toList());
	}
}
