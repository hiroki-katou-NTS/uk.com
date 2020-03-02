package nts.uk.ctx.hr.notice.ac.report;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.HumanCategoryPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgDataEnumImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgShowImport;
import nts.uk.ctx.pereg.pub.person.info.ctg.IPerInfoCtgOrderByCompanyPub;
import nts.uk.ctx.pereg.pub.person.info.ctg.PerInfoCtgDataEnumExport;
import nts.uk.ctx.pereg.pub.person.info.ctg.PerInfoCtgShowExport;
@Stateless
public class HumanCategoryPubImp implements HumanCategoryPub{
	@Inject
	private IPerInfoCtgOrderByCompanyPub catgegoryPub;
	
	@Override
	public PerInfoCtgDataEnumImport getAllPerInfoCtgHumanByCompany() {
		PerInfoCtgDataEnumExport ctg = this.catgegoryPub.getAllPerInfoCtgHumanByCompany();
		return toConvert(ctg);
	}
	
	private PerInfoCtgDataEnumImport toConvert(PerInfoCtgDataEnumExport export) {
		return new PerInfoCtgDataEnumImport(export.getHistoryTypes(),
				export.getCategoryList().stream()
				.map(c -> {return new PerInfoCtgShowImport(c.getId(), c.getCategoryName(),
				c.getCategoryType(), c.getCategoryCode(), c.getIsAbolition(),
				c.getCategoryParentCode(), c.getInitValMasterObjCls(),
				c.getAddItemObjCls(), c.isCanAbolition(),
				c.getSalaryUseAtr(), c.getPersonnelUseAtr(), c.getEmploymentUseAtr(), c.getFixed());})
				.collect(Collectors.toList())) ;
	}

	@Override
	public List<PerInfoCtgShowImport> getInfoCtgByCtgIdsAndCid(String cid, List<String> ctgIds) {
		List<PerInfoCtgShowExport> categoryList = this.catgegoryPub.getInfoCtgByCtgIdsAndCid(cid, ctgIds);
		return categoryList.stream()
				.map(c -> {return new PerInfoCtgShowImport(c.getId(), c.getCategoryName(),
				c.getCategoryType(), c.getCategoryCode(), c.getIsAbolition(),
				c.getCategoryParentCode(), c.getInitValMasterObjCls(),
				c.getAddItemObjCls(), c.isCanAbolition(),
				c.getSalaryUseAtr(), c.getPersonnelUseAtr(), c.getEmploymentUseAtr(), c.getFixed());})
				.collect(Collectors.toList());
	}

	@Override
	public Optional<PerInfoCtgShowImport> getCategoryByCidAndContractCd(String ctgId, String contractCd) {
		Optional<PerInfoCtgShowExport> categoryOpt = this.catgegoryPub.getInfoCtgByCtgIdAndCid(ctgId, contractCd);
		if (categoryOpt.isPresent()) {
			PerInfoCtgShowExport category = categoryOpt.get();
			return Optional.of(new PerInfoCtgShowImport(category.getId(), category.getCategoryName(),
					category.getCategoryType(), category.getCategoryCode(), category.getIsAbolition(),
					category.getCategoryParentCode(), category.getInitValMasterObjCls(), category.getAddItemObjCls(),
					category.isCanAbolition(), category.getSalaryUseAtr(), category.getPersonnelUseAtr(),
					category.getEmploymentUseAtr(), category.getFixed()));
		}
		return Optional.empty();
	}

}
