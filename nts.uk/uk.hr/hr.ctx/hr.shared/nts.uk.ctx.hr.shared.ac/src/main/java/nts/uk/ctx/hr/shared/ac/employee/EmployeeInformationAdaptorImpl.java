package nts.uk.ctx.hr.shared.ac.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.classification.AffCompanyHistItemExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.ctx.bs.employee.pub.department.aff.AffDepartmentPub;
import nts.uk.ctx.bs.employee.pub.department.aff.RequestList643Export;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentInforExport;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentPub;
import nts.uk.ctx.bs.employee.pub.employee.PersonInfoJhn001Export;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.jobtitle.AffJobTitleBasicExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.hr.shared.dom.adapter.ClassificationImport;
import nts.uk.ctx.hr.shared.dom.adapter.DepartmentImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfo;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmploymentImport;
import nts.uk.ctx.hr.shared.dom.adapter.FacePhotoFileImport;
import nts.uk.ctx.hr.shared.dom.adapter.PositionImport;
import nts.uk.ctx.hr.shared.dom.adapter.WorkplaceImport;
import nts.uk.ctx.hr.shared.dom.employee.AffCompanyHistItemImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.query.pub.classification.ClassificationExport;
import nts.uk.query.pub.department.DepartmentExport;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.query.pub.employee.FacePhotoFileExport;
import nts.uk.query.pub.employement.EmploymentExport;
import nts.uk.query.pub.position.PositionExport;
import nts.uk.query.pub.workplace.WorkplaceExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInformationAdaptorImpl implements EmployeeInformationAdaptor {

	@Inject
	private EmployeeInformationPub empInfo;
	
	@Inject
	private SyEmployeePub empPub;
	
	@Inject
	private AffDepartmentPub affDepartmentPub;
	
	@Inject
	private DepartmentPub departmentPub;
	
	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	@Inject
	private  SyClassificationPub syClassificationPub;

	@Override
	public List<EmployeeInformationImport> getEmployeeInfos(Optional<List<String>> pIds, List<String> sIds,
			GeneralDate baseDate, Optional<Boolean> getDepartment, Optional<Boolean> getPosition,
			Optional<Boolean> getEmployment) {

		return this.empInfo.getEmployeeInfos(pIds, sIds, baseDate, getDepartment, getPosition, getEmployment).stream()
				.map(x -> toImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeInformationImport> find(EmployeeInfoQueryImport param) {

		EmployeeInformationQueryDto query = convertQuery(param);

		return this.empInfo.find(query).stream().map(c -> this.toImport(c)).collect(Collectors.toList());
	}

	private EmployeeInformationImport toImport(EmployeeInformationExport x) {
		return EmployeeInformationImport.builder().employeeId(x.getEmployeeId()).employeeCode(x.getEmployeeCode())
				.gender(x.getGender()).businessName(x.getBusinessName()).businessNameKana(x.getBusinessNameKana())
				.workplace(toWorkplace(x.getWorkplace())).classification(toClassification(x.getClassification()))
				.department(toDepartment(x.getDepartment())).position(toPosition(x.getPosition()))
				.employment(toEmployment(x.getEmployment())).employmentCls(x.getEmploymentCls())
				.personID(x.getPersonID()).employeeName(x.getEmployeeName()).avatarFile(toAvatarFile(x.getAvatarFile()))
				.birthday(x.getBirthday()).age(x.getAge()).build();
	}

	private FacePhotoFileImport toAvatarFile(FacePhotoFileExport ex) {

		if (ex == null) {
			return null;
		}

		return FacePhotoFileImport.builder().thumbnailFileID(ex.getThumbnailFileID())
				.facePhotoFileID(ex.getFacePhotoFileID()).build();
	}

	private EmploymentImport toEmployment(EmploymentExport ex) {
		if (ex == null)
			return null;

		return new EmploymentImport(ex.getEmploymentCode(), ex.getEmploymentName());
	}

	private PositionImport toPosition(PositionExport ex) {
		if (ex == null)
			return null;

		return new PositionImport(ex.getPositionId(), ex.getPositionCode(), ex.getPositionName());
	}

	private DepartmentImport toDepartment(DepartmentExport ex) {
		if (ex == null)
			return null;

		return new DepartmentImport(ex.getCompanyId(), ex.isDeleteFlag(), ex.getDepartmentHistoryId(),
				ex.getDepartmentId(), ex.getDepartmentCode(), ex.getDepartmentName(), ex.getDepartmentGeneric(),
				ex.getDepartmentName(), ex.getHierarchyCode(), ex.getDepartmentExternalCode());
	}

	private ClassificationImport toClassification(ClassificationExport ex) {
		if (ex == null)
			return null;

		return new ClassificationImport(ex.getClassificationCode(), ex.getClassificationName());
	}

	private WorkplaceImport toWorkplace(WorkplaceExport export) {
		if (export == null) {
			return WorkplaceImport.builder().build();
		}
		return new WorkplaceImport(export.getWorkplaceId(), export.getWorkplaceCode(), export.getWorkplaceGenericName(),
				export.getWorkplaceName());
	}

	private EmployeeInformationQueryDto convertQuery(EmployeeInfoQueryImport query) {
		return EmployeeInformationQueryDto.builder().employeeIds(query.getEmployeeIds())
				.referenceDate(query.getReferenceDate()).toGetWorkplace(query.isToGetWorkplace())
				.toGetDepartment(query.isToGetDepartment()).toGetPosition(query.isToGetPosition())
				.toGetEmployment(query.isToGetEmployment()).toGetClassification(query.isToGetClassification())
				.toGetEmploymentCls(query.isToGetEmploymentCls()).build();
	}

	// get thông tin cá nhân dùng cho đăng ký report màn JHN001.A
	@Override
	public EmployeeInfo getInfoEmp(String sid, String cid, GeneralDate baseDate) {
		EmployeeInfo empInfo = new EmployeeInfo();
		
		String companyId = AppContexts.user().companyId();
		//アルゴリズム[社員IDから個人社員基本情報を取得]を実行する 
		//(THực hiện thuật toán "Lấy Basic informationl employees cá nhân từ employee ID" )
		PersonInfoJhn001Export personInfoExport = empPub.getEmployeeInfo(sid);
		if (personInfoExport != null) {
			empInfo.inputPid = personInfoExport.getPid();
			empInfo.inputSid = personInfoExport.getEmployeeId();
			empInfo.inputScd = personInfoExport.getEmployeeCode();
			empInfo.inputBussinessName = personInfoExport.getBusinessName();
			
			empInfo.appliPerId = personInfoExport.getPid();
			empInfo.appliPerSid = personInfoExport.getEmployeeId();
			empInfo.appliPerScd = personInfoExport.getEmployeeCode();
			empInfo.appliPerBussinessName = personInfoExport.getBusinessName();
		}
		
		// アルゴリズム[[RQ643]社員ID(List)と基準日から部門IDを取得する]を実行する 
		//(Thực hiện thuật toán "[[RQ643] Get department ID from employee ID (List) and base date]")
		List<RequestList643Export> affDepartment = affDepartmentPub.getAffDeptHistByEmpIdAndBaseDate(Arrays.asList(sid), baseDate);
		
		if (!affDepartment.isEmpty()) {
			List<String> depIds = affDepartment.stream().map(i -> i.getDepartmentId()).collect(Collectors.toList());
			// アルゴリズム[[No.563]部門IDから部門の情報をすべて取得する]を実行する 
			//(THực hiện thuật toán [[No.563] Get all department information from department ID)
			List<DepartmentInforExport> listDepInfo =  departmentPub.getDepartmentInforByDepIds(companyId, depIds, baseDate);
			empInfo.appDevId   = listDepInfo.get(0).getDepartmentId();
			empInfo.appDevCd   = listDepInfo.get(0).getDepartmentCode();
			empInfo.appDevName = listDepInfo.get(0).getDepartmentName();
		}
		
		Optional<AffJobTitleBasicExport> affPosition = syJobTitlePub.getBySidAndBaseDate(sid, baseDate);
		if (affPosition.isPresent()) {
			empInfo.appPosId   = affPosition.get().getJobTitleId();
			empInfo.appPosCd   = affPosition.get().getJobTitleCode();
			empInfo.appPosName = affPosition.get().getJobTitleName();
		}
		return empInfo;
	}

	@Override
	public EmployeeInfo getInfoEmp(String sid) {
		EmployeeInfo empInfo = new EmployeeInfo();
		PersonInfoJhn001Export personInfoExport = empPub.getEmployeeInfo(sid);
		
		if (personInfoExport == null) {
			return null;
		}
		
		if (personInfoExport != null) {
			empInfo.inputPid = personInfoExport.getPid();
			empInfo.inputSid = personInfoExport.getEmployeeId();
			empInfo.inputScd = personInfoExport.getEmployeeCode();
			empInfo.inputBussinessName = personInfoExport.getBusinessName();
		}
		return empInfo;
	}

	@Override
	public List<AffCompanyHistItemImport> getByIDAndBasedate(GeneralDate baseDate, List<String> listempID) {
		
		List<AffCompanyHistItemExport> listExport = this.syClassificationPub.getByIDAndBasedate(baseDate, listempID); 
		
		if (listExport.isEmpty()) {
			return new ArrayList<AffCompanyHistItemImport>();
		}
		
		List<AffCompanyHistItemImport> result = listExport.stream().map(item -> {
			AffCompanyHistItemImport export = AffCompanyHistItemImport.builder()
					.employeeID(item.getEmployeeID())
					.historyId(item.getHistoryId())
					.destinationData(item.isDestinationData())
					.startDate(item.getStartDate())
					.endDate(item.getEndDate()).build();
			return export;
		}).collect(Collectors.toList());
		
		return result;
	}
}
