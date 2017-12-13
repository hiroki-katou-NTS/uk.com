package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.dynamic.DynamicConstraintFinder;
import nts.uk.shr.com.primitive.dynamic.types.NumberConstraintDescriptor;
import nts.uk.shr.com.primitive.dynamic.types.StringConstraintDescriptor;
import nts.uk.shr.com.primitive.dynamic.types.TimeConstraintDescriptor;

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
				String itemCode = m.getItemCode().v();
				boolean required = m.getIsRequired().value == 1;

				switch (item.getDataTypeState().getDataTypeValue()) {
				default:
				case STRING:
					StringItem stringItem = (StringItem) item.getDataTypeState();
					switch (stringItem.getStringItemType()) {
					default:
					case ANY:
						return new StringConstraintDescriptor(itemCode, stringItem.getStringItemLength().v(), "Any",
								' ', true, true, null, required);
					case ALPHANUMERIC:
						return new StringConstraintDescriptor(itemCode, stringItem.getStringItemLength().v(),
								"AlphaNumeric", ' ', true, true, null, required);
					case ANYHALFWIDTH:
						return new StringConstraintDescriptor(itemCode, stringItem.getStringItemLength().v(),
								"AnyHalfWidth", ' ', true, true, null, required);
					case KANA:
						return new StringConstraintDescriptor(itemCode, stringItem.getStringItemLength().v(), "Kana",
								' ', true, true, null, required);
					case NUMERIC:
						return new StringConstraintDescriptor(itemCode, stringItem.getStringItemLength().v(), "Numeric",
								' ', true, true, null, required);
					}
				case NUMERIC:
					NumericItem numericItem = (NumericItem) item.getDataTypeState();
					return new NumberConstraintDescriptor(itemCode, numericItem.getNumericItemMin().v().toString(),
							numericItem.getNumericItemMax().v().toString(), "Numeric",
							numericItem.getDecimalPart().v().toString(), required);
				case DATE:
					//DateItem dateItem = (DateItem) item.getDataTypeState();
					return new TimeConstraintDescriptor(itemCode, "", "", "Date", required);
				case SELECTION:
					return new StringConstraintDescriptor(itemCode, 0, "Any", ' ', false, false, null, required);
				case TIME:
					TimeItem timeItem = (TimeItem) item.getDataTypeState();
					return new TimeConstraintDescriptor(m.getItemCode().v(), timeItem.getMin().toString(),
							timeItem.getMax().toString(), "Time", m.getIsRequired().value == 1);
				case TIMEPOINT:
					TimePointItem tpointItem = (TimePointItem) item.getDataTypeState();
					return new TimeConstraintDescriptor(itemCode, tpointItem.getTimePointItemMin().v().toString(),
							tpointItem.getTimePointItemMax().v().toString(), "TimePoint", m.getIsRequired().value == 1);
				}
			} else {
				return null;
			}
		}).filter(f -> f != null).collect(Collectors.toList());
	}

}
