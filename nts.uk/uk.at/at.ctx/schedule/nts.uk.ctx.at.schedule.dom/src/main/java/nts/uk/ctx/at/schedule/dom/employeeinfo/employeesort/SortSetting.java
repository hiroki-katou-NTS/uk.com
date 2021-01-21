package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;

/**
 * 並び替え設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.社員並び替え.並び替え設定
 * @author HieuLt
 *
 */

@AllArgsConstructor
public class SortSetting implements DomainAggregate {
	@Getter
	/** 会社ID **/
	private final String companyID;
	@Getter
	@Setter
	/** 並び替え優先順 **/
	private List<OrderedList> orderedList;

	/**
	 * 作る
	 * @param companyID 会社ID
	 * @param orderedList 並び替え優先順
	 * @return
	 */
	public static SortSetting create(String companyID, List<OrderedList> orderedList) {

		List<OrderedList> list = orderedList.stream().distinct().collect(Collectors.toList());
		// inv-1: @並び替え優先順.種類は重複しないこと
		if (list.size() < orderedList.size()) {
			throw new BusinessException("Msg_1612");
		}
		// inv-2: 1 <= @並び替え優先順.Size <= 5
		if ((orderedList.size() == 0) || orderedList.size() > 5) {
			throw new BusinessException("Msg_1613");
		}
		return new SortSetting(companyID, orderedList);
	}
	
	/**
	 * 並び替える (Sắp xếp)
	 * @param require
	 * @param baseDate 基準日
	 * @param sids 社員IDリスト
	 * @return
	 */
	public List<String> sort(Require require, GeneralDate baseDate, List<String> sids) {
		
		List<EmployeeInfo> getListEmpInfo = getListEmpInfo(require, baseDate, sids);
		Comparator<EmployeeInfo> compare = null;
		
		for (OrderedList condition : this.orderedList) {
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
	
	
	private List<EmployeeInfo> getListEmpInfo(Require require, GeneralDate baseDate, List<String> sids) {
		List<EmployeeInfo> result = new ArrayList<>();
		
		List<BelongScheduleTeam> belongScheduleTeams = new ArrayList<>();
		List<EmployeeRankDto> employeeRankResults = new ArrayList<>();
		List<EmpLicenseClassification> empLicenseClss = new ArrayList<>();
		List<EmployeePositionDto> employeePositionResults = new ArrayList<>();
		List<EmpClassifiImport> empClassifiImports = new ArrayList<>();
		
		for (OrderedList condition : this.orderedList) {
			switch (condition.getType()) {
			case SCHEDULE_TEAM:
				belongScheduleTeams = require.getScheduleTeam(sids).stream()
						.filter(x -> x.getScheduleTeamCd() != null).collect(Collectors.toList());
				belongScheduleTeams.sort(Comparator.comparing(v-> sids.indexOf(v.getEmployeeID())));
				break;
			case RANK:
				List<EmployeeRankDto> employeeRankDtos = new ArrayList<>();
				List<EmployeeRank> employeeRanks = require.getEmployeeRanks(sids).stream()
						.filter(x -> x.getEmplRankCode() != null).collect(Collectors.toList());
				employeeRanks.sort(Comparator.comparing(v-> sids.indexOf(v.getSID())));
				employeeRankDtos = employeeRanks.stream().map(m -> {
					return new EmployeeRankDto(m.getSID(), m.getEmplRankCode().toString(),0);
				}).collect(Collectors.toList());
				
				Optional<RankPriority> rankPriority = require.getRankPriorities();
				if (rankPriority.isPresent()) {
					List<String > listRankCode = rankPriority.get().getListRankCd().stream().map(i ->i.toString()).collect(Collectors.toList());
					employeeRankDtos.forEach((EmployeeRankDto employeeRankDto) -> {
						if (!listRankCode.contains(employeeRankDto.emplRankCode)) {
							employeeRankDto.setEmplRankCode(null);
						} 
			        });
					
					List<EmployeeRankDto> listEmployeeRank1 = employeeRankDtos.stream().filter(i -> i.getEmplRankCode() != null).collect(Collectors.toList());
					List<EmployeeRankDto> listEmployeeRank2 = employeeRankDtos.stream().filter(i -> i.getEmplRankCode() == null).collect(Collectors.toList());
					
					listEmployeeRank1.sort(Comparator.comparing(v-> listRankCode.indexOf(v.getEmplRankCode())));
					
					listEmployeeRank1.forEach((EmployeeRankDto employeeRankDto) -> {
							employeeRankDto.setPriority(listRankCode.indexOf(employeeRankDto.emplRankCode));
			        });
					
					listEmployeeRank2.forEach((EmployeeRankDto employeeRankDto) -> {
						employeeRankDto.setPriority(null);
					});
							
					employeeRankResults.addAll(listEmployeeRank1);
					employeeRankResults.addAll(listEmployeeRank2);
				}
				break;
			case LISENCE_ATR:
				empLicenseClss = GetEmpLicenseClassificationService.get(require, baseDate, sids).stream()
						.filter(x -> x.getOptLicenseClassification().isPresent()).collect(Collectors.toList());
				empLicenseClss.sort(Comparator.comparing(v-> sids.indexOf(v.getEmpID())));
				break;
			case POSITION:
				List<EmployeePositionDto> listEmployeePositionDto = new ArrayList<>();
				
				List<EmployeePosition> listEmployeePosition =  require.getPositionEmps(baseDate, sids);
				listEmployeePosition.sort(Comparator.comparing(v-> sids.indexOf(v.getEmpID())));
				
				listEmployeePositionDto = listEmployeePosition.stream().map(m -> {
					return new EmployeePositionDto(m.getEmpID(), m.getJobtitleCode().toString(),0);
				}).collect(Collectors.toList());
				
				List<PositionImport> listPositionImport = require.getCompanyPosition(baseDate);
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
					
					employeePositionResults.addAll(listEmployeePosition1);
					employeePositionResults.addAll(listEmployeePosition2);
				} else {
					employeePositionResults = listEmployeePositionDto;
				}
				break;
			case CLASSIFY:
				empClassifiImports =  require.getEmpClassifications(baseDate, sids);
				empClassifiImports.sort(Comparator.comparing(v-> sids.indexOf(v.getEmpID())));
				break;
			}
		}
		
		for (String sid : sids) {
			Optional<BelongScheduleTeam> team = belongScheduleTeams.stream().filter(i -> i.getEmployeeID().equals(sid)).findFirst();
			Optional<EmployeeRankDto> rank = employeeRankResults.stream().filter(i -> i.getSID().equals(sid)).findFirst();
			Optional<EmpLicenseClassification> empLicenseCls = empLicenseClss.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmployeePositionDto> employeePosition = employeePositionResults.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			Optional<EmpClassifiImport> empClassifiImport = empClassifiImports.stream().filter(i -> i.getEmpID().equals(sid)).findFirst();
			
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
		 * [R-1] 所属スケジュールチームを取得する //Lấy "schedule Team" trực thuộc
		 * 所属スケジュールチームRepository.*get ( 会社ID, List<社員ID> )
		 * 
		 * @param companyID
		 * @param empIDs
		 * @return
		 */
		List<BelongScheduleTeam> getScheduleTeam( List<String> empIDs);

		/**
		 * [R-2] 社員ランクを取得する //Lấy "Employee Rank" 社員ランクRepository.*get
		 * (List<社員ID> )
		 * 
		 * @param lstSID
		 * @return
		 */
		List<EmployeeRank> getEmployeeRanks(List<String> lstSID);

		/**
		 * [R-3] ランクの優先順を取得する //Lấy rank priority RankRepository
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<RankPriority> getRankPriorities();

		/**
		 * [R-4] 社員の職位を取得する //Lấy "job title" của employee
		 * <Adapter>社員の職位.取得する(年月日, List<社員ID>)
		 * 
		 * @param ymd
		 * @param lstEmp
		 * @return
		 */
		List<EmployeePosition> getPositionEmps(GeneralDate ymd, List<String> lstEmp);

		/**
		 * [R-5] 会社の職位を取得する //Lấy "job title" của company
		 * <Adapter>社員の職位.取得する(年月日) QA
		 * --------------------------------http://192.168.50.4:3000/issues/110607
		 *
		 * 
		 */
		List<PositionImport> getCompanyPosition(GeneralDate ymd);

		/**
		 * [R-6] 社員の分類を取得する //Lấy "classification" của employee
		 * <Adapter>社員分類Adapter.取得する(年月日, List<社員ID>)
		 * 
		 * @param ymd
		 * @param lstEmpId
		 * @return
		 */
		List<EmpClassifiImport> getEmpClassifications(GeneralDate ymd, List<String> lstEmpId);
	}	

}
