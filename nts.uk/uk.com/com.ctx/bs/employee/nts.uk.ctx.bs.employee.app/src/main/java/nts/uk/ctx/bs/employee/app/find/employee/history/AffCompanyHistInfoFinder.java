package nts.uk.ctx.bs.employee.app.find.employee.history;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
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
	public PeregDomainDto getSingleData(PeregQuery query) {
		return null;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		return null;
	}

}
