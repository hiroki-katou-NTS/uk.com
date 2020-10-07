package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CategoryInitDisplayFinder {
	
	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;
	
	@Inject
	private LoginPersonInChargeService picService;
	
	@Inject
	private CategoryService categoryService;
	
	public StartupParameterDto<CategoryDto, DataStoragePatternSettingDto> initDisplay() {
		StartupParameterDto<CategoryDto, DataStoragePatternSettingDto> dto = new StartupParameterDto<>();
		
		//１．ドメイン「パターン設定」を取得する
		LoginUserContext user = AppContexts.user();
		List<DataStoragePatternSetting> patterns = dataStoragePatternSettingRepository.findByContractCd(user.contractCode());
		
		//２．ログイン者が担当者か判断する
//		LoginPersonInCharge pic = picService.getPic();
		//FAKE-DATA
		LoginPersonInCharge pic = picService.fakePic();
		dto.setPic(pic);
			
		//List <カテゴリマスタ>を取得する
		List<CategoryDto> master = getCategoryList(pic).stream()
									.sorted(Comparator.comparing(CategoryDto::getCategoryId))
									.collect(Collectors.toList());
		
		//List<カテゴリマスタ>をチェックする。
		if (!master.isEmpty()) {
			dto.setCategories(master);
			if (!patterns.isEmpty()) {
				dto.setPatterns(patterns.stream().map(p -> {
					DataStoragePatternSettingDto d = new DataStoragePatternSettingDto();
					p.setMemento(d);
					return d;
				}).collect(Collectors.toList()));
			}
			return dto;
		} else {
			throw new BusinessException("Msg_1740");
		}
	}
	
	private List<CategoryDto> getCategoryList(LoginPersonInCharge pic) {
		List<SystemType> systemTypes = picService.getSystemTypes(pic);
		return systemTypes.stream()
						.map(type -> categoryService.categoriesBySystemType(type.value))
						.flatMap(List::stream)
						.map(CategoryDto::fromDomain)
						.filter(distinctByKey(CategoryDto::getCategoryId))
						.collect(Collectors.toList());
	}
	
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
}
