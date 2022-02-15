package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeCodeAndDisplayNameImport;

/**
 * 様式９で並び替える
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９で並び替える
 * @author lan_lt
 *
 */
public class SortByForm9Service {
	/**
	 * 並び替える
	 * @param require
	 * @param baseDate 基準日
	 * @param employeeIds 社員IDリスト
	 * @return
	 */
	public static List<String> sort(Require require, GeneralDate baseDate, List<String> employeeIds){
		val empLicenseClassifications = GetEmpLicenseClassificationService.get( require, baseDate, employeeIds );
		val empJobTitleHistories = require.getEmployeeJobTitle( baseDate, employeeIds );
		val empBasicInfos = require.getEmployeeCodeAndDisplayNameImportByEmployeeIds( employeeIds );
		
		val sortEmpInfos = createForm9SortEmployeeInfo( employeeIds, empLicenseClassifications, empJobTitleHistories, empBasicInfos );
		
		Comparator<Form9SortEmployeeInfo> compare;
		compare = Comparator.comparing( Form9SortEmployeeInfo::getLicenseClassification, Comparator.nullsLast(Comparator.naturalOrder()) );
		compare = compare.thenComparing( Form9SortEmployeeInfo::getJobTitleCode, Comparator.nullsLast(Comparator.naturalOrder()) );
		compare = compare.thenComparing( Form9SortEmployeeInfo::getEmployeeCode, Comparator.nullsLast(Comparator.naturalOrder()) );
		
		return sortEmpInfos.stream().sorted( compare )
				.map( empInfo -> empInfo.getEmployeeId() )
				.collect( Collectors.toList() );
	}
	
	/**
	 * 様式９のソートの社員情報を作る
	 * @param employeeIds 社員IDリスト
	 * @param empLicenseClassifications 社員免許区分リスト
	 * @param empJobTitleHistories 社員職位リスト
	 * @param empBasicInfos 社員基本情報リスト
	 * @return
	 */
	private static List<Form9SortEmployeeInfo> createForm9SortEmployeeInfo(
				List<String> employeeIds
			,	List<EmpLicenseClassification> empLicenseClassifications
			,	List<SharedAffJobTitleHisImport> empJobTitleHistories
			,	List<EmployeeCodeAndDisplayNameImport> empBasicInfos) {
		
		return employeeIds.stream().map( employeeId ->{
			val licenseCls = empLicenseClassifications.stream()
					.filter( license -> license.getEmpID().equals(employeeId)
							&& license.getOptLicenseClassification().isPresent() )
					.findFirst()
					.map( c -> c.getOptLicenseClassification().get() ).orElse(null);
			val jobTitleCode = empJobTitleHistories.stream()
					.filter( job -> job.getEmployeeId().equals(employeeId) )
					.findFirst()
					.map( job -> job.getJobTitleCode() ).orElse(null);
			val employeeCode = empBasicInfos.stream()
					.filter( emp -> emp.getEmployeeId().equals(employeeId) )
					.findFirst()
					.map( emp -> emp.getEmployeeCode() ).orElse(null);
			
			return new Form9SortEmployeeInfo(employeeId, licenseCls, jobTitleCode, employeeCode);
			
		}).collect(Collectors.toList());
		
	}

	public static interface Require extends GetEmpLicenseClassificationService.Require{
		
		/**
		 * 社員職位を取得する
		 * @param baseDate 基準日
		 * @param employeeIds 社員IDリスト
		 * @return
		 */
		List<SharedAffJobTitleHisImport> getEmployeeJobTitle(GeneralDate baseDate, List<String> employeeIds);
		
		/**
		 * 社員コードと氏名を取得する
		 * @param employeeIds 社員IDリスト
		 * @return
		 */
		List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds);
	}
}
