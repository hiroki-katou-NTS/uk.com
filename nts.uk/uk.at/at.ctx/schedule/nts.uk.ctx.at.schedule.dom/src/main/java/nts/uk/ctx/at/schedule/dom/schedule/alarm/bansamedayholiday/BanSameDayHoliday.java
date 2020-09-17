package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * 同日休日禁止
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class BanSameDayHoliday implements DomainAggregate{
	//対象組織
	private final TargetOrgIdenInfor targetOrg;
	
	//コード
	private final BanSameDayHolidayCode banSameDayHolidayCode;
	
	//名称
	private BanSameDayHolidayName banSameDayHolidayName;
	
	//稼働日の参照先
	private Optional<BussinessCalendarReference> workDayReference;
	
	//同日の休日取得を禁止する社員
	private List<String> empsCanNotSameHolidays;
	
	//最低限出勤すべき人数
	private SameDayMinOfNumberEmployee minNumberOfEmployeeToWork;
	
	/**
	 * [C-1] 作成する
	 * @param targetOrg
	 * @param banHolidayCode
	 * @param banHolidayName
	 * @param workDayReference
	 * @param empsCanNotSameHolidays
	 * @param minNumberOfEmployeeToWork
	 * @return
	 */
	public static BanSameDayHoliday create(TargetOrgIdenInfor targetOrg, 
			BanSameDayHolidayCode banHolidayCode,
			BanSameDayHolidayName banHolidayName,
			Optional<BussinessCalendarReference> workDayReference,
			List<String> empsCanNotSameHolidays,
			SameDayMinOfNumberEmployee minNumberOfEmployeeToWork) {
		
		if(empsCanNotSameHolidays.isEmpty() || empsCanNotSameHolidays.size() <= 1) {
			throw new BusinessException("Msg_1885");
		}
		
		if(empsCanNotSameHolidays.size() < minNumberOfEmployeeToWork.v().intValue()) {
			throw new BusinessException("Msg_1886");
		}
		
		return new BanSameDayHoliday(targetOrg, banHolidayCode, 
				banHolidayName, workDayReference,
				empsCanNotSameHolidays, minNumberOfEmployeeToWork);
	}
	
}
