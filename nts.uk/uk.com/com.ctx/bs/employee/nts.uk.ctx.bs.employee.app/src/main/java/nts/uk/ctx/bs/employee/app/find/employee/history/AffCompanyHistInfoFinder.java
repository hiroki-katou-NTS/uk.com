package nts.uk.ctx.bs.employee.app.find.employee.history;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
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
		AffCompanyInfo info = aciFinder.getAffCompanyInfoByHistId(query.getInfoId());

		AffCompanyHist domain = achFinder.getAffCompanyHistoryOfHistInfo(query.getInfoId());

		return AffCompanyHistInfoDto.fromDomain(domain, info);
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		AffCompanyHist affCompanyHist = achFinder.getAffCompanyHistoryOfEmployee(query.getEmployeeId());
		if (affCompanyHist != null)
			return affCompanyHist.getLstAffCompanyHistByEmployee().get(0).getLstAffCompanyHistoryItem().stream()
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), x.end().toString()))
					.collect(Collectors.toList());
		return new ArrayList<>();
	}

}
