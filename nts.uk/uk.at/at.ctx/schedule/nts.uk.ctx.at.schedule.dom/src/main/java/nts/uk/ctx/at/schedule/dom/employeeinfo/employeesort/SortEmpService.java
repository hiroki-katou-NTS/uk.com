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
 * 社員を並び替える
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
		if(sortPriorities.size() == 1){
			long start1 = System.nanoTime();
			result = sort(require,ymd,lstEmpId,sortSetting);
			System.out.println("time run sortService "+  ((System.nanoTime() - start1 )/1000000) + "ms");
		}else{
			long start2 = System.nanoTime();
			result = sortMultiCond(require,ymd,lstEmpId,sortSetting);
			System.out.println("time run sortService  "+  ((System.nanoTime() - start2 )/1000000) + "ms");
		}
		return result;
	}
	
	private static List<String> sort(Require require, GeneralDate ymd, List<String> empIDs,
			SortSetting sortSetting) {
		
		List<String> result         = new ArrayList<>();
		OrderedList  sortPriorities = sortSetting.getOrderedList().get(0);
		switch (sortPriorities.getType()) {
		case SCHEDULE_TEAM:		 
			result = sortByTeams(require, empIDs, sortPriorities);
			break;
		case RANK:		 
			result = sortByRank(require, empIDs, sortPriorities);
			break;
		case LISENCE_ATR:		 
			result = sortByLicenseCls(require, empIDs, ymd, sortPriorities);
			break;
		case POSITION:		 
			result = sortEmpByPosition(require, ymd, empIDs);
			break;
		case CLASSIFY:		 
			result = sortEmpByClassification(require, ymd, empIDs);
			break;
		}
		return result;
	}
	
	private static List<String> sortMultiCond(Require require, GeneralDate ymd, List<String> empIDs,
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
				compare2  = Comparator.comparing(EmployeeInfo::getEmplRankCode, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case LISENCE_ATR:
				compare2  = Comparator.comparing(EmployeeInfo::getOptLicenseClassification, Comparator.nullsLast(Comparator.naturalOrder()));
				break;
			case POSITION:
				compare2  = Comparator.comparing(EmployeeInfo::getJobtitleID, Comparator.nullsLast(Comparator.naturalOrder()));
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
		List<EmployeeRank> listEmployeeRank = new ArrayList<>();
		List<EmpLicenseClassification> listEmpLicenseCls = new ArrayList<>();
		List<EmployeePosition> listEmployeePosition = new ArrayList<>();
		List<EmpClassifiImport> listEmpClassifiImport = new ArrayList<>();
		
		List<OrderedList> sortPriorities = sortSetting.getOrderedList();
		
		for (OrderedList condition : sortPriorities) {
			switch (condition.getType()) {
			case SCHEDULE_TEAM:
				listBelongScheduleTeam = require.get(empIDs).stream()
						.filter(x -> x.getScheduleTeamCd() != null).collect(Collectors.toList());
				break;
			case RANK:
				listEmployeeRank = require.getAll(empIDs).stream()
						.filter(x -> x.getEmplRankCode() != null).collect(Collectors.toList());
				Optional<RankPriority> rankPriority = require.getRankPriority();
				if (rankPriority.isPresent()) {
					List<String > listRankCode = rankPriority.get().getListRankCd().stream().map(i ->i.toString()).collect(Collectors.toList());
					listEmployeeRank.sort(Comparator.comparing(v-> listRankCode.indexOf(v.getEmplRankCode().v())));
				}
				break;
			case LISENCE_ATR:
				listEmpLicenseCls = GetEmpLicenseClassificationService.get(require, ymd, empIDs).stream()
						.filter(x -> x.getOptLicenseClassification().isPresent()).collect(Collectors.toList());
				break;
			case POSITION:
				listEmployeePosition =  require.getPositionEmp(ymd, empIDs);
				List<PositionImport> listPositionImport = require.getCompanyPosition(ymd);
				List<String> listjobIdPriority = listPositionImport.stream().map(x->x.getJobId()).collect(Collectors.toList());
				listEmployeePosition.sort(Comparator.comparing(v-> listjobIdPriority.indexOf(v.getJobtitleID())));
				break;
			case CLASSIFY:
				listEmpClassifiImport =  require.get(ymd, empIDs);
				break;
			}
		}
		
		for (String sid : empIDs) {
			Optional<BelongScheduleTeam> team = listBelongScheduleTeam.stream().filter(i -> i.getEmployeeID().equals(sid)).findFirst();
			Optional<EmployeeRank> rank = listEmployeeRank.stream().filter(i -> i.getSID().equals(sid)).findFirst();
			Optional<EmpLicenseClassification> empLicenseCls = listEmpLicenseCls.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmployeePosition> employeePosition = listEmployeePosition.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmpClassifiImport> empClassifiImport = listEmpClassifiImport.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			
			EmployeeInfo employeeInfo = EmployeeInfo.builder()
					.empId(sid)
					.scheduleTeamCd(team.isPresent() ? team.get().getScheduleTeamCd().toString() : null)
					.emplRankCode(rank.isPresent() ? rank.get().getEmplRankCode().toString() : null)
					.optLicenseClassification(empLicenseCls.isPresent() ? empLicenseCls.get().getOptLicenseClassification().get().value : null)
					.jobtitleID(employeePosition.isPresent() ? employeePosition.get().getJobtitleID() : null)
					.classificationCode(empClassifiImport.isPresent() ? empClassifiImport.get().getClassificationCode() : null)
					.build();
			result.add(employeeInfo);
		}
		return result;
	}

	private static List<String> sortByTeams(Require require, List<String> empIDs, OrderedList sortPriorities) {
		List<BelongScheduleTeam> listDomain = require.get(empIDs).stream().filter(x -> x.getScheduleTeamCd() != null)
				.collect(Collectors.toList());
		List<String> listSidDomain = listDomain.stream().map(i -> i.getEmployeeID()).collect(Collectors.toList());
		List<String> result = new ArrayList<>();
		
		List<String> lstSidUnBelongSchedule =  empIDs.stream().filter(x -> !listSidDomain.contains(x)).collect(Collectors.toList());
		
		Comparator<BelongScheduleTeam> compare = Comparator.comparing(e -> e.getScheduleTeamCd());
		List<String> sortedListSidASC = listDomain.stream().sorted(compare).map(i -> i.getEmployeeID()).collect(Collectors.toList());
		
		result.addAll(sortedListSidASC);
		result.addAll(lstSidUnBelongSchedule);
		return result;
	}
	
	private static List<String> sortByRank(Require require, List<String> empIDs, OrderedList sortPriorities) {
		List<EmployeeRank> listDomain  = require.getAll(empIDs).stream().filter(x -> x.getEmplRankCode() != null)
				.collect(Collectors.toList());
		List<String> listSidDomain = listDomain.stream().map(i -> i.getSID()).collect(Collectors.toList());
		List<String> result = new ArrayList<>();
		
		List<String> lstSidUnEmployeeRank =  empIDs.stream().filter(x -> !listSidDomain.contains(x)).collect(Collectors.toList());
		
		Optional<RankPriority> rankPriority = require.getRankPriority();
		if (!rankPriority.isPresent()) {
			result.addAll(listDomain.stream().map(i -> i.getSID()).collect(Collectors.toList()));
			result.addAll(lstSidUnEmployeeRank);
			return result;
		}
		
		List<String > listRankCode = rankPriority.get().getListRankCd().stream().map(i ->i.toString()).collect(Collectors.toList());
		listDomain.sort(Comparator.comparing(v-> listRankCode.indexOf(v.getEmplRankCode().v())));
		result.addAll(listDomain.stream().map(i -> i.getSID()).collect(Collectors.toList()));
		result.addAll(lstSidUnEmployeeRank);
		return result;
	}
	
	private static List<String> sortByLicenseCls(Require require, List<String> empIDs,GeneralDate ymd, OrderedList sortPriorities) {
		List<String> result = new ArrayList<>();
		
		List<EmpLicenseClassification> listEmpLicenseCls = GetEmpLicenseClassificationService
				.get(require, ymd, empIDs).stream().filter(x -> x.getOptLicenseClassification().isPresent())
				.collect(Collectors.toList());
		
		List<String> listSidEmpLicenseCls   = listEmpLicenseCls.stream().map(x->x.getEmpID()).collect(Collectors.toList()); 
		List<String> listSidUnEmpLicenseCls  = empIDs.stream().filter(x -> !listSidEmpLicenseCls.contains(x)).collect(Collectors.toList());
		
		Comparator<EmpLicenseClassification> compare = Comparator.comparing(e -> e.getOptLicenseClassification().get().value);
		
		List<String> sortedListASC = listEmpLicenseCls.stream()
				.sorted(compare).map(i -> i.getEmpID())
				.collect(Collectors.toList());
		
		result.addAll(sortedListASC);
		result.addAll(listSidUnEmpLicenseCls);
		return result;
	}
	
	private static List<String> sortEmpByPosition(Require require, GeneralDate ymd, List<String> empIDs) {
		List<String> result = new ArrayList<>();
		//	$社員職位リスト = require.社員の職位を取得する(基準日, 社員IDリスト)
		List<EmployeePosition> listEmployeePosition =  require.getPositionEmp(ymd, empIDs);
		List<String> listEmpIDPosition = listEmployeePosition.stream().map(c->c.getEmpID()).collect(Collectors.toList());
		//	$職位マスタリスト = require.会社の職位を取得する(基準日)
		List<PositionImport> listPositionImport = require.getCompanyPosition(ymd);
		List<String> listjobIdPriority = listPositionImport.stream().map(x->x.getJobId()).collect(Collectors.toList());
		
		List<String> listEmpIDUnPosition  = empIDs.stream().filter(x -> !listEmpIDPosition.contains(x)).collect(Collectors.toList());
		
		listEmployeePosition.sort(Comparator.comparing(v-> listjobIdPriority.indexOf(v.getJobtitleID())));
		result.addAll(listEmployeePosition.stream().map(i -> i.getEmpID()).collect(Collectors.toList()));
		result.addAll(listEmpIDUnPosition);
		return result;
	}
	
	private static List<String> sortEmpByClassification(Require require, GeneralDate ymd, List<String> empIDs) {
		
		List<String> result = new ArrayList<>();
		//$社員分類リスト = require.社員の分類を取得する(年月日, 社員IDリスト)
		List<EmpClassifiImport> listEmpClassifiImport =  require.get(ymd, empIDs);
		List<String> listEmpIDClassifi = listEmpClassifiImport.stream().map(c->c.getEmpID()).collect(Collectors.toList());
		
		List<String> listEmpIDUnClassifi  = empIDs.stream().filter(x -> !listEmpIDClassifi.contains(x)).collect(Collectors.toList());
		
		Comparator<EmpClassifiImport> compare = Comparator.comparing(e -> e.getClassificationCode());
		
		List<String> sortedListASC = listEmpClassifiImport.stream()
				.sorted(compare).map(i -> i.getEmpID())
				.collect(Collectors.toList());
		
		result.addAll(sortedListASC);
		result.addAll(listEmpIDUnClassifi);
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
		 * --------------------------------http://192.168.50.4:3000/issues/110607
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
