package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ReserveLeaveDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 積立年休月別残数データ */
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_RESERVE_LEAVING_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class RsvLeaRemNumEachMonthDto extends MonthlyItemCommon {
	/** 会社ID */
	private String companyId;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月: 年月 */
	private YearMonth ym;

	/** 締めID: 締めID */
	private int closureID = 1;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;

	/** 締め処理状態 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = CLOSURE_STATE, layout = LAYOUT_B)
	private int closureStatus;

	/** 積立年休 */
	@AttendanceItemLayout(jpPropertyName = RETENTION, layout = LAYOUT_C)
	private ReserveLeaveDto reserveLeave;
	
	/** 実積立年休 */
	@AttendanceItemLayout(jpPropertyName = REAL + RETENTION, layout = LAYOUT_D)
	private ReserveLeaveDto realReserveLeave;
	
	/** 積立年休付与情報 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = GRANT + INFO, layout = LAYOUT_E)
	private Double reserveLeaveGrant;

	/** 付与区分 */
	@AttendanceItemLayout(jpPropertyName = GRANT + ATTRIBUTE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.BOOLEAN)
	private boolean grantAtr;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	public static RsvLeaRemNumEachMonthDto from(RsvLeaRemNumEachMonth domain) {
		RsvLeaRemNumEachMonthDto dto = new RsvLeaRemNumEachMonthDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setClosureDate(domain.getClosureDate() == null ? null : ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(DatePeriodDto.from(domain.getClosurePeriod()));
			dto.setClosureStatus(domain.getClosureStatus().value);
			dto.setReserveLeave(ReserveLeaveDto.from(domain.getReserveLeave()));
			dto.setRealReserveLeave(ReserveLeaveDto.from(domain.getRealReserveLeave()));
			domain.getReserveLeaveGrant().ifPresent(g -> {
				dto.setReserveLeaveGrant(g.getGrantDays().v());
			});
			dto.setGrantAtr(domain.isGrantAtr());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public RsvLeaRemNumEachMonth toDomain() {
		if (!this.isHaveData()) {
			return null;
		}
		return RsvLeaRemNumEachMonth.of(employeeId, ym, ConvertHelper.getEnum(closureID, ClosureId.class),
				closureDate == null ? null : closureDate.toDomain(), datePeriod == null ? null : datePeriod.toDomain(),
				ConvertHelper.getEnum(closureStatus, ClosureStatus.class), 
				reserveLeave == null ? null : reserveLeave.toDomain(), 
				realReserveLeave == null ? null : realReserveLeave.toRealDomain(),
				Optional.ofNullable(reserveLeaveGrant == null ? null : ReserveLeaveGrant.of(new ReserveLeaveGrantDayNumber(reserveLeaveGrant))),
				grantAtr);
	}

	@Override
	public YearMonth yearMonth() {
		return this.ym;
	}
}
