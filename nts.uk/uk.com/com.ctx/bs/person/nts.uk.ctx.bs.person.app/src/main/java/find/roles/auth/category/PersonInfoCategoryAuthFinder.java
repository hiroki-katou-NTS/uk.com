package find.roles.auth.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryDetail;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonInfoCategoryAuthFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoCategoryAuthFinder {
	@Inject
	private PersonInfoCategoryAuthRepository personCategoryAuthRepository;

	@Inject
	private PersonInfoRoleAuthRepository personRoleAuthRepository;

	public List<PersonInfoCategoryDetailDto> getAllCategory(String roleId) {
		return this.personCategoryAuthRepository
				.getAllCategory(roleId, AppContexts.user().contractCode(), AppContexts.user().companyId()).stream()
				.map(x -> PersonInfoCategoryDetailDto.fromDomain(x)).collect(Collectors.toList());

	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId) {
		Optional<PersonInfoCategoryAuth> opt = this.personCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, personCategoryAuthId);
		if (!opt.isPresent())
			return null;
		return opt.map(c -> PersonInfoCategoryAuthDto.fromDomain(c)).get();

	}

	public List<PersonInfoCategoryDetail> getAllCategoryByRoleId(String roleId) {
		List<PersonInfoCategoryDetail> allCategory = new ArrayList<>();
		List<PersonInfoCategoryDetail> categoryIdList = this.personCategoryAuthRepository.getAllCategoryInfo();
		if (categoryIdList.size() > 0) {
			categoryIdList.stream().forEach(c -> {
				if (c.getAllowOtherRef() == 1 || c.getAllowPersonRef() == 1) {
					c.setAllowOtherRef(0);
					c.setAllowPersonRef(0);
					allCategory.add(c);
				}
			});

		}
		PersonInfoRoleAuth roleAuth = this.personRoleAuthRepository.getDetailPersonRoleAuth(roleId).orElse(null);
		if (roleAuth != null) {
			List<PersonInfoCategoryDetail> categoryInfoList = this.personCategoryAuthRepository
					.getAllCategoryByRoleId(roleId);
			allCategory.addAll(categoryInfoList);
		}
		if (allCategory.size() <= 0) {
			throw new BusinessException("Msg34");
		}
		return allCategory.stream().filter(distinctByKey(c -> c.getCategoryId())).collect(Collectors.toList());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
