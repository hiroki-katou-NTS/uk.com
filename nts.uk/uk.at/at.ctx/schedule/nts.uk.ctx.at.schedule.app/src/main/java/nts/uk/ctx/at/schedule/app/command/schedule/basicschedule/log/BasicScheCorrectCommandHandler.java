package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectedItem;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectionTarget;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItemManagementRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
@Transactional(value = TxType.SUPPORTS)
@Stateless
public class BasicScheCorrectCommandHandler extends CommandHandler<BasicScheCorrectCommand> {
	
	@Inject
	private CompareItemValue compareItemValue;
	
	@Inject
	private ScheduleItemManagementRepository scheduleItemManagementRepository;

	@Override
	protected void handle(CommandHandlerContext<BasicScheCorrectCommand> context) {
		String companyId = AppContexts.user().companyId();
		DataCorrectionContext.transactional(CorrectionProcessorId.SCHEDULE, () -> {
			
			// Get all attendanceItemId from domain BasicSchedule
			List<Integer> attItemIds = new ArrayList<>();
			for(int i =1; i<=103; i++){
				attItemIds.add(Integer.valueOf(i));
			}
			
			// Get Name of attendanceItemId
			List<ScheduleItem> itemIdNameList = this.scheduleItemManagementRepository.findAllScheduleItem(companyId);
			
			List<ScheduleItem> newItemIdNameList = itemIdNameList.stream().filter(item -> attItemIds.contains(Integer.valueOf(item.getScheduleItemId()))).collect(Collectors.toList());
			
			Map<String, String> itemNameIdMap  = newItemIdNameList.stream().collect(Collectors.toMap(ScheduleItem :: getScheduleItemId, x -> x.getScheduleItemName()));
			
			val correctionLogParameter = new BasicScheduleCorrectionParameter(
					mapToScheduleCorrection(convertToItemValue(context.getCommand().getDomainNew()),
							convertToItemValue(context.getCommand().getDomainOld()), itemNameIdMap));
			DataCorrectionContext.setParameter(correctionLogParameter);
			AttendanceItemIdContainer.getIds(AttendanceItemType.DAILY_ITEM);
		});
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
			Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemOldMap,
			Map<String, String> itemNameIdMap) {
		List<ScheduleCorrectionTarget> targets = new ArrayList<>();

		// set correctionAttr
		// attendanceItemId lien quan den SCHE_TIME (lien quan den tinh toan- CALCULATE)
		List<Integer> correctionItemIds = Arrays.asList(34,35,36,37,39,43,44,45,46,47,48,49,50,51,52,102,103);
		itemNewMap.forEach((key, value) -> {
			val itemOldValueMap = itemOldMap.get(key);
			val daiTarget = new ScheduleCorrectionTarget(key.getLeft(), key.getRight());
			
			value.forEach((valueItemKey, valueItemNew) -> {				
				if (itemOldValueMap == null) {
					if(valueItemNew.getValue() != null){
						ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameIdMap.get(String.valueOf(valueItemKey)),
								valueItemNew.getItemId(), null, valueItemNew.getValue(),
								convertType(valueItemNew.getValueType()), null,
								correctionItemIds.contains(valueItemNew.getItemId())
										? CorrectionAttr.CALCULATE : CorrectionAttr.EDIT);
						daiTarget.getCorrectedItems().add(item);
					}					
				} else {
					val itemOld = itemOldValueMap.get(valueItemKey);
					if (valueItemNew.getValue() != null && itemOld.getValue() != null
							&& !valueItemNew.getValue().equals(itemOld.getValue())
							|| (valueItemNew.getValue() == null && itemOld.getValue() != null)
							|| (valueItemNew.getValue() != null && itemOld.getValue() == null)) {
						ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameIdMap.get(String.valueOf(valueItemKey)),
								valueItemNew.getItemId(), itemOld.getValue(), valueItemNew.getValue(),
								convertType(valueItemNew.getValueType()), null,
								correctionItemIds.contains(valueItemNew.getItemId())
										? CorrectionAttr.CALCULATE : CorrectionAttr.EDIT);
						daiTarget.getCorrectedItems().add(item);
					}
				}
			});
			targets.add(daiTarget);
		});
		return targets;
	}
	
	/**
	 * Convert type ValueType from to DataValueAttribute
	 * @param valueType
	 * @return
	 */
	private Integer convertType(ValueType valueType) {
		switch (valueType.value) {

		case 1:
		case 2:
			return DataValueAttribute.TIME.value;

		case 13:
			return DataValueAttribute.MONEY.value;
			
		case 15:
			return DataValueAttribute.CLOCK.value;

		default:
			return DataValueAttribute.STRING.value;
		}
	}

}
