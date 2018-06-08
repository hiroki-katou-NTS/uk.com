package nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class Specialleave18InformationFinder implements PeregFinder<Specialleave18informationDto>{

	@Inject 
	private SpecialleaveInformationFinder specialleaveInformationFinder;
	
	@Override
	public String targetCategoryCode() {
		return "CS00056";
	}

	@Override
	public Class<Specialleave18informationDto> dtoClass() {
		return Specialleave18informationDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		// 社員ID　＝　社員ID　 and 特別休暇コード＝17
		return specialleaveInformationFinder.getSingleData(query, SpecialLeaveCode.CS00056.value);
	}
	
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
