package nts.uk.ctx.office.dom.favorite.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyDto;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.SequenceMasterImport;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

public class FavoriteSpecifyDomainServiceTestHelper {

	/**
	 * 
	 * @return お気に入りの指定 FavoriteSpecify
	 */
	public static FavoriteSpecify mockFavoriteSpecify() {
		FavoriteSpecifyDto memento = new FavoriteSpecifyDto(
				"favoriteName", 
				"creatorId", 
				GeneralDateTime.FAKED_NOW, 
				0,
				Collections.emptyList(),
				0);
		FavoriteSpecify domain = new FavoriteSpecify();
		domain.getMemento(memento);
		return domain;
	}
	
	/**
	 * Mock [R-1] 社員の職場IDを取得する
	 */
	public static Map<String, String> mockRequireGetEmployeesWorkplaceId(){
		Map<String, String> employeesWorkplaceId = new HashMap<>();
		employeesWorkplaceId.put("sid", "wspId");
		return employeesWorkplaceId;
	}

	/**
	 *Mock [R-2]職場情報を取得する
	 */
	public static Map<String, WorkplaceInforImport> mockRequireGetWorkplaceInfo(){
		WorkplaceInforImport info = new WorkplaceInforImport(
				"workplaceId",
				"hierarchyCode",
				"workplaceCode",
				"workplaceName",
				"workplaceDisplayName",
				"workplaceGenericName",
				"workplaceExternalCode"
				);
		Map<String, WorkplaceInforImport> workplaceInfor = new HashMap<>();
		workplaceInfor.put("sid", info);
		return workplaceInfor;
	}

	/**
	 * Mock [R-3] 社員の職位を取得する
	 */
	public static Map<String, EmployeeJobHistImport> mockRequireGetPositionBySidsAndBaseDate(){
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder()
				.employeeId("employeeId")
				.jobTitleID("jobTitleID")
				.jobTitleName("jobTitleName")
				.sequenceCode("sequenceCode")
				.startDate(GeneralDate.today())
				.endDate(GeneralDate.today())
				.jobTitleCode("jobTitleCode")
				.build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("sid", emp);
		return positionBySidsAndBaseDate;
	}


	/**
	 * Mock [R-4]職位の序列を取得する
	 */
	public static List<SequenceMasterImport> mockRequireGetRankOfPosition(){
		SequenceMasterImport sequence = new SequenceMasterImport("companyId", 0, "sequenceCode", "sequenceName");
		List<SequenceMasterImport> rankOfPosition = new ArrayList<>();
		rankOfPosition.add(sequence);
		return rankOfPosition;
	}


	/**
	 * Mock [R-5] 個人情報を取得する
	 */
	public static Map<String, EmployeeBasicImport> mockRequireGetPersonalInformation(){
		EmployeeBasicImport empImport = new EmployeeBasicImport("sid", "pid", "name", "code");
		Map<String, EmployeeBasicImport> personalInformation = new HashMap<>();
		personalInformation.put("sid", empImport);
		return personalInformation;
	}

}
