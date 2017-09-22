package nts.uk.ctx.bs.person.app.find.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.dynamic.DynamicConstraintFinder;

@Stateless
public class DynamicConstraintFinderImpl implements DynamicConstraintFinder {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Override
	public List<Object> find(List<String> codes) {
		String contractCode = AppContexts.user().contractCode();

		return pernfoItemDefRep.getPerInfoItemDefByListId(codes, contractCode).stream().map(m -> {
			ItemTypeState state = m.getItemTypeState();
			ItemType type = state.getItemType();
			if (type == ItemType.SINGLE_ITEM) {
				SingleItem item = (SingleItem) state;
				switch (item.getDataTypeState().getDataTypeValue()) {
				default:
				case STRING:
					return null;
				case NUMERIC:
					return null;
				case DATE:
					return null;
				case SELECTION:
					return null;
				case TIME:
					return null;
				case TIMEPOINT:
					return null;
				}
			} else {
				return null;
			}
		}).filter(f -> f != null).collect(Collectors.toList());
	}

}
