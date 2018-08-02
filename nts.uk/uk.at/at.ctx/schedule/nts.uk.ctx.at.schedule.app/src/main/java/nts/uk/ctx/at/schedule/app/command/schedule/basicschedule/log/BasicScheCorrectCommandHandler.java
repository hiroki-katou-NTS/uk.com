package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.ArrayList;
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
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectedItem;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectionTarget;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
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
public class BasicScheCorrectCommandHandler extends CommandHandler<BasicScheCorrectCommand> {

	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
	
	@Inject
	private CompareItemValue compareItemValue;

	@Override
	protected void handle(CommandHandlerContext<BasicScheCorrectCommand> context) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.SCHEDULE);
		
		// Get all attendanceItemId from domain BasicSchedule
		List<Integer> attItemIds = new ArrayList<>();
		for(int i =1; i<=66; i++){
			attItemIds.add(Integer.valueOf(i));
		}
		
		// Get Name of attendanceItemId
		Map<Integer, String> itemNameMap = dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(attItemIds)
				.stream().collect(Collectors.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId,
						x -> x.getAttendanceItemName()));
		
//		// get data
//		// Map< Pair<employeeId, date> , List<ScheLogDto>>
//		Map<Pair<String, GeneralDate>, List<ScheLogDto>> scheLog = context.getCommand().getScheLog();
//		
//		scheLog.forEach((key, value) -> {
//			// set employeeId and date
//			val daiTarget = new ScheduleCorrectionTarget(key.getLeft(), key.getRight());
//			for(ScheLogDto scheLogDto : value){
//				ScheduleCorrectedItem scheduleCorrectedItem = new ScheduleCorrectedItem(scheLogDto.getAttendanceItemName(),
//						scheLogDto.getAttendanceItemId(),
//						scheLogDto.getBefore(),
//						scheLogDto.getAfter(), 
//						scheLogDto.getValueType(), 
//						null,
//						EnumAdaptor.valueOf(scheLogDto.getAttr(), CorrectionAttr.class));
//				// set list ScheduleCorrectedItem
//				daiTarget.getCorrectedItems().add(scheduleCorrectedItem);
//			}
//			targets.add(daiTarget);
//		});
		val correctionLogParameter = new BasicScheduleCorrectionParameter(
				mapToScheduleCorrection(convertToItemValue(context.getCommand().getDomainNew()),
						convertToItemValue(context.getCommand().getDomainOld()), new ArrayList<>(), itemNameMap));
		DataCorrectionContext.setParameter(correctionLogParameter);
		AttendanceItemIdContainer.getIds(AttendanceItemType.DAILY_ITEM);
	}

	@Override
	protected void postHandle(CommandHandlerContext<BasicScheCorrectCommand> context) {
		super.postHandle(context);
		DataCorrectionContext.transactionFinishing();
	}
	
	/**
	 * convert to Map ItemValue
	 * @param domains
	 * @return Map <Pair<employeeId, Date>, Map<AttendanceItemId, ItemValue>>
	 */
	private Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> convertToItemValue(List<BasicSchedule> domains){
		Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> result = new HashMap<>();
		for (BasicSchedule daily : domains) {
			 List<ItemValue> values = compareItemValue.convertToItemValue(daily);
			 Map<Integer, ItemValue> map = values.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x));
			 result.put(Pair.of(daily.getEmployeeId(), daily.getDate()), map);
		}
		return result;
	}

	private List<ScheduleCorrectionTarget> mapToScheduleCorrection(
			Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemNewMap,
			Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemOldMap, List<Integer> itemEdit,
			Map<Integer, String> itemNameMap) {
		List<ScheduleCorrectionTarget> targets = new ArrayList<>();
//		itemNewMap.forEach((key, value) -> {
//			val itemOldValueMap = itemOldMap.get(key);
//			val daiTarget = new ScheduleCorrectionTarget(key.getLeft(), key.getRight());
//			value.forEach((valueItemKey, valueItemNew) -> {
//				val itemOld = itemOldValueMap.get(valueItemKey);
//				if (valueItemNew.getValue() != null && itemOld.getValue() != null
//						&& !valueItemNew.getValue().equals(itemOld.getValue())) {
//					ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameMap.get(valueItemKey),
//							valueItemNew.getItemId(), itemOld.getValue(), valueItemNew.getValue(),
//							// TODO : convert Type ???
//							convertType(valueItemNew.getValueType()), null,
//							itemEdit.contains(valueItemNew.getItemId())
//									? CorrectionAttr.EDIT : CorrectionAttr.CALCULATE);
//					daiTarget.getCorrectedItems().add(item);
//				}
//			});
//			targets.add(daiTarget);
//		});
		itemNewMap.forEach((key, value) -> {
			val itemOldValueMap = itemOldMap.get(key);
			val daiTarget = new ScheduleCorrectionTarget(key.getLeft(), key.getRight());
			value.forEach((valueItemKey, valueItemNew) -> {				
				if (itemOldValueMap == null) {
					ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameMap.get(valueItemKey),
							valueItemNew.getItemId(), null, valueItemNew.getValue(),
							convertType(valueItemNew.getValueType()), null,
							itemEdit.contains(valueItemNew.getItemId())
									? CorrectionAttr.EDIT : CorrectionAttr.CALCULATE);
					daiTarget.getCorrectedItems().add(item);
				} else {
					val itemOld = itemOldValueMap.get(valueItemKey);
					if (valueItemNew.getValue() != null && itemOld.getValue() != null
							&& !valueItemNew.getValue().equals(itemOld.getValue())
							|| (valueItemNew.getValue() == null && itemOld.getValue() != null)
							|| (valueItemNew.getValue() != null && itemOld.getValue() == null)) {
						ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameMap.get(valueItemKey),
								valueItemNew.getItemId(), itemOld.getValue(), valueItemNew.getValue(),
								convertType(valueItemNew.getValueType()), null,
								itemEdit.contains(valueItemNew.getItemId())
										? CorrectionAttr.EDIT : CorrectionAttr.CALCULATE);
						daiTarget.getCorrectedItems().add(item);
					}
				}
			});
			targets.add(daiTarget);
		});
		return targets;
	}

	private Integer convertType(ValueType valueType) {
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
