package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
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
		if (sortSetting.isPresent()) {
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
		int sortOrder = sortSetting.getOrderedList().getSortOrder().value;
		switch (sortOrder) {
		case 0: {
			/*
			 * case スケジュールチーム: [prv-2] スケジュールチームで社員を並び替える(require, 社員IDリスト,
			 * $リストのリスト)
			 */
			listEmpIDs = sortEmpByScheduleTeam(require, lstEmpId, listEmpIDs);
		}
		case 1: {
			/*
			 * case ランク: [prv-3] ランクで社員を並び替える(require, 社員IDリスト, $リストのリスト)
			 */
			listEmpIDs = sortEmpByRank(require, lstEmpId, listEmpIDs);
		}
		case 2: {
			/*
			 * case 免許区分: [prv-4] 免許区分で社員を並び替える(require, 基準日, 社員IDリスト, $リストのリスト)
			 */
			listEmpIDs = sortEmpByLicenseClassification(require, ymd, lstEmpId, listEmpIDs);
		}
		case 3: {
			/*
			 * case 職位: [prv-5] 職位で社員を並び替える(require, 基準日, 社員IDリスト, $リストのリスト)
			 */
			listEmpIDs = sortEmpByPosition(require, ymd, lstEmpId, listEmpIDs);

		}
		case 4: {
			/*
			 * case 分類: [prv-6] 分類で社員を並び替える(require, 基準日, 社員IDリスト, $リストのリスト)
			 */
			listEmpIDs = sortEmpByClassification(require, ymd, lstEmpId, listEmpIDs);
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
			List<List<String>> listEmpID) {
		//Chờ QA http://192.168.50.4:3000/issues/110617
		return null;
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
		//$ランクの優先順 = require.ランクの優先順を取得する()	
		List<Rank>  listRank = require.getListRank();
		/*if $ランクの優先順.empty																				
		return リストのリスト	*/
		if(listRank.isEmpty()){
			return listEmpIDs;
		}
		
		return null;
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
		return null;
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
		return null;
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
		return null;
	}

	public static interface Require {
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
		Optional<BelongScheduleTeam> get( List<String> empIDs);

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
		Optional<RankPriority> getRankPriority(String companyId);

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
