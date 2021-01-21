package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.ArrayList;
import java.util.HashMap;
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
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 個人条件の表示制御 Root
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
	public  List<PersonalCondition> acquireInforDisplayControlPersonalCondition(Require require,
			GeneralDate referenceDate, List<String> lstEmpId) {
		// 	$社員チームリスト
		Map<String, EmpTeamInfor> mapEmpTeam = new HashMap<>();
		
		// 	$社員ランクリスト
		Map<String, EmpRankInfor> mapEmpRankInfor = new HashMap<>();
		
		// $社員免許区分リスト
		Map<String, EmpLicenseClassification> mapEmpLicenseClassification = new HashMap<>();
		
		/*
		 * $社員チームリスト = DS_所属スケジュールチーム情報を取得する.取得する(require, 社員リスト) :map key
		 * $.社員ID value $
		 */
		if(this.getDisplayCategory(ConditionATRWorkSchedule.TEAM).equals(NotUseAtr.USE)) {
			List<EmpTeamInfor> lstEmpTeam = GetScheduleTeamInfoService.get(require, lstEmpId);
			mapEmpTeam = lstEmpTeam.stream()
					.collect(Collectors.toMap(EmpTeamInfor::getEmployeeID, x -> x));
		}

		/*
		 * $社員ランクリスト = DS_社員ランク情報を取得する.取得する(require, 社員リスト) :map key $.社員ID
		 * value $
		 */
		if(this.getDisplayCategory(ConditionATRWorkSchedule.RANK).equals(NotUseAtr.USE)) {
		List<EmpRankInfor> lstRank = GetEmRankInforService.get(require, lstEmpId);
		mapEmpRankInfor = lstRank.stream()
				.collect(Collectors.toMap(EmpRankInfor::getEmpId, x -> x));
		}
		/*
		 * $社員免許区分リスト = DS_社員の免許区分を取得する.取得する(require, 基準日, 社員リスト) :map key
		 * $.社員ID value $
		 */
		if(this.getDisplayCategory(ConditionATRWorkSchedule.LICENSE_ATR).equals(NotUseAtr.USE)) {
		List<EmpLicenseClassification> lstEmpLicense = GetEmpLicenseClassificationService
				.get(require, referenceDate, lstEmpId);
		mapEmpLicenseClassification = lstEmpLicense.stream()
				.collect(Collectors.toMap(EmpLicenseClassification::getEmpID, x -> x));
		}
			/*
			 * $社員チーム = $社員チームリスト.get($).チーム名称 $社員ランク = $社員ランクリスト.get($).ランク記号
			 * $社員免許区分 = $社員免許区分リスト.get($).免許区分 return 個人条件( $, $社員チーム, $社員ランク,
			 * $社員免許区分)
			 */
		List<PersonalCondition> conditions = new ArrayList<>();
		
		for(String empId : lstEmpId) {
			Optional<ScheduleTeamName> teamName = mapEmpTeam.get(empId) != null ? mapEmpTeam.get(empId).getOptScheduleTeamName() : Optional.empty();
			Optional<RankSymbol> empRank = mapEmpRankInfor.get(empId) != null ? mapEmpRankInfor.get(empId).getRankSymbol() : Optional.empty();
			Optional<LicenseClassification> mapEmpLicense = mapEmpLicenseClassification.get(empId) != null ? mapEmpLicenseClassification.get(empId)
					.getOptLicenseClassification() : Optional.empty();
			
			PersonalCondition condition = new PersonalCondition(empId, Optional.ofNullable(teamName.isPresent() ? teamName.get().v() : null),
					Optional.ofNullable(empRank.isPresent() ? empRank.get().v() : null), mapEmpLicense);
			
			conditions.add(condition);
		}
		return conditions;
	}
	
	/**
	 * [prv-1] 表示区分を取得する
	 * @param atrWorkSchedule
	 * @return
	 */
	private NotUseAtr getDisplayCategory(ConditionATRWorkSchedule atrWorkSchedule) {
		// note $条件表示制御
		Optional<PersonInforDisplayControl> displayControl = listConditionDisplayControl.stream().filter(x-> x.getConditionATR().equals(atrWorkSchedule)).findFirst();
		
			return displayControl.get().getDisplayCategory();

	}

	public static interface Require extends GetScheduleTeamInfoService.Require, GetEmRankInforService.Require,
			GetEmpLicenseClassificationService.Require {
	}
}
