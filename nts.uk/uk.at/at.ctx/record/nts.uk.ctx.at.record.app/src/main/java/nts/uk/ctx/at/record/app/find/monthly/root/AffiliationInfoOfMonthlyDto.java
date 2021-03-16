package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AggregateAffiliationInfoDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の所属情報 */
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_AFFILIATION_INFO_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class AffiliationInfoOfMonthlyDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	private long version;
	
	/** 年月: 年月 */
	private YearMonth yearMonth;

	/** 月初の情報: 集計所属情報 */
	@AttendanceItemLayout(jpPropertyName = START_MONTH, layout = LAYOUT_A)
	private AggregateAffiliationInfoDto startMonthInfo;

	/** 月末の情報: 集計所属情報 */
	@AttendanceItemLayout(jpPropertyName = END_MONTH, layout = LAYOUT_B)
	private AggregateAffiliationInfoDto endMonthInfo;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 締めID: 締めID */
	private int closureID = 1;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public AffiliationInfoOfMonthly toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		if(!this.isHaveData()) {
			return null;
		}
		if(employeeId == null){
			employeeId = this.employeeId;
		}
		if(ym == null){
			ym = this.yearMonth;
		} 
		if(closureDate == null){
			closureDate = this.closureDate;
		}
		AffiliationInfoOfMonthly domain = AffiliationInfoOfMonthly.of(employeeId, yearMonth, 
																		ConvertHelper.getEnum(closureID, ClosureId.class), 
																		closureDate == null ? null : closureDate.toDomain(), 
																		startMonthInfo == null ? new AggregateAffiliationInfo()  : startMonthInfo.toDomain(), 
																		endMonthInfo == null ? new AggregateAffiliationInfo() : endMonthInfo.toDomain());
		domain.setVersion(this.version);
		
		return domain;
		
	}

	@Override
	public YearMonth yearMonth() {
		return yearMonth;
	}

	public static AffiliationInfoOfMonthlyDto from(AffiliationInfoOfMonthly domain) {
		AffiliationInfoOfMonthlyDto dto = new AffiliationInfoOfMonthlyDto();
		if (domain != null) {
			dto.setClosureDate(ClosureDateDto.from(domain.getClosureDate()));
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setEndMonthInfo(AggregateAffiliationInfoDto.from(domain.getLastInfo()));
			dto.setStartMonthInfo(AggregateAffiliationInfoDto.from(domain.getFirstInfo()));
			dto.setYearMonth(domain.getYearMonth());
			dto.setVersion(domain.getVersion());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public boolean isRoot() { return true; }

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case START_MONTH:
		case END_MONTH:
			return new AggregateAffiliationInfoDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case START_MONTH:
			return Optional.ofNullable(startMonthInfo);
		case END_MONTH:
			return Optional.ofNullable(endMonthInfo);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case START_MONTH:
			this.startMonthInfo = (AggregateAffiliationInfoDto) value;
			break;
		case END_MONTH:
			this.endMonthInfo = (AggregateAffiliationInfoDto) value;
			break;
		default:
		}
	}

	@Override
	public String rootName() {
		return MONTHLY_AFFILIATION_INFO_NAME;
	}
	
	
}
