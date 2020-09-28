package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryService;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInChargeService;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * アルゴリズム「カテゴリ選択初期表示」を実行する
 */
@Stateless
public class CategoryInitDisplayFinder {
	
	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;
	
	@Inject
	private LoginPersonInChargeService picService;
	
	@Inject
	private CategoryService categoryService;
	
	public StartupParameterDto initDisplay() {
		StartupParameterDto dto = new StartupParameterDto();
		
		/**
		 * １．ドメイン「パターン設定」を取得する
		 */
		LoginUserContext user = AppContexts.user();
		List<DataStoragePatternSetting> patterns = dataStoragePatternSettingRepository.findByContractCd(user.contractCode());
		
		/**
		 * ２．ログイン者が担当者か判断する
		 */
		LoginPersonInCharge pic = picService.getPic(user.roles());
		dto.setPic(pic);
		
		try {
			
			/**
			 * List <カテゴリマスタ>を取得する
			 */
			List<Category> master = getCategoryList(pic);
			
			/**
			 * List<カテゴリマスタ>をチェックする。
			 */
			if (!master.isEmpty()) {
				dto.setCategories(master);
				if (!patterns.isEmpty()) {
					dto.setPatterns(patterns);
				}
			} else {
				throw new BusinessException("Msg_1740");
			}
			return dto;
		} catch (Exception e) {
			return null;
		}
	}
	
	private List<Category> getCategoryList(LoginPersonInCharge pic) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<SystemType> systemTypes = picService.getSystemTypes(pic);
		return systemTypes.stream()
						.map(type -> categoryService.categoriesBySystemType(type.value))
						.flatMap(List::stream)
						.filter(distinctByKey(Category::getCategoryId))
						.collect(Collectors.toList());
	}
	
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
}
