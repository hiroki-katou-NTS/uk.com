package nts.uk.ctx.office.dom.favorite;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

@AllArgsConstructor
public class RequireImpl implements FavoriteSpecify.Require {
	
	private WorkplaceInforAdapter adapter;

	@Override
	public Map<String, WorkplaceInforImport> getWrkspDispName(List<String> wrkspIds, GeneralDate date) {
		return adapter.getWorkplaceInfor(wrkspIds, date);
	}
}