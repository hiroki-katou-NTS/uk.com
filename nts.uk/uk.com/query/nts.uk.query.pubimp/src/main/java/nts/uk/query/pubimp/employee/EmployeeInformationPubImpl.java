/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.filemanagement.services.PersonFileManagementDto;
import nts.uk.ctx.pereg.dom.filemanagement.services.PersonFileManagementService;
import nts.uk.ctx.sys.auth.dom.wkpmanager.EmpInfoAdapter;
import nts.uk.ctx.sys.auth.dom.wkpmanager.dom.EmpBasicInfoImport;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.query.pub.classification.ClassificationExport;
import nts.uk.query.pub.department.DepartmentExport;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.query.pub.employee.FacePhotoFileExport;
import nts.uk.query.pub.employement.EmploymentExport;
import nts.uk.query.pub.position.PositionExport;
import nts.uk.query.pub.workplace.WorkplaceExport;

/**
 * The Class EmployeeInformationPubImpl.
 */
@Stateless
public class EmployeeInformationPubImpl implements EmployeeInformationPub {

	/** The repo. */
	@Inject
	private EmployeeInformationRepository repo;
	
	@Inject
	private EmpInfoAdapter empInfoAdapter;
	
	@Inject
	private PersonFileManagementService perFileService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.EmployeeInformationPub#find(nts.uk.query.pub.
	 * employee.EmployeeInformationQueryDto)
	 */
	@Override
	public List<EmployeeInformationExport> find(EmployeeInformationQueryDto param) {
		return this.repo.find(this.toQueryModel(param)).stream().map(item -> toExport(item)).collect(Collectors.toList());
	}

	/**
	 * To query model.
	 *
	 * @param dto the dto
	 * @return the employee information query
	 */
	private EmployeeInformationQuery toQueryModel(EmployeeInformationQueryDto dto) {
		return EmployeeInformationQuery.builder()
				.employeeIds(dto.getEmployeeIds())
				.referenceDate(dto.getReferenceDate())
				.toGetClassification(dto.isToGetClassification())
				.toGetDepartment(dto.isToGetDepartment())
				.toGetEmployment(dto.isToGetEmployment())
				.toGetEmploymentCls(dto.isToGetEmploymentCls())
				.toGetPosition(dto.isToGetPosition())
				.toGetWorkplace(dto.isToGetWorkplace())
				.build();
	}

	@Override
	public List<EmployeeInformationExport> getEmployeeInfos(Optional<List<String>> pIds, List<String> sIds,
			GeneralDate baseDate, Optional<Boolean> getDepartment, Optional<Boolean> getPosition, Optional<Boolean> getEmployment) {
		// 社員の情報を取得する (Get thông tin nhân viên/employee information)
		
		List<EmployeeInformationExport> empInfos = this.repo.find(EmployeeInformationQuery.builder()
				.employeeIds(sIds)
				.referenceDate(baseDate)
				.toGetWorkplace(false)
				.toGetDepartment(getDepartment.isPresent() ? getDepartment.get() : false)
				.toGetPosition(getPosition.isPresent() ? getPosition.get() : false)
				.toGetEmployment(getEmployment.isPresent() ? getEmployment.get() : false)
				.toGetClassification(false)
				.toGetEmploymentCls(false)
				.build()).stream().map(item -> toExport(item)).collect(Collectors.toList());;

		// 社員情報リストを作成して、<<QueryModel>>社員情報の値をセット (Tạo list thông tin nhân viên
		// để set giá trị của thông tin nhân viên <<QueryModel>>)
		
		List<String> lstSid = empInfos.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
		
		// 社員ID(List)から個人社員基本情報を取得(Lấy thông tin cá nhân cơ bản của employee từ
		// EmployeeID(List))
		
		List<EmpBasicInfoImport> empInfoLst = this.empInfoAdapter.getListPersonInfo(lstSid);

		// List<個人社員基本情報>から個人IDリストを取得(Lấy PersonIDList từ List<thông tin cá nhân
		// cơ bản của employee>)
		
		List<String> personIds = empInfoLst.stream().map(x -> x.getPId()).collect(Collectors.toList());
		
		//個人IDをキーに個人管理ファイルを取得する (Get file quản lý cá nhân/Personal file management list với ID cá nhân làm khóa)
		
		List<PersonFileManagementDto> files = this.perFileService.getPersonalFileManagementFromPID(personIds);
		
		
		empInfos.forEach(x -> {
			
			empInfoLst.stream().filter(empInfo -> empInfo.getEmployeeId().equals(x.getEmployeeId())).findFirst().ifPresent(empInfo -> {
				x.setPersonID(empInfo.getPId());
				x.setEmployeeName(empInfo.getNamePerson());
				x.setBirthday(empInfo.getBirthDay());
				//年齢を計算する
				x.setAge(calcAge(empInfo.getBirthDay()));
			});
			
			files.stream().filter(file -> file.getPId().equals(x.getPersonID())).findFirst().ifPresent(file -> {
				x.setAvatarFile(new FacePhotoFileExport(
						file.getAvatarFile().isPresent() ? file.getAvatarFile().get().getThumbnailFileID() : null,
						file.getAvatarFile().isPresent() ? file.getAvatarFile().get().getFacePhotoFileID() : null));
			});
		});

		return empInfos;
	}

	private int calcAge(GeneralDate birthDay) {
		Integer birthdate = birthDay.year() * 10000 + birthDay.month() * 100 + birthDay.day();

		GeneralDate today = GeneralDate.today();

		Integer targetdate = today.year() * 10000 + (today.month() + 1) * 100 + today.day();

		return new Double(Math.floor((targetdate - birthdate) / 10000)).intValue();
	}

	private EmployeeInformationExport toExport(EmployeeInformation item) {

		ClassificationExport cls = item.getClassification().isPresent() ? ClassificationExport.builder()
				.classificationCode(item.getClassification().get().getClassificationCode())
				.classificationName(item.getClassification().get().getClassificationName())
				.build() : null;

		WorkplaceExport wkp = item.getWorkplace().isPresent() ? WorkplaceExport.builder()
				.workplaceId(item.getWorkplace().get().getWorkplaceId())
				.workplaceCode(item.getWorkplace().get().getWorkplaceCode())
				.workplaceName(item.getWorkplace().get().getWorkplaceName())
				.workplaceGenericName(item.getWorkplace().get().getWorkplaceGenericName())
				.build() : null;

		DepartmentExport dep = item.getDepartment().isPresent() ? DepartmentExport.builder()
				.companyId(item.getDepartment().get().getCompanyId())
				.deleteFlag(item.getDepartment().get().isDeleteFlag())
				.departmentHistoryId(item.getDepartment().get().getDepartmentHistoryId())
				.departmentId(item.getDepartment().get().getDepartmentId())
				.departmentCode(item.getDepartment().get().getDepartmentCode())
				.departmentName(item.getDepartment().get().getDepartmentName())
				.departmentGeneric(item.getDepartment().get().getDepartmentGeneric())
				.departmentDisplayName(item.getDepartment().get().getDepartmentDisplayName())
				.hierarchyCode(item.getDepartment().get().getHierarchyCode())
				.departmentExternalCode(item.getDepartment().get().getDepartmentExternalCode())
				.build() : null;

		PositionExport pos = item.getPosition().isPresent() ? PositionExport.builder()
				.positionId(item.getPosition().get().getPositionId())
				.positionCode(item.getPosition().get().getPositionCode())
				.positionName(item.getPosition().get().getPositionName())
				.build(): null;

		EmploymentExport emp = item.getEmployment().isPresent() ? EmploymentExport.builder()
				.employmentCode(item.getEmployment().get().getEmploymentCode())
				.employmentName(item.getEmployment().get().getEmploymentName())
				.build() : null;

		return EmployeeInformationExport.builder()
			.businessName(item.getBusinessName())
			.businessNameKana(item.getBusinessNameKana())
			.employeeCode(item.getEmployeeCode())
			.employeeId(item.getEmployeeId())
			.gender(item.getGender())
			.classification(cls)
			.workplace(wkp)
			.department(dep)
			.position(pos)
			.employment(emp)
			.employmentCls(item.getEmploymentCls().orElse(null))
			.build();

	}

}
