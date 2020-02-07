package nts.uk.ctx.pereg.pubimp.person.info.ctg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
		return repo.getOrderList(categoryIds, itemDefinitionIds);
	}

	@Override
	public PerInfoCtgDataEnumExport getAllPerInfoCtgHumanByCompany() {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();

		int forAttendance = NotUseAtr.NOT_USE.value;
		int forPayroll = NotUseAtr.NOT_USE.value;
		int forPersonnel = 1;

		List<PersonInfoCategory> lstCtg = perInfoCtgRepositoty.getAllCategoryForCPS007(companyId, contractCode,
				forAttendance, forPayroll, forPersonnel);

		List<String> lstCtgId = lstCtg.stream().map(c -> c.getPersonInfoCategoryId()).collect(Collectors.toList());

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
					p.getSalaryUseAtr().value, p.getPersonnelUseAtr().value, p.getEmploymentUseAtr().value, p.getIsFixed().value);

		}).filter(m -> m != null).collect(Collectors.toList());

		List<EnumConstant> historyTypes = EnumAdaptor.convertToValueNameList(HistoryTypes.class, internationalization);
		return new PerInfoCtgDataEnumExport(historyTypes, categoryList);
	}

	@Override
	public List<PerInfoCtgShowExport> getInfoCtgByCtgIdsAndCid(String cid, List<String> ctgIds) {
		List<PerInfoCtgShowExport> categoryList = perInfoCtgRepositoty.getAllByCtgId(cid, ctgIds).stream().map(p -> {
			return new PerInfoCtgShowExport(p.getPersonInfoCategoryId(), p.getCategoryName().v(),
					p.getCategoryType().value, p.getCategoryCode().v(), p.getIsAbolition().value,
					p.getCategoryParentCode().v(), p.getInitValMasterCls() == null ? 1 : p.getInitValMasterCls().value,
					p.getAddItemCls() == null ? 1 : p.getAddItemCls().value, p.isCanAbolition(),
					p.getSalaryUseAtr() == null? 0:p.getSalaryUseAtr().value, 
					p.getPersonnelUseAtr() == null? 0: p.getPersonnelUseAtr().value, 
					p.getEmploymentUseAtr() == null? 0:p.getEmploymentUseAtr().value, p.getIsFixed().value);

		}).filter(m -> m != null).collect(Collectors.toList());
		return categoryList;
	}

	@Override
	public Optional<PerInfoCtgShowExport> getInfoCtgByCtgIdAndCid(String ctgId, String contractCd) {

		Optional<PersonInfoCategory> categoryOpt = this.perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCd);
		if (categoryOpt.isPresent()) {
			PersonInfoCategory category = categoryOpt.get();
			return Optional.of(new PerInfoCtgShowExport(category.getPersonInfoCategoryId(),
					category.getCategoryName().v(), category.getCategoryType().value,
					category.getCategoryCode().v(), category.getIsAbolition().value,
					category.getCategoryParentCode().v(),
					category.getInitValMasterCls() == null ? 1 : category.getInitValMasterCls().value,
					category.getAddItemCls() == null ? 1 : category.getAddItemCls().value,
					category.isCanAbolition(),
					category.getSalaryUseAtr() == null ? 0 : category.getSalaryUseAtr().value,
					category.getPersonnelUseAtr() == null ? 0 : category.getPersonnelUseAtr().value,
					category.getEmploymentUseAtr() == null ? 0
							: category.getEmploymentUseAtr().value,
							category.getIsFixed().value		
					));
			
		}
		return Optional.empty();
	}
}
