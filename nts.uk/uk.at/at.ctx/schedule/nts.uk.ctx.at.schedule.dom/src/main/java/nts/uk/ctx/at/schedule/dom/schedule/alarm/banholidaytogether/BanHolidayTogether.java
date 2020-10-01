package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * 同日休日禁止
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class BanHolidayTogether implements DomainAggregate{
	/** 対象組織  */
	private final TargetOrgIdenInfor targetOrg;
	
	/** コード */
	private final BanHolidayTogetherCode banHolidayTogetherCode;
	
	/** 名称 */
	private BanHolidayTogetherName banHolidayTogetherName;
	
	/** 稼働日の参照先 */
	private Optional<ReferenceCalendar> workDayReference;
	
	/** 最低限出勤すべき人数 */
	private MinNumberEmployeeTogether minOfWorkingEmpTogether;
	
	/** 同日の休日取得を禁止する社員 */
	private List<String> empsCanNotSameHolidays;
	

	
	/**
	 * 作成する
	 * @param targetOrg 対象組織
	 * @param banHolidayCode コード
	 * @param banHolidayName 名称
	 * @param workDayReference 稼働日の参照先
	 * @param minNumberOfEmployeeToWork 最低限出勤すべき人数
	 * @param empsCanNotSameHolidays 同日の休日取得を禁止する社員
	 * @return
	 */
	public static BanHolidayTogether create(TargetOrgIdenInfor targetOrg, 
			BanHolidayTogetherCode banHolidayCode,
			BanHolidayTogetherName banHolidayName,
			Optional<ReferenceCalendar> workDayReference,
			MinNumberEmployeeTogether minNumberOfEmployeeToWork,
			List<String> empsCanNotSameHolidays) {
		
		if(empsCanNotSameHolidays.size() < 2) {
			throw new BusinessException("Msg_1885");
		}
		
		if(empsCanNotSameHolidays.size() < minNumberOfEmployeeToWork.v()) {
			throw new BusinessException("Msg_1886");
		}
		
		return new BanHolidayTogether(targetOrg, banHolidayCode, 
				banHolidayName, workDayReference,
				minNumberOfEmployeeToWork,
				empsCanNotSameHolidays);
	}
	
}
