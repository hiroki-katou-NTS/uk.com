package nts.uk.ctx.at.record.ac.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.StatusOfEmployeeExport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.bs.employee.pub.company.AffCompanyHistExport;
import nts.uk.ctx.bs.employee.pub.company.SyCompanyPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SyCompanyRecordAdapterImpl implements SyCompanyRecordAdapter {

	@Inject
	private SyCompanyPub syCompanyPub;
	
	//private ICompanyPub companyPub;
	
	

	@Override
	public List<AffCompanyHistImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod) {
		List<AffCompanyHistImport> importList = this.syCompanyPub.GetAffCompanyHistByEmployee(sids, datePeriod)
				.stream()
				.map(x -> convert(x)).collect(Collectors.toList());
		return importList;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AffCompanyHistImport> getAffCompanyHistByEmployeeRequire(CacheCarrier cacheCarrier, List<String> sids, DatePeriod datePeriod) {
		List<AffCompanyHistImport> importList = this.syCompanyPub.GetAffCompanyHistByEmployeeRequire(cacheCarrier, sids, datePeriod)
				.stream()
				.map(x -> convert(x)).collect(Collectors.toList());
		return importList;
	}

	private AffCompanyHistImport convert(AffCompanyHistExport dataExpprt) {
		List<AffComHistItemImport> subListImport = dataExpprt.getLstAffComHistItem().stream()
				.map(x -> new AffComHistItemImport(x.getHistoryId(), x.isDestinationData(), x.getDatePeriod()))
				.collect(Collectors.toList());
		return new AffCompanyHistImport(dataExpprt.getEmployeeId(), subListImport);
	}

	@Override
	public List<StatusOfEmployeeExport> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod) {
		return syCompanyPub.GetListAffComHistByListSidAndPeriod(sid, datePeriod).stream()
				.map(x -> new StatusOfEmployeeExport(x.getEmployeeId(), x.getListPeriod()))
				.collect(Collectors.toList());
	}

//	@Override
//	public List<CompanyImportForKDP003> get(String contractCd, Optional<String> cid,Boolean isAbolition) {
//		List<CompanyImportForKDP003> result = new ArrayList<>();
//		List<CompanyExportForKDP003> importCom = companyPub.get(contractCd, cid, isAbolition);
//		if (importCom.isEmpty()) {
//			return result;
//		}
//		return importCom.stream().map(item -> new CompanyImportForKDP003(item.getCompanyCode(),item.getCompanyName(), item.getCompanyId(), item.getContractCd()))
//				.collect(Collectors.toList());
//	}

}
