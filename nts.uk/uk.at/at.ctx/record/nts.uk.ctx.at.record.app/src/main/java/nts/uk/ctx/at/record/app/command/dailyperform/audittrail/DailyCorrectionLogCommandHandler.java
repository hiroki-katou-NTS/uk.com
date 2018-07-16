package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogParameter.DailyCorrectedItem;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogParameter.DailyCorrectionTarget;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
public class DailyCorrectionLogCommandHandler extends CommandHandler<DailyCorrectionLogCommand> {
	
	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;

    private final static List<Integer> ITEM_ID_ALL = AttendanceItemIdContainer.getIds(AttendanceItemType.DAILY_ITEM).stream().map(x -> x.getItemId()).collect(Collectors.toList());
	
    @Override
	protected void handle(CommandHandlerContext<DailyCorrectionLogCommand> context) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.DAILY);
        
		Map<Integer, String> itemNameMap = dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(ITEM_ID_ALL)
				.stream().collect(Collectors.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId,
						x -> x.getAttendanceItemName()));
		
		val correctionLogParameter = new DailyCorrectionLogParameter(
				mapToDailyCorrection(convertToItemValue(context.getCommand().getDomainNew()),
						convertToItemValue(context.getCommand().getDomainOld()), new ArrayList<>(), itemNameMap));
		DataCorrectionContext.setParameter(correctionLogParameter);
		AttendanceItemIdContainer.getIds(AttendanceItemType.DAILY_ITEM);

	}

	@Override
	protected void postHandle(CommandHandlerContext<DailyCorrectionLogCommand> context) {
		super.postHandle(context);
		DataCorrectionContext.transactionFinishing();
	}
	
	private Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> convertToItemValue(List<IntegrationOfDaily> domains){
		Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> result = new HashMap<>();
		for (IntegrationOfDaily daily : domains) {
			List<ItemValue> values = DailyRecordToAttendanceItemConverterImpl.builder().setData(daily).convert(ITEM_ID_ALL);
			 Map<Integer, ItemValue> map = values.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x));
			 result.put(Pair.of(daily.getWorkInformation().getEmployeeId(), daily.getWorkInformation().getYmd()), map);
		}
		return result;
	}
	
	private List<DailyCorrectionTarget> mapToDailyCorrection(Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemNewMap, Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemOldMap, List<Integer> itemEdit, Map<Integer, String> itemNameMap){
		List<DailyCorrectionTarget> targets = new ArrayList<>();
		itemNewMap.forEach((key, value) -> {
			val itemOldValueMap = itemOldMap.get(key);
			val daiTarget = new DailyCorrectionTarget(key.getLeft(), key.getRight());
			value.forEach((valueItemKey, valueItemNew) -> {
				val itemOld = itemOldValueMap.get(valueItemKey);
				if(valueItemNew.getValue() != null && itemOld.getValue() != null && !valueItemNew.getValue().equals(itemOld.getValue())){
					DailyCorrectedItem item = new DailyCorrectedItem(itemNameMap.get(valueItemKey), valueItemNew.getItemId(),
							itemOld.getValue(), valueItemNew.getValue(), convertType(valueItemNew.getValueType()),
							itemEdit.contains(valueItemNew.getItemId()) ? CorrectionAttr.EDIT
									: CorrectionAttr.CALCULATE);
					daiTarget.getCorrectedItems().add(item);
				}
			});
			targets.add(daiTarget);
		});
		return targets;
	}

	private Integer convertType(ValueType valueType){
		switch (valueType.value) {
		
		case 1:
		case 2:
			return DataValueAttribute.TIME.value;
			
		case 13:
			return DataValueAttribute.MONEY.value;
			
		default:
			return DataValueAttribute.STRING.value;
		}
	}
}
