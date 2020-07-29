package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.ArrayList;
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
	public static List<String> sortBySpecSortingOrder(Require require, GeneralDate ymd, List<String> lstEmpId,
			SortSetting sortSetting) {
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
		/*
		 * $リストのリスト = List: 社員IDリスト $並び順 in $並び替え設定.並び替え優先順:
		 */
		List<List<String>> listEmpIDs = new ArrayList<List<String>>();
		listEmpIDs.add(lstEmpId);

		List<SortOrder> litsOrderedList = sortSetting.getOrderedList().stream().map(c -> c.getSortOrder())
				.collect(Collectors.toList());
		for (SortOrder item : litsOrderedList) {
			switch (item) {
			case SCHEDULE_TEAM: {
				/*
				 * case スケジュールチーム: [prv-2] スケジュールチームで社員を並び替える(require, 社員IDリスト,
				 * $リストのリスト)
				 */
				listEmpIDs = sortEmpByScheduleTeam(require, lstEmpId, listEmpIDs);
				break;
			}
			case RANK: {
				/*
				 * case ランク: [prv-3] ランクで社員を並び替える(require, 社員IDリスト, $リストのリスト)
				 */
				listEmpIDs = sortEmpByRank(require, lstEmpId, listEmpIDs);
				break;
			}
			case LISENCE_ATR: {
				/*
				 * case 免許区分: [prv-4] 免許区分で社員を並び替える(require, 基準日, 社員IDリスト,
				 * $リストのリスト)
				 */
				listEmpIDs = sortEmpByLicenseClassification(require, ymd, lstEmpId, listEmpIDs);
				break;
			}
			case POSITION: {
				/*
				 * case 職位: [prv-5] 職位で社員を並び替える(require, 基準日, 社員IDリスト, $リストのリスト)
				 */
				listEmpIDs = sortEmpByPosition(require, ymd, lstEmpId, listEmpIDs);
				break;

			}
			default: {//CLASSIFY
				/*
				 * case 分類: [prv-6] 分類で社員を並び替える(require, 基準日, 社員IDリスト, $リストのリスト)
				 */
				listEmpIDs = sortEmpByClassification(require, ymd, lstEmpId, listEmpIDs);
				break;
			}
			}
		}
		return listEmpIDs.stream().flatMap(mapper -> mapper.stream()).collect(Collectors.toList());

	}

	/**
	 * [prv-2] スケジュールチームで社員を並び替える //Sắp xếp employee theo "schedule Team"
	 * 
	 * @param require
	 * @param empIDs
	 * @param listEmpID
	 * @return
	 */
	private static List<List<String>> sortEmpByScheduleTeam(Require require, List<String> empIDs,
			List<List<String>> listEmpIDs) {
		List<List<String>> listResult = new ArrayList<>();
		//$所属チームリスト = require.所属スケジュールチームを取得する($社員IDリスト)
		List<BelongScheduleTeam> listBelongScheduleTeam  = require.get(empIDs);
		
		List<String> listEmpIDBelongSchedule = listBelongScheduleTeam.stream().map(c -> c.getEmployeeID()).collect(Collectors.toList());
		for (List<String> list :listEmpIDs){
			//$所属していない社員IDリスト = $社員リスト: except $所属チームリスト.constains($)
			List<String> lstUnBelongSchedule =  list.stream().filter(x -> !listEmpIDBelongSchedule.contains(x)).collect(Collectors.toList());
			//$所属している社員リスト = $所属チームリスト: filter $社員リスト.constains($)																
			List<BelongScheduleTeam>  lstBelongScheduleTeam = listBelongScheduleTeam.stream().filter(x -> list.contains(x.getEmployeeID())).collect(Collectors.toList());
			//Sort by ScheduleTeamCd
			List<BelongScheduleTeam> lstBelongScheduleTeamSorted = lstBelongScheduleTeam.stream()
					.sorted((x,y) -> x.getScheduleTeamCd().compareTo(y.getScheduleTeamCd()))
					.collect(Collectors.toList());
			//	:groupingBy $.チームコード  values :map $.社員ID
			List<List<String>> result = lstBelongScheduleTeamSorted.stream().collect(Collectors.groupingBy(BelongScheduleTeam ::getScheduleTeamCd))
					.values().stream().map(lst -> {
						return lst.stream().map(t -> t.getEmployeeID()).collect(Collectors.toList());
					}).collect(Collectors.toList());
			result.add(lstUnBelongSchedule);
			listResult.addAll(result);
		}
		return listResult;
	}

	/**
	 * [prv-3] ランクで社員を並び替える
	 * 
	 * @param require
	 * @param empIDs
	 * @param listEmpID
	 * @return
	 */
	private static List<List<String>> sortEmpByRank(Require require, List<String> empIDs,
			List<List<String>> listEmpIDs) {
		List<List<String>> result = new ArrayList<>();
		//$ランクの優先順 = require.ランクの優先順を取得する()														
		/*if $ランクの優先順.empty																				
		return リストのリスト*/
		Optional<RankPriority> rankPriority = require.getRankPriority();
		if(!rankPriority.isPresent()){
			return listEmpIDs;
		}
		//$社員ランクリスト = require.社員ランクを取得する(社員IDリスト)		
		List<EmployeeRank> lstEmpRank = require.getAll(empIDs) ;
		List<String> lstEmpRankID = lstEmpRank.stream().map(c->c.getSID()).collect(Collectors.toList());
		
		for(List<String> list : listEmpIDs){
			//$未付与社員IDリスト = $社員リスト: except $社員ランクリスト.contains($)
			List<String> listEmpUnassigned = list.stream().filter(x-> !lstEmpRankID.contains(x)).collect(Collectors.toList());
			//$付与済社員リスト = $社員ランクリスト: filter $社員リスト.contains($)															
			List<EmployeeRank> listEmployeeRank = lstEmpRank.stream().filter(x -> list.contains(x.getSID())).collect(Collectors.toList()); 
			List<String> listRankCode = rankPriority.get().getListRankCd().stream().map(x -> x.v()).collect(Collectors.toList());
			//		$並び替えた社員IDリスト = $ランクの優先順.優先順リスト: map $																								
			//$付与済社員リスト: filter $.ランクコード == $		
			List<EmployeeRank> listEmployeeRankResult = listEmployeeRank.stream().filter(x->listRankCode.contains(x.getEmplRankCode().v())).collect(Collectors.toList());
			
			List<String> lstSortEmpID =  listEmployeeRankResult.stream().map(c->c.getSID()).collect(Collectors.toList());
			//		$並び替えた社員IDリスト.add($未付与社員IDリスト)											
			lstSortEmpID.addAll(listEmpUnassigned);	
			result.add(lstSortEmpID);
		}
		return result;
	}

	/**
	 * [prv-4] 免許区分で並び替える
	 * 
	 * @param require
	 * @param ymd
	 * @param empIDs
	 * @param listEmpID
	 * @return
	 */
	private static List<List<String>> sortEmpByLicenseClassification(Require require, GeneralDate ymd,
			List<String> empIDs, List<List<String>> listEmpIDs) {
		List<List<String>> listResult = new ArrayList<>();
		//$社員免許区分リスト = DS.社員の免許区分を取得する.取得する(基準日, 社員IDリスト) :filter $.免許区分.isPresent	
		List<EmpLicenseClassification> listEmpLicenseClassification = GetEmpLicenseClassificationService
				.get(require, ymd, empIDs).stream().filter(x -> x.getOptLicenseClassification().isPresent())
				.collect(Collectors.toList());
		List<String> listEmpIDEmpLicenseClassification = listEmpLicenseClassification.stream().map(x->x.getEmpID()).collect(Collectors.toList()); 
		//$社員リスト in リストのリスト: map	
		for(List<String> list :listEmpIDs ) {
			//$未付与社員IDリスト = $社員リスト: except $社員免許区分リスト.contains($)
			List<String> listEmpUnassigned = list.stream().filter(x->!listEmpIDEmpLicenseClassification.contains(x)).collect(Collectors.toList());
			//$付与済社員リスト = $社員免許区分リスト: filter $社員リスト.contains($)	
			List<EmpLicenseClassification> listEmpAssigned = listEmpLicenseClassification.stream().filter(x-> list.contains(x.getEmpID())).collect(Collectors.toList());
			
			//$並び替えた社員IDリスト = $付与済社員リスト: sort $.免許区分 ASC
			List<EmpLicenseClassification> listEmpLicenseClassificationSorted = listEmpAssigned.stream()
					.sorted((x, y) -> x.getOptLicenseClassification().get().value - y.getOptLicenseClassification().get().value)
					.collect(Collectors.toList());
			//groupingBy $.分類コード.values: map $.社員ID	
			List<List<String>> result = listEmpLicenseClassificationSorted.stream().collect(Collectors.groupingBy(c->c.getOptLicenseClassification().get().value))
					.values().stream().map(lst -> {
						return lst.stream().map(t -> t.getEmpID()).collect(Collectors.toList());
					}).collect(Collectors.toList());
			result.add(listEmpUnassigned);
			// return $並び替えた社員リスト
			listResult.addAll(result);
			
		}
		return listResult;
	}

	/**
	 * [prv-5] 職位で並び替える
	 * 
	 * @param require
	 * @param ymd
	 * @param empIDs
	 * @param listEmpID
	 * @return
	 */
	private static List<List<String>> sortEmpByPosition(Require require, GeneralDate ymd, List<String> empIDs,
			List<List<String>> listEmpIDs) {
		List<List<String>> listResult = new ArrayList<>();
		//	$社員職位リスト = require.社員の職位を取得する(基準日, 社員IDリスト)
		List<EmployeePosition> listEmployeePosition =  require.getPositionEmp(ymd, empIDs);
		List<String> listEmpIDPosition = listEmployeePosition.stream().map(c->c.getEmpID()).collect(Collectors.toList());
		//	$職位マスタリスト = require.会社の職位を取得する(基準日)
		List<PositionImport> listPositionImport = require.getCompanyPosition(ymd);
		
		//$社員リスト in リストのリスト: map
		for(List<String> list :listEmpIDs ) {
			//$職位がない社員IDリスト = $社員リスト: except $社員職位リスト.contains($)
			List<String> listEmpUnpositions = list.stream().filter(x->!listEmpIDPosition.contains(x)).collect(Collectors.toList());
			//$職位がある社員リスト = $社員職位リスト: filter $社員リスト.contains($)	
			List<EmployeePosition> listEmpPosition = listEmployeePosition.stream().filter(x-> list.contains(x.getEmpID())).collect(Collectors.toList());
			List<String> listPositionID = listPositionImport.stream().map(x->x.getJobId()).collect(Collectors.toList());
			//$並び替えた社員IDリスト = $職位マスタリスト: map											
			List<EmployeePosition> listPositionImportSorted = listEmpPosition.stream().filter(c->listPositionID.contains(c.getJobtitleID())).collect(Collectors.toList());
			List<String> result = listPositionImportSorted.stream().map(c->c.getEmpID()).collect(Collectors.toList());
			//$並び替えた社員IDリスト.add($職位がない社員IDリスト)
			result.addAll(listEmpUnpositions);
			//return $並び替えた社員リスト
			listResult.add(result);
		}
		return listResult;
	}

	/**
	 * [prv-6] 分類で社員を並び替える //Sắp xếp employee theo phân loại "classification"
	 * 
	 * @param require
	 * @param ymd
	 * @param empIDs
	 * @param listEmpID
	 * @return
	 */
	private static List<List<String>> sortEmpByClassification(Require require, GeneralDate ymd, List<String> empIDs,
			List<List<String>> listEmpIDs) {
		
		List<List<String>> listResult = new ArrayList<>();
		//$社員分類リスト = require.社員の分類を取得する(年月日, 社員IDリスト)
		List<EmpClassifiImport> listEmpClassifiImport =  require.get(ymd, empIDs);
		List<String> listEmpIDClassifi = listEmpClassifiImport.stream().map(c->c.getEmpID()).collect(Collectors.toList());
		// $社員リスト in リストのリスト: map
		for(List<String> list :listEmpIDs ) {
			// $分類がない社員IDリスト = $社員リスト: except $社員分類リスト.contains($)
			List<String> listEmpUnclassification = list.stream().filter(x->!listEmpIDClassifi.contains(x)).collect(Collectors.toList());
			// $分類がある社員リスト = $社員分類リスト: filter $社員リスト.contains($)															
			List<EmpClassifiImport> listEmpclassification = listEmpClassifiImport.stream().filter(x-> list.contains(x.getEmpID())).collect(Collectors.toList());
			//	sort $.分類コード ASC	
			List<EmpClassifiImport> listEmpclassificationSorted = listEmpclassification.stream()
					.sorted((x, y) -> x.getClassificationCode().compareTo(y.getClassificationCode()))
					.collect(Collectors.toList());
			//	groupingBy $.分類コード.values: map $.社員ID	
			List<List<String>> result = listEmpclassificationSorted.stream().collect(Collectors.groupingBy(EmpClassifiImport::getClassificationCode))
					.values().stream().map(lst -> {
						return lst.stream().map(t -> t.getEmpID()).collect(Collectors.toList());
					}).collect(Collectors.toList());
			// $並び替えた社員IDリスト.add($分類がない社員IDリスト)												
			result.add(listEmpUnclassification);
			// return $並び替えた社員リスト
			listResult.addAll(result);
		}
		
		return listResult;
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
