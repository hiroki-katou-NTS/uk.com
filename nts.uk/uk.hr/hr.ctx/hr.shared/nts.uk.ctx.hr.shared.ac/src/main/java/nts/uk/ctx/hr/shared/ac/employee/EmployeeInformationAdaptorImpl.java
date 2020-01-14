package nts.uk.ctx.hr.shared.ac.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.adapter.ClassificationImport;
import nts.uk.ctx.hr.shared.dom.adapter.DepartmentImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmploymentImport;
import nts.uk.ctx.hr.shared.dom.adapter.FacePhotoFileImport;
import nts.uk.ctx.hr.shared.dom.adapter.PositionImport;
import nts.uk.ctx.hr.shared.dom.adapter.WorkplaceImport;
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

@Stateless
public class EmployeeInformationAdaptorImpl implements EmployeeInformationAdaptor {

	@Inject
	private EmployeeInformationPub empInfo;

	@Override
	public List<EmployeeInformationImport> getEmployeeInfos(Optional<List<String>> pIds, List<String> sIds,
			GeneralDate baseDate) {

		return this.empInfo.getEmployeeInfos(pIds, sIds, baseDate).stream().map(x -> toImport(x))
				.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeInformationImport> find(EmployeeInfoQueryImport param) {

		EmployeeInformationQueryDto query = convertQuery(param);

		return this.empInfo.find(query).stream().map(c -> this.toImport(c)).collect(Collectors.toList());
	}

	private EmployeeInformationImport toImport(EmployeeInformationExport x) {
		return EmployeeInformationImport.builder().employeeId(x.getEmployeeId()).employeeCode(x.getEmployeeCode())
				.gender(x.getGender())
				.businessName(x.getBusinessName()).businessNameKana(x.getBusinessNameKana())
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

}
