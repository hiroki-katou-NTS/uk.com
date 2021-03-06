package nts.uk.ctx.pereg.app.find.roles.auth.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryDetail;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.system.config.InstalledProduct;

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
	private PerInfoItemDefRepositoty itemInfoRepo;

	public List<PersonInfoCategoryDetailDto> getAllCategory(String roleId) {
		String contractCd = AppContexts.user().contractCode();
		int payroll = NotUseAtr.NOT_USE.value;
		int personnel = NotUseAtr.NOT_USE.value;
		int atttendance = NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				atttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				payroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				personnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}
		List<PersonInfoCategoryDetail> ctgSourceLst = this.personCategoryAuthRepository.getAllCategory(roleId,
				AppContexts.user().contractCode(), AppContexts.user().companyId(), payroll, personnel, atttendance);
		List<String> ctgLstId = ctgSourceLst.stream().map(c -> {
			return c.getCategoryId();
		}).collect(Collectors.toList());

		Map<String, List<Object[]>> itemByCtgId = this.itemInfoRepo.getAllPerInfoItemDefByListCategoryId(ctgLstId,
				contractCd);

		List<PersonInfoCategoryDetailDto> ctgResultLst = new ArrayList<>();
		for (PersonInfoCategoryDetail i : ctgSourceLst) {
			List<Object[]> item = itemByCtgId.get(i.getCategoryId());
			if (item == null)
				continue;
			if (item.size() > 0)
				ctgResultLst.add(PersonInfoCategoryDetailDto.fromDomain(i));
		}
		return ctgResultLst;
	}

	public List<PersonInfoCategoryAuthDto> getAllCategoryAuth(String roleId) {
		return this.personCategoryAuthRepository.getAllCategoryAuthByRoleId(roleId).stream()
				.map(x -> PersonInfoCategoryAuthDto.fromDomain(x)).collect(Collectors.toList());

	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId) {
		Optional<PersonInfoCategoryAuth> opt = this.personCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, personCategoryAuthId);
		if (!opt.isPresent())
			return null;
		return opt.map(c -> PersonInfoCategoryAuthDto.fromDomain(c)).get();

	}

}
