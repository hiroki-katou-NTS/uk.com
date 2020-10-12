package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;

/**
 * DS_社員を並び替える
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * 
 * @author HieuLt
 */
public class SortEmpService {
	
	// [1] 並び順に基づいて社員を並び替える
	public static List<String> sortEmpTheirOrder(Require require, GeneralDate ymd, List<String> lstEmpId) {
		// $並び替え設定 = require.並び替え設定を取得する()
		Optional<SortSetting> sortSetting = require.get();
		// if $並び替え設定.empty---return 社員IDリスト
		if (!sortSetting.isPresent()) {
			return lstEmpId;
		}
		
		// return [prv-1] 並び替える(require, 基準日, 社員IDリスト, 並び替え設定)
		return rearranges(require, ymd, lstEmpId, sortSetting.get());
	}

	// [2] 並び順を指定して社員を並び替える
	public static List<String> sortBySpecSortingOrder(Require require, GeneralDate ymd, List<String> lstEmpId, SortSetting sortSetting) {
	
		// return [prv-1] 並び替える(require, 基準日, 社員IDリスト, 並び替え設定)
		return rearranges(require, ymd, lstEmpId, sortSetting);
	}
	
	/**
	 * [prv-1] 並び替える //Sắp xếp
	 * 
	 * @param require
	 * @param ymd
	 * @param lstEmpId
	 * @param sortSetting
	 * @return
	 */
	private static List<String> rearranges(Require require, GeneralDate ymd, List<String> lstEmpId,
			SortSetting sortSetting) {
		List<String> result = new ArrayList<>();
		List<OrderedList> sortPriorities = sortSetting.getOrderedList();
		if (sortPriorities.size() == 0)
			return lstEmpId;
		if (sortPriorities.size() > 0) {
			long start = System.nanoTime();
			result = sort(require, ymd, lstEmpId, sortSetting);
			System.out.println("time run sortService  " + ((System.nanoTime() - start) / 1000000) + "ms");
		}
		return result;
	}
	
	private static List<String> sort(Require require, GeneralDate ymd, List<String> empIDs,
			SortSetting sortSetting) {
		
		List<EmployeeInfo> getListEmpInfo = getListEmpInfo(require, ymd, empIDs, sortSetting);
		Comparator<EmployeeInfo> compare = null;
		List<OrderedList> sortPriorities = sortSetting.getOrderedList();
		for (OrderedList condition : sortPriorities) {
			Comparator<EmployeeInfo> compare2 = null;
			switch (condition.getType()) {
			case SCHEDULE_TEAM:
				compare2  = Comparator.comparing(EmployeeInfo::getScheduleTeamCd, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case RANK:
				compare2  = Comparator.comparing(EmployeeInfo::getEmplRankPriority, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case LISENCE_ATR:
				compare2  = Comparator.comparing(EmployeeInfo::getOptLicenseClassification, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case POSITION:
				compare2  = Comparator.comparing(EmployeeInfo::getJobtitlePriority, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case CLASSIFY:
				compare2  = Comparator.comparing(EmployeeInfo::getClassificationCode, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			}
			
			if (compare == null) {
				compare = compare2;
			} else {
				compare = compare.thenComparing(compare2);
			}
		}
		
		List<String> sortedIds = getListEmpInfo.stream()
				.sorted(compare)
				.map(e -> e.empId)
				.collect(Collectors.toList());
		
		return sortedIds;
	}
	
	private static List<EmployeeInfo> getListEmpInfo(Require require, GeneralDate ymd, List<String> empIDs,
			SortSetting sortSetting) {
		List<EmployeeInfo> result = new ArrayList<>();
		
		List<BelongScheduleTeam> listBelongScheduleTeam = new ArrayList<>();
		List<EmployeeRankDto> listEmployeeRankResult = new ArrayList<>();
		List<EmpLicenseClassification> listEmpLicenseCls = new ArrayList<>();
		List<EmployeePositionDto> listEmployeePositionResult = new ArrayList<>();
		List<EmpClassifiImport> listEmpClassifiImport = new ArrayList<>();
		
		List<OrderedList> sortPriorities = sortSetting.getOrderedList();
		
		for (OrderedList condition : sortPriorities) {
			switch (condition.getType()) {
			case SCHEDULE_TEAM:
				listBelongScheduleTeam = require.get(empIDs).stream()
						.filter(x -> x.getScheduleTeamCd() != null).collect(Collectors.toList());
				listBelongScheduleTeam.sort(Comparator.comparing(v-> empIDs.indexOf(v.getEmployeeID())));
				break;
			case RANK:
				List<EmployeeRankDto> listEmployeeRankDto = new ArrayList<>();
				List<EmployeeRank> listEmployeeRank = require.getAll(empIDs).stream()
						.filter(x -> x.getEmplRankCode() != null).collect(Collectors.toList());
				listEmployeeRank.sort(Comparator.comparing(v-> empIDs.indexOf(v.getSID())));
				listEmployeeRankDto = listEmployeeRank.stream().map(m -> {
					return new EmployeeRankDto(m.getSID(), m.getEmplRankCode().toString(),0);
				}).collect(Collectors.toList());
				
				Optional<RankPriority> rankPriority = require.getRankPriority();
				if (rankPriority.isPresent()) {
					List<String > listRankCode = rankPriority.get().getListRankCd().stream().map(i ->i.toString()).collect(Collectors.toList());
					listEmployeeRankDto.forEach((EmployeeRankDto employeeRankDto) -> {
						if (!listRankCode.contains(employeeRankDto.emplRankCode)) {
							employeeRankDto.setEmplRankCode(null);
						} 
			        });
					
					List<EmployeeRankDto> listEmployeeRank1 = listEmployeeRankDto.stream().filter(i -> i.getEmplRankCode() != null).collect(Collectors.toList());
					List<EmployeeRankDto> listEmployeeRank2 = listEmployeeRankDto.stream().filter(i -> i.getEmplRankCode() == null).collect(Collectors.toList());
					
					listEmployeeRank1.sort(Comparator.comparing(v-> listRankCode.indexOf(v.getEmplRankCode())));
					
					listEmployeeRank1.forEach((EmployeeRankDto employeeRankDto) -> {
							employeeRankDto.setPriority(listRankCode.indexOf(employeeRankDto.emplRankCode));
			        });
					
					listEmployeeRank2.forEach((EmployeeRankDto employeeRankDto) -> {
						employeeRankDto.setPriority(null);
					});
							
					listEmployeeRankResult.addAll(listEmployeeRank1);
					listEmployeeRankResult.addAll(listEmployeeRank2);
				}
				break;
			case LISENCE_ATR:
				listEmpLicenseCls = GetEmpLicenseClassificationService.get(require, ymd, empIDs).stream()
						.filter(x -> x.getOptLicenseClassification().isPresent()).collect(Collectors.toList());
				listEmpLicenseCls.sort(Comparator.comparing(v-> empIDs.indexOf(v.getEmpID())));
				break;
			case POSITION:
				List<EmployeePositionDto> listEmployeePositionDto = new ArrayList<>();
				
				List<EmployeePosition> listEmployeePosition =  require.getPositionEmp(ymd, empIDs);
				listEmployeePosition.sort(Comparator.comparing(v-> empIDs.indexOf(v.getEmpID())));
				
				listEmployeePositionDto = listEmployeePosition.stream().map(m -> {
					return new EmployeePositionDto(m.getEmpID(), m.getJobtitleCode().toString(),0);
				}).collect(Collectors.toList());
				
				List<PositionImport> listPositionImport = require.getCompanyPosition(ymd);
				if (!listPositionImport.isEmpty()) {
					List<String> listjobCodePriority = listPositionImport.stream().map(x -> x.getJobCd()).collect(Collectors.toList());
					listEmployeePositionDto.forEach((EmployeePositionDto employeePosition) -> {
						if (!listjobCodePriority.contains(employeePosition.getJobtitleCode())) {
							employeePosition.setJobtitleCode(null);
						} 
			        });
					
					List<EmployeePositionDto> listEmployeePosition1 = listEmployeePositionDto.stream().filter(i -> i.getJobtitleCode() != null).collect(Collectors.toList());
					List<EmployeePositionDto> listEmployeePosition2 = listEmployeePositionDto.stream().filter(i -> i.getJobtitleCode() == null).collect(Collectors.toList());
					
					listEmployeePosition1.sort(Comparator.comparing(v-> listjobCodePriority.indexOf(v.getJobtitleCode())));
					
					listEmployeePosition1.forEach((EmployeePositionDto employeePosition) -> {
						employeePosition.setPriority(listjobCodePriority.indexOf(employeePosition.getJobtitleCode()));
					});
				
					listEmployeePosition2.forEach((EmployeePositionDto employeePosition) -> {
						employeePosition.setPriority(null);
					});
					
					listEmployeePositionResult.addAll(listEmployeePosition1);
					listEmployeePositionResult.addAll(listEmployeePosition2);
				} else {
					listEmployeePositionResult = listEmployeePositionDto;
				}
				break;
			case CLASSIFY:
				listEmpClassifiImport =  require.get(ymd, empIDs);
				listEmpClassifiImport.sort(Comparator.comparing(v-> empIDs.indexOf(v.getEmpID())));
				break;
			}
		}
		
		for (String sid : empIDs) {
			Optional<BelongScheduleTeam> team = listBelongScheduleTeam.stream().filter(i -> i.getEmployeeID().equals(sid)).findFirst();
			Optional<EmployeeRankDto> rank = listEmployeeRankResult.stream().filter(i -> i.getSID().equals(sid)).findFirst();
			Optional<EmpLicenseClassification> empLicenseCls = listEmpLicenseCls.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmployeePositionDto> employeePosition = listEmployeePositionResult.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmpClassifiImport> empClassifiImport = listEmpClassifiImport.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			
			EmployeeInfo employeeInfo = EmployeeInfo.builder()
					.empId(sid)
					.scheduleTeamCd(team.isPresent() ? team.get().getScheduleTeamCd().toString() : null)
					.emplRankPriority(rank.isPresent() ? (rank.get().priority == null ? null : rank.get().priority) : null)
					.optLicenseClassification(empLicenseCls.isPresent() ? empLicenseCls.get().getOptLicenseClassification().get().value : null)
					.jobtitlePriority(employeePosition.isPresent() ? (employeePosition.get().priority == null ? null : employeePosition.get().priority) : null)
					.classificationCode(empClassifiImport.isPresent() ? empClassifiImport.get().getClassificationCode() : null)
					.build();
			result.add(employeeInfo);
		}
		return result;
	}

	public static interface Require extends GetEmpLicenseClassificationService.Require {
		/**
		 * [R-1] 並び替え設定を取得する //Lấy "sort setting" 並び替え設定Repository.get（会社ID）
		 * 
		 * @param companyID
		 * @return
		 */
		Optional<SortSetting> get();

		/**
		 * [R-2] 所属スケジュールチームを取得する //Lấy "schedule Team" trực thuộc
		 * 所属スケジュールチームRepository.*get ( 会社ID, List<社員ID> )
		 * 
		 * @param companyID
		 * @param empIDs
		 * @return
		 */
		List<BelongScheduleTeam> get( List<String> empIDs);

		/**
		 * [R-3] 社員ランクを取得する //Lấy "Employee Rank" 社員ランクRepository.*get
		 * (List<社員ID> )
		 * 
		 * @param lstSID
		 * @return
		 */
		List<EmployeeRank> getAll(List<String> lstSID);

		/**
		 * [R-4] ランクの優先順を取得する //Lấy rank priority RankRepository
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<RankPriority> getRankPriority();

		/**
		 * [R-5] 社員の職位を取得する //Lấy "job title" của employee
		 * <Adapter>社員の職位.取得する(年月日, List<社員ID>)
		 * 
		 * @param ymd
		 * @param lstEmp
		 * @return
		 */
		List<EmployeePosition> getPositionEmp(GeneralDate ymd, List<String> lstEmp);

		/**
		 * [R-6] 会社の職位を取得する //Lấy "job title" của company
		 * <Adapter>社員の職位.取得する(年月日) QA
		 * http://192.168.50.4:3000/issues/110607
		 *
		 * 
		 */
		List<PositionImport> getCompanyPosition(GeneralDate ymd);

		/**
		 * [R-7] 社員の分類を取得する //Lấy "classification" của employee
		 * <Adapter>社員分類Adapter.取得する(年月日, List<社員ID>)
		 * 
		 * @param ymd
		 * @param lstEmpId
		 * @return
		 */
		List<EmpClassifiImport> get(GeneralDate ymd, List<String> lstEmpId);
	}

}
