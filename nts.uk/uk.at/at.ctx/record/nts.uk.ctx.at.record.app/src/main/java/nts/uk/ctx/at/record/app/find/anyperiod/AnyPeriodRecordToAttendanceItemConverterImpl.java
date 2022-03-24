package nts.uk.ctx.at.record.app.find.anyperiod;

import nts.uk.ctx.at.record.app.find.anyperiod.dto.AttendanceTimeOfAnyPeriodDto;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConverterCommonService;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;

import java.util.HashMap;
import java.util.Optional;

public class AnyPeriodRecordToAttendanceItemConverterImpl extends AttendanceItemConverterCommonService
        implements AnyPeriodRecordToAttendanceItemConverter {

    private String employeeId;

    private AnyPeriodRecordToAttendanceItemConverterImpl(OptionalItemRepository optionalItem) {
        super(new HashMap<>(), optionalItem);
    }

    public static AnyPeriodRecordToAttendanceItemConverterImpl builder(OptionalItemRepository optionalItem) {
        return new AnyPeriodRecordToAttendanceItemConverterImpl(optionalItem);
    }

    @Override
    public AnyPeriodRecordToAttendanceItemConverter completed() {
        return this;
    }

    @Override
    public AnyPeriodRecordToAttendanceItemConverter withBase(String employeeId) {
        this.employeeId = (employeeId);
        return this;
    }

    @Override
    public AnyPeriodRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfAnyPeriod domain) {
        if (domain != null) {
            this.withBase(domain.getEmployeeId());
        }

        this.domainSource.put(ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME, domain);
        this.dtoSource.put(ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME, null);
        this.itemValues.put(ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME, null);

        return this;
    }

    @Override
    protected boolean isMonthly() {
        return false;
    }

    @Override
    protected boolean isAnyPeriod() {
        return true;
    }

    @Override
    protected Object correctOptionalItem(Object dto) {
        return dto;
    }

    @Override
    protected boolean isOpyionalItem(String type) {
        return false;
//        return type.equals(ItemConst.OPTIONAL_ITEM);
    }

    @Override
    protected void convertDomainToDto(String type) {
        switch (type) {
            case ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME:
                processOnDomain(type, c -> AttendanceTimeOfAnyPeriodDto.fromDomain((AttendanceTimeOfAnyPeriod) c, loadOptionalItemMaster()));
                break;
            default:
                break;
        }
    }

    @Override
    protected Object toDomain(ConvertibleAttendanceItem dto) {
        return dto.toDomain(employeeId, null);
    }

    @Override
    public Optional<AttendanceTimeOfAnyPeriod> toAttendanceTime() {
        return Optional.ofNullable((AttendanceTimeOfAnyPeriod) getDomain(ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME));
    }

}
