package nts.uk.screen.at.app.query.kfp002;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditingState;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.screen.at.app.kdw013.a.ItemValueDto;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任意期間別実績を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GetAnyPeriodActualResultsScreenQuery {
    @Inject
    private AttendanceTimeOfAnyPeriodRepository attdRepo;

    @Inject
    private AnyPeriodEditingStateRepository editStateRepo;

    @Inject
    private AttendanceItemConvertFactory converterFactory;

    public AnyPeriodResultDataDto getActualResults(String frameCode, List<String> employeeIds, List<Integer> itemIds) {
        List<AttendanceTimeOfAnyPeriod> actualResults = attdRepo.findBySids(employeeIds, frameCode);
        if (actualResults.isEmpty()) throw new BusinessException("Msg_3278");

        // 任意期間別項目に変換する
        List<AnyPeriodActualResultDto> actualResultDtos = new ArrayList<>();
        AnyPeriodRecordToAttendanceItemConverter converter = converterFactory.createOptionalItemConverter();
        actualResults.forEach(ar -> {
            List<ItemValue> itemValues = converter.withAttendanceTime(ar).convert(itemIds);
            AnyPeriodActualResultDto tmp = new AnyPeriodActualResultDto(
                    ar.getEmployeeId(),
                    itemValues.stream().map(ItemValueDto::fromDomain).collect(Collectors.toList())
            );
            actualResultDtos.add(tmp);
        });

        List<AnyPeriodEditStateDto> editStates = new ArrayList<>();
        Map<String, List<AnyPeriodCorrectionEditingState>> editStateMap = editStateRepo.getByListEmployee(frameCode, employeeIds);
        editStateMap.entrySet().forEach(e -> {
            editStates.addAll(e.getValue().stream().map(i -> new AnyPeriodEditStateDto(
                    i.getEmployeeId(),
                    i.getAttendanceItemId(),
                    i.getState().value
            )).collect(Collectors.toList()));
        });

        return new AnyPeriodResultDataDto(actualResultDtos, editStates);
    }
}
