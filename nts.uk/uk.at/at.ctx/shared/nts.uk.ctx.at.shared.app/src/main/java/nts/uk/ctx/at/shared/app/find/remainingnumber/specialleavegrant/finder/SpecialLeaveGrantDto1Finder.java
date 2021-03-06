package nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantCode;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class SpecialLeaveGrantDto1Finder implements PeregFinder<SpecialLeaveGrantDto1>{

	@Inject 
	private SpecialLeaveGrantFinder specialLeaveGrantFinder;
	
	@Override
	public String targetCategoryCode() {
		return "CS00039";
	}

	@Override
	public Class<SpecialLeaveGrantDto1> dtoClass() {
		return SpecialLeaveGrantDto1.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		// 社員ID　＝　社員ID　 and 特別休暇コード＝１
		return specialLeaveGrantFinder.getSingleData(query, SpecialLeaveGrantCode.CS00039.value);
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

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#getAllData(nts.uk.shr.pereg.app.find.PeregQueryByListEmp)
	 */
	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		return specialLeaveGrantFinder.getAllData(query, SpecialLeaveGrantCode.CS00039.value);
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		return specialLeaveGrantFinder.getListData(query, SpecialLeaveGrantCode.CS00039.value);
	}
}
