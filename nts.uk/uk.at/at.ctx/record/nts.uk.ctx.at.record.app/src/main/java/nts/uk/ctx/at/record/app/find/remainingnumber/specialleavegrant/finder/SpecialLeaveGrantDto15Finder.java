package nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.finder;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantFinder;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantCode;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class SpecialLeaveGrantDto15Finder implements PeregFinder<SpecialLeaveGrantDto15>{

	@Inject 
	private SpecialLeaveGrantFinder specialLeaveGrantFinder;
	
	@Override
	public String targetCategoryCode() {
		return "CS00063";
	}

	@Override
	public Class<SpecialLeaveGrantDto15> dtoClass() {
		return SpecialLeaveGrantDto15.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		// 社員ID　＝　社員ID　 and 特別休暇コード＝１
		return specialLeaveGrantFinder.getSingleData(query, SpecialLeaveGrantCode.CS00063.value);
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
