package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.care.ChildCareNurseUsedInfoDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.care.ChildCareNurseUsedNumberDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildcareNurseRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/** 子の看護月別残数データ */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_CHILD_CARE_HD_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyChildCareHdRemainDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月: 年月 */
	private YearMonth ym;

	/** 締めID: 締めID */
	// @AttendanceItemValue
	// @AttendanceItemLayout(jpPropertyName = "締めID", layout = "A")
	private int closureID = 1;

	/** 締め日: 日付 */
	// @AttendanceItemLayout(jpPropertyName = "締め日", layout = "B")
	private ClosureDateDto closureDate;

	private ClosureStatus closureStatus = ClosureStatus.UNTREATED;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;

	/** 本年使用数 */
	private ChildCareNurseUsedInfoDto thisYearUsedInfo;
	/** 合計使用数 */
	private ChildCareNurseUsedInfoDto usedInfo;
	/** 翌年使用数 */
	private ChildCareNurseUsedInfoDto nextYearUsedInfo;
	/** 本年残数 */
	private ChildCareNurseUsedNumberDto thisYearRemainNumber;
	/** 翌年残 */
	private ChildCareNurseUsedNumberDto nextYearRemainNumber;

	@Override
	public String employeeId() {
		return employeeId;
	}

	/**
	 * ドメインへ変換
	 */
	@Override
	public ChildcareRemNumEachMonth toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {

		return new ChildcareRemNumEachMonth(
				employeeId, /** 社員ID */
				ym, /** 年月 */
				ConvertHelper.getEnum(closureID, ClosureId.class), /** 締めID */
				new ClosureDate(closureDate.getClosureDay(), closureDate.getLastDayOfMonth()), /** 締め日付 */
				ClosureStatus.UNTREATED, // 締め処理状態←未締め
				/** 子の看護休暇月別残数データ */
				ChildcareNurseRemNumEachMonth.of(
						this.thisYearUsedInfo == null ? new ChildCareNurseUsedInfo() : this.thisYearUsedInfo.domain(), /** 本年使用数 */
						this.usedInfo == null ? new ChildCareNurseUsedInfo() : this.usedInfo.domain(), /** 合計使用数 */
						this.thisYearRemainNumber == null ? new ChildCareNurseRemainingNumber() : this.thisYearRemainNumber.domainRemain(), /** 本年残数 */
						Optional.ofNullable(this.nextYearUsedInfo == null ? null : this.nextYearUsedInfo.domain()), /** 翌年使用数 */
						Optional.ofNullable(this.nextYearRemainNumber == null ? null : this.nextYearRemainNumber.domainRemain())) /** 翌年残数 */
				);
	}

	@Override
	public YearMonth yearMonth() {
		return ym;
	}

	public static MonthlyChildCareHdRemainDto from(ChildcareRemNumEachMonth domain){
		MonthlyChildCareHdRemainDto dto = new MonthlyChildCareHdRemainDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId().value);
			dto.setClosureStatus(domain.getClosureStatus());
			dto.setClosureDate(new ClosureDateDto(domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth()));
			dto.setThisYearUsedInfo(ChildCareNurseUsedInfoDto.from(domain.getRemNumEachMonth().getThisYearUsedInfo()));
			dto.setUsedInfo(ChildCareNurseUsedInfoDto.from(domain.getRemNumEachMonth().getUsedInfo()));
			dto.setNextYearUsedInfo(ChildCareNurseUsedInfoDto.from(domain.getRemNumEachMonth().getNextYearUsedInfo().orElse(null)));
			dto.setThisYearRemainNumber(ChildCareNurseUsedNumberDto.from(domain.getRemNumEachMonth().getThisYearRemainNumber()));
			dto.setNextYearRemainNumber(ChildCareNurseUsedNumberDto.from(domain.getRemNumEachMonth().getNextYearRemainNumber().orElse(null)));
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case THIS_YEAR + USAGE:
		case TOTAL + USAGE:
		case NEXT_YEAR + USAGE:
			return new ChildCareNurseUsedInfoDto();
		case THIS_YEAR + REMAIN:
		case NEXT_YEAR + REMAIN:
			return new ChildCareNurseUsedNumberDto();
		default:
			return super.newInstanceOf(path);
		}
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		switch (path) {
		case THIS_YEAR + USAGE:
			return Optional.ofNullable(this.thisYearUsedInfo);
		case TOTAL + USAGE:
			return Optional.ofNullable(this.usedInfo);
		case NEXT_YEAR + USAGE:
			return Optional.ofNullable(this.nextYearUsedInfo);
		case THIS_YEAR + REMAIN:
			return Optional.ofNullable(this.thisYearRemainNumber);
		case NEXT_YEAR + REMAIN:
			return Optional.ofNullable(this.nextYearRemainNumber);
		default:
			return super.get(path);
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {

		switch (path) {
		case THIS_YEAR + USAGE:
			this.thisYearUsedInfo = (ChildCareNurseUsedInfoDto) value; return;
		case TOTAL + USAGE:
			this.usedInfo = (ChildCareNurseUsedInfoDto) value; return;
		case NEXT_YEAR + USAGE:
			this.nextYearUsedInfo = (ChildCareNurseUsedInfoDto) value; return;
		case THIS_YEAR + REMAIN:
			this.thisYearRemainNumber = (ChildCareNurseUsedNumberDto) value; return;
		case NEXT_YEAR + REMAIN:
			this.nextYearRemainNumber = (ChildCareNurseUsedNumberDto) value; return;
		default:
		}
	}
	
	@Override
	public boolean isRoot() {
		return true;
	}
	
	@Override
	public String rootName() {
		return MONTHLY_CHILD_CARE_HD_REMAIN_NAME;
	}
}
