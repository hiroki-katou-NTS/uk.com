package nts.uk.ctx.pereg.pubimp.person.info.ctg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.info.category.HistoryTypes;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.pub.person.info.ctg.IPerInfoCtgOrderByCompanyPub;
import nts.uk.ctx.pereg.pub.person.info.ctg.PerInfoCtgDataEnumExport;
import nts.uk.ctx.pereg.pub.person.info.ctg.PerInfoCtgShowExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class PerInfoCtgOrderByCompanyPubImpl implements IPerInfoCtgOrderByCompanyPub {

	@Inject
	private PerInfoCtgByCompanyRepositoty repo;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;
	
	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	

	@Inject
	I18NResourcesForUK internationalization;
	
	@Override
	public HashMap<Integer, HashMap<String, Integer>> getOrderList(List<String> categoryIds,
			List<String> itemDefinitionIds) {
		// TODO Auto-generated method stub
		return repo.getOrderList(categoryIds, itemDefinitionIds);
	}

	@Override
	public PerInfoCtgDataEnumExport getAllPerInfoCtgHumanByCompany() {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();

		int forAttendance = NotUseAtr.NOT_USE.value;
		int forPayroll = NotUseAtr.NOT_USE.value;
		int forPersonnel = 1;
//		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
//		for (InstalledProduct productType : installProduct) {
//			switch (productType.getProductType()) {
//			case ATTENDANCE:
//				forAttendance = NotUseAtr.USE.value;
//				break;
//			case PAYROLL:
//				forPayroll = NotUseAtr.USE.value;
//				break;
//			case PERSONNEL:
//				forPersonnel = NotUseAtr.USE.value;
//				break;
//			default:
//				break;
//			}
//		}

		List<PersonInfoCategory> lstCtg = perInfoCtgRepositoty.getAllCategoryForCPS007(companyId, contractCode,
				forAttendance, forPayroll, forPersonnel);

		List<String> lstCtgId = lstCtg.stream().map(c -> c.getPersonInfoCategoryId()).collect(Collectors.toList());

//		Map<String, PersonInfoCategory> mapCtgAndId = lstCtg.stream()
//				.collect(Collectors.toMap(e -> e.getPersonInfoCategoryId(), e -> e));

		Map<String, List<Object[]>> mapCategoryIdAndLstItemDf =  this.pernfoItemDefRep.getAllPerInfoItemDefByListCategoryId(lstCtgId, AppContexts.user().contractCode());

		List<PerInfoCtgShowExport> categoryList = lstCtg.stream().map(p -> {
			
			List<Object[]> lstItemDfGroupByCtgId = mapCategoryIdAndLstItemDf.get(p.getPersonInfoCategoryId());
			
			if (CollectionUtil.isEmpty(lstItemDfGroupByCtgId)) {
				return null;
			}
			
			return new PerInfoCtgShowExport(p.getPersonInfoCategoryId(), p.getCategoryName().v(),
					p.getCategoryType().value, p.getCategoryCode().v(), p.getIsAbolition().value,
					p.getCategoryParentCode().v(), p.getInitValMasterCls() == null ? 1 : p.getInitValMasterCls().value,
					p.getAddItemCls() == null ? 1 : p.getAddItemCls().value, p.isCanAbolition(),
					p.getSalaryUseAtr().value, p.getPersonnelUseAtr().value, p.getEmploymentUseAtr().value);

		}).filter(m -> m != null).collect(Collectors.toList());

		List<EnumConstant> historyTypes = EnumAdaptor.convertToValueNameList(HistoryTypes.class, internationalization);
		return new PerInfoCtgDataEnumExport(historyTypes, categoryList);
	}
}
