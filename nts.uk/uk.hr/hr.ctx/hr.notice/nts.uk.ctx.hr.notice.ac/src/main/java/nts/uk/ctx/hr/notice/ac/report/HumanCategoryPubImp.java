package nts.uk.ctx.hr.notice.ac.report;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.HumanCategoryPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgDataEnumImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgShowImport;
import nts.uk.ctx.pereg.pub.person.info.ctg.IPerInfoCtgOrderByCompanyPub;
import nts.uk.ctx.pereg.pub.person.info.ctg.PerInfoCtgDataEnumExport;
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
				c.getSalaryUseAtr(), c.getPersonnelUseAtr(), c.getEmploymentUseAtr());})
				.collect(Collectors.toList())) ;
	}

}
