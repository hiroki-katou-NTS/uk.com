package nts.uk.ctx.bs.employee.app.find.employee.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AffCompanyHistInfoFinder implements PeregFinder<AffCompanyHistInfoDto> {
	@Inject
	AffCompanyHistRepository achFinder;

	@Inject
	AffCompanyInfoRepository aciFinder;

	@Override
	public String targetCategoryCode() {
		return "CS00003";
	}

	@Override
	public Class<AffCompanyHistInfoDto> dtoClass() {
		return AffCompanyHistInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public AffCompanyHistInfoDto getSingleData(PeregQuery query) {
		if (query.getInfoId() == null) {
			AffCompanyHist domain = achFinder.getAffCompanyHistoryOfEmployeeAndBaseDate(query.getEmployeeId(),
					query.getStandardDate());
			if (domain == null) {
				return null;
			}

			AffCompanyHistByEmployee empHist = domain.getLstAffCompanyHistByEmployee().get(0);
			if (empHist == null) {
				return null;
			}

			AffCompanyHistItem histItem = empHist.getLstAffCompanyHistoryItem().get(0);

			if (histItem == null) {
				return null;
			}

			AffCompanyInfo info = aciFinder.getAffCompanyInfoByHistId(histItem.getHistoryId());

			return AffCompanyHistInfoDto.fromDomain(domain, info);
		} else {
			AffCompanyInfo info = aciFinder.getAffCompanyInfoByHistId(query.getInfoId());

			AffCompanyHist domain = achFinder.getAffCompanyHistoryOfHistInfo(query.getInfoId());

			return AffCompanyHistInfoDto.fromDomain(domain, info);
		}
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {

		AffCompanyHist affCompanyHist = achFinder.getAffCompanyHistoryOfEmployeeDesc(AppContexts.user().companyId(),
				query.getEmployeeId());

		if (affCompanyHist != null) {

			return affCompanyHist.getLstAffCompanyHistByEmployee().get(0).getLstAffCompanyHistoryItem().stream()
					.filter(x -> aciFinder.getAffCompanyInfoByHistId(x.identifier()) != null)
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max())
							//&& query.getCtgType() == 3 
							? "" :x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		List<AffCompanyHist> affComHistDomainLst = this.achFinder.getAffComHistOfEmployeeListAndBaseDateV2(sids,
				query.getStandardDate());
		List<String> histIds = new ArrayList<>();
		
		affComHistDomainLst.stream().forEach(c ->{
			AffCompanyHistByEmployee empHist = c.getLstAffCompanyHistByEmployee().get(0);
			if (empHist != null) {
				AffCompanyHistItem histItem = empHist.getLstAffCompanyHistoryItem().get(0);
				if (histItem != null) {
					histIds.add(histItem.getHistoryId());
				}
			}
		});

		List<AffCompanyInfo> affCompany = aciFinder.getAffCompanyInfoByHistId(histIds);

		result.stream().forEach(c -> {
			Optional<AffCompanyHist> affComHistOpt = affComHistDomainLst.stream()
					.filter(aff -> aff.getPId().equals(c.getPersonId())).findFirst();
			Optional<AffCompanyInfo> affComOpt = affCompany.stream()
					.filter(aff -> aff.getSid().equals(c.getEmployeeId())).findFirst();
			if (affComOpt.isPresent() && affComHistOpt.isPresent()) {
				c.setPeregDomainDto(AffCompanyHistInfoDto.fromDomain(affComHistOpt.get(), affComOpt.get()));
			}
		});

		return result;
	}
}
