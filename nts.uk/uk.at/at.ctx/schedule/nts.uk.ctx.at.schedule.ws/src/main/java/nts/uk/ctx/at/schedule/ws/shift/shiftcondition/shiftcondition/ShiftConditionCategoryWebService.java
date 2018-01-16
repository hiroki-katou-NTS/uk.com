package nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionCategoryDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionCategoryFinder;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionFinder;
import nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition.dto.ShiftConditionNodeDto;

@Path("at/schedule/shift/shiftCondition/shiftCondition")
@Produces("application/json")
public class ShiftConditionCategoryWebService {
	@Inject
	private ShiftConditionCategoryFinder categoryFinder;
	@Inject
	private ShiftConditionFinder shiftConditionFinder;

	@POST
	@Path("getAllShiftConCategory")
	public List<ShiftConditionCategoryDto> getAllListShiftConCategory() {
		return this.categoryFinder.getAllShiftConditonCategory();
	}
	
	@POST
	@Path("buildTreeShiftCondition")
	public List<ShiftConditionNodeDto> buildTreeShiftCondition() {
		List<ShiftConditionCategoryDto>  listShiftCategory = this.categoryFinder.getAllShiftConditonCategory();
		List<ShiftConditionDto> listShiftCondition = this.shiftConditionFinder.getAllShiftCondition();
		
		List<ShiftConditionNodeDto> itemsTree = new ArrayList<>();
		
		// build level 1
		listShiftCategory.stream().forEach(shiftCate -> {
			ShiftConditionNodeDto level1 = new ShiftConditionNodeDto(shiftCate.getCategoryNo(), shiftCate.getCategoryName(), new ArrayList<>());
			// build level 2
			listShiftCondition.stream().forEach(shiftCon -> {
				if (shiftCate.getCategoryNo() == shiftCon.getCategoryNo()) {
					ShiftConditionNodeDto level2 = new ShiftConditionNodeDto(shiftCon.getConditionNo(), shiftCon.getConditionName(), new ArrayList<>());
					level1.getChilds().add(level2);
				}
			});
			
			itemsTree.add(level1);
		});
		
		return itemsTree;
	}
}
