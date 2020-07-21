package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;

/**
 * 個人条件の表示制御 
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * 
 * @author HieuLT
 * 
 */
@AllArgsConstructor
public class DisplayControlPersonalCondition implements DomainAggregate {

	@Getter
	/** 会社ID **/
	private final String companyID;
	@Getter
	/** List<条件表示制御> --- 条件表示制御リスト **/
	private List<PersonInforDisplayControl> listConditionDisplayControl;

	@Getter
	/** Optional<勤務予定の資格設定> 資格設定 **/
	private Optional<WorkscheQualifi> otpWorkscheQualifi;

	/**
	 * [C-1] 個人条件の表示制御
	 * 
	 * @param companyID
	 * @param listConditionDisplayControl
	 * @param otpWorkscheQualifi
	 * @return
	 */
	public static DisplayControlPersonalCondition get(String companyID,
			List<PersonInforDisplayControl> listConditionDisplayControl, Optional<WorkscheQualifi> otpWorkscheQualifi) {
		if ((listConditionDisplayControl.stream()
				.anyMatch(c -> c.getConditionATR().value != ConditionATRWorkSchedule.QUALIFICATION.value))
				&& (!otpWorkscheQualifi.isPresent())) {
			throw new BusinessException("Msg_1682");
		}
		return new DisplayControlPersonalCondition(companyID, listConditionDisplayControl, otpWorkscheQualifi);
	}

	//
	// [1] 個人条件の表示制御に対して必要な個人情報を取得する
	public List<PersonalCondition> acquireInforDisplayControlPersonalCondition(Require require,
			GeneralDate referenceDate, List<String> lstEmpId) {
		/*
		 * $社員チームリスト = DS_所属スケジュールチーム情報を取得する.取得する(require, 社員リスト) :map key
		 * $.社員ID value $
		 */
		List<EmpTeamInfor> lstEmpTeam = GetScheduleTeamInfoService.get(require, lstEmpId);
		Map<String, EmpTeamInfor> mapEmpTeamLst = lstEmpTeam.stream()
				.collect(Collectors.toMap(EmpTeamInfor::getEmployeeID, x -> x));

		/*
		 * $社員ランクリスト = DS_社員ランク情報を取得する.取得する(require, 社員リスト) :map key $.社員ID
		 * value $
		 */
		List<EmpRankInfor> lstRank = GetEmRankInforService.get(require, lstEmpId);
		Map<String, EmpRankInfor> mapEmpRankInfor = lstRank.stream()
				.collect(Collectors.toMap(EmpRankInfor::getEmpId, x -> x));
		/*
		 * $社員免許区分リスト = DS_社員の免許区分を取得する.取得する(require, 基準日, 社員リスト) :map key
		 * $.社員ID value $
		 */
		
		List<EmpLicenseClassification> lstEmpLicense = GetEmpLicenseClassificationService
				.get(require, referenceDate, lstEmpId);
		Map<String, EmpLicenseClassification> mapEmpLicenseClassification = lstEmpLicense.stream()
				.collect(Collectors.toMap(EmpLicenseClassification::getEmpID, x -> x));
			
			/*
			 * $社員チーム = $社員チームリスト.get($).チーム名称 $社員ランク = $社員ランクリスト.get($).ランク記号
			 * $社員免許区分 = $社員免許区分リスト.get($).免許区分 return 個人条件( $, $社員チーム, $社員ランク,
			 * $社員免許区分)
			 */
		return lstEmpId.stream().map(empId->{
			Optional<ScheduleTeamName> teamName = mapEmpTeamLst.get(empId).getOptScheduleTeamName();
			String empRank = mapEmpRankInfor.get(empId).getRankSymbol().v();
			Optional<LicenseClassification> mapEmpLicense = mapEmpLicenseClassification.get(empId)
					.getOptLicenseClassification();

			return new PersonalCondition(empId, Optional.ofNullable(teamName.get().v()),
					Optional.ofNullable(empRank), mapEmpLicense);
			
		}).collect(Collectors.toList());
	}

	public static interface Require extends GetScheduleTeamInfoService.Require, GetEmRankInforService.Require,
			GetEmpLicenseClassificationService.Require {
	}
}
