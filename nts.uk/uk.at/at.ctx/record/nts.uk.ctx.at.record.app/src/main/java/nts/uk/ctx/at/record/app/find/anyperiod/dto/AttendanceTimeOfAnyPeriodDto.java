package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.TotalCountByPeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.VerticalTotalOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 任意期間別実績の勤怠時間 */
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(isContainer = true, rootName = ItemConst.ANY_PERIOD_ATTENDANCE_TIME_NAME, itemType = AttendanceItemUtil.AttendanceItemType.ANY_PERIOD_ITEM)
public class AttendanceTimeOfAnyPeriodDto extends AttendanceItemCommon {

    private static final long serialVersionUID = 1L;

    /** 社員ID: 社員ID */
    private String employeeId;

    /** 任意集計枠コード */
    private String anyAggrFrameCode;

    /** 月の計算: 期間別の月の計算 */
    @AttendanceItemLayout(jpPropertyName = CALC, layout = LAYOUT_B)
    private MonthlyCalculationByPeriodDto monthlyCalculation;

    /** 時間外超過: 期間別の時間外超過 */
    @AttendanceItemLayout(jpPropertyName = EXCESS, layout = LAYOUT_C)
    private ExcessOutsideByPeriodDto excessOutsideWork;

    /** 36協定時間: 期間別の36協定時間 */
	@AttendanceItemLayout(jpPropertyName = AGREEMENT + TIME, layout = LAYOUT_D)
	private AgreementTimeByPeriodDto agreementTime;

    /** 縦計: 期間別の縦計 */
    @AttendanceItemLayout(jpPropertyName = VERTICAL_TOTAL, layout = LAYOUT_E)
    private VerticalTotalOfMonthlyDto verticalTotal;

    /** 回数集計: 期間別の回数集計 */
    @AttendanceItemLayout(jpPropertyName = COUNT + AGGREGATE, layout = LAYOUT_F)
    private TotalCountByPeriodDto totalCount;

    /** 任意項目: 期間別の任意項目 */
    @AttendanceItemLayout(jpPropertyName = OPTIONAL_ITEM, layout = LAYOUT_G)
    private AnyItemByPeriodDto anyItem;

    @Override
    public String employeeId() {
        return this.employeeId;
    }

    @Override
    public GeneralDate workingDate() {
        return null;
    }

    @Override
    public AttendanceTimeOfAnyPeriod toDomain(String employeeId, GeneralDate date) {
        return AttendanceTimeOfAnyPeriod.of(
                employeeId,
                new AnyAggrFrameCode(this.anyAggrFrameCode),
                this.monthlyCalculation.toDomain(),
                this.excessOutsideWork.toDomain(),
                this.agreementTime.toDomain(),
                this.verticalTotal.toDomain(),
                this.totalCount.toDomain(),
                this.anyItem.toDomain()
        );
    }

    public static AttendanceTimeOfAnyPeriodDto fromDomain(AttendanceTimeOfAnyPeriod domain) {
        return new AttendanceTimeOfAnyPeriodDto(
                domain.getEmployeeId(),
                domain.getAnyAggrFrameCode().v(),
                MonthlyCalculationByPeriodDto.from(domain.getMonthlyAggregation()),
                ExcessOutsideByPeriodDto.from(domain.getExcessOutside()),
                AgreementTimeByPeriodDto.from(domain.getAgreementTime()),
                VerticalTotalOfMonthlyDto.from(domain.getVerticalTotal()),
                TotalCountByPeriodDto.from(domain.getTotalCount()),
                AnyItemByPeriodDto.fromDomain(domain.getAnyItem())
        );
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public String rootName() {
        return ANY_PERIOD_ATTENDANCE_TIME_NAME;
    }

    @Override
    public AttendanceItemDataGate newInstanceOf(String path) {
        switch (path) {
            case CALC:
                return new MonthlyCalculationByPeriodDto();
            case EXCESS:
                return new ExcessOutsideByPeriodDto();
            case AGREEMENT + TIME:
                return new AgreementTimeByPeriodDto();
            case VERTICAL_TOTAL:
                return new VerticalTotalOfMonthlyDto();
            case (COUNT + AGGREGATE):
                return new TotalCountByPeriodDto();
            case OPTIONAL_ITEM:
                return new AnyItemByPeriodDto();
            default:
                return super.newInstanceOf(path);
        }
    }

    @Override
    public Optional<AttendanceItemDataGate> get(String path) {
        switch (path) {
            case CALC:
                return Optional.ofNullable(monthlyCalculation);
            case EXCESS:
                return Optional.ofNullable(excessOutsideWork);
            case AGREEMENT + TIME:
                return Optional.ofNullable(agreementTime);
            case VERTICAL_TOTAL:
                return Optional.ofNullable(verticalTotal);
            case (COUNT + AGGREGATE):
                return Optional.ofNullable(totalCount);
            case OPTIONAL_ITEM:
                return Optional.ofNullable(anyItem);
            default:
                return super.get(path);
        }
    }

    @Override
    public void set(String path, AttendanceItemDataGate value) {
        switch (path) {
            case CALC:
                monthlyCalculation = (MonthlyCalculationByPeriodDto) value; break;
            case EXCESS:
                excessOutsideWork = (ExcessOutsideByPeriodDto) value; break;
            case AGREEMENT + TIME:
                agreementTime = (AgreementTimeByPeriodDto) value; break;
            case VERTICAL_TOTAL:
                verticalTotal = (VerticalTotalOfMonthlyDto) value; break;
            case (COUNT + AGGREGATE):
                totalCount = (TotalCountByPeriodDto) value; break;
            case OPTIONAL_ITEM:
                anyItem = (AnyItemByPeriodDto) value; break;
            default:
                break;
        }
    }


}
