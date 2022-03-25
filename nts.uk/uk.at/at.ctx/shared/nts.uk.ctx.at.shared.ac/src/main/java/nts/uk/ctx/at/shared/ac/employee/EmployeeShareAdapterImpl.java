/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeInfoWantToBeGet;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.ClassificationImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.DepartmentImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmploymentImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.PositionImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.WorkplaceImported;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

/**
 * The Class EmployeeShareAdapterImpl.
 */
@Stateless
public class EmployeeShareAdapterImpl implements EmployeeAdapter {

	@Inject private SyEmployeePub employeePub;

	@Inject private EmployeeInformationPub employeeInformationPub;



	/**
	 * 社員コードから社員IDを取得する
	 * @param companyId 会社ID
	 * @param employeeCodes 社員コードリスト
	 * @return Map<社員コード, 社員ID>
	 */
	@Override
	public Map<String, String> getEmployeeIdFromCode(String companyId, List<String> employeeCodes) {

		return this.employeePub.findSdataMngInfoByEmployeeCodes(companyId, employeeCodes).stream()
			.collect(Collectors.toMap(
							EmployeeDataMngInfoExport::getEmployeeCode
						,	EmployeeDataMngInfoExport::getEmployeeId)
					);

	}


	/**
	 * 社員IDリストから社員コードと表示名を取得する
	 * @param employeeIds 社員IDリスト
	 * @return List<社員コードと表示名Imported>
	 */
	@Override
	public List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds) {

		return this.employeePub.getByListSid(employeeIds).stream()
				.map(x -> new EmployeeCodeAndDisplayNameImport(x.getSid(), x.getScd(), x.getBussinessName()))
				.collect(Collectors.toList());

	}


	/**
	 * 社員の情報を取得する
	 * ※ UKDesign.クエリ.社員の情報を取得.アルゴリズム.社員の情報を取得する.<<Public>> 社員の情報を取得する
	 * @param employeeIds 社員IDリスト
	 * @param baseDate 年月日
	 * @param param 取得したい社員情報
	 * @return List<社員情報Imported>
	 */
	@Override
	public List<EmployeeInfoImported> getEmployeeInfo(List<String> employeeIds, GeneralDate baseDate, EmployeeInfoWantToBeGet param) {

		val queryParam = EmployeeInformationQueryDto.builder()
				.toGetEmploymentCls(false)
				.employeeIds(employeeIds)
				.referenceDate(baseDate)
				.toGetWorkplace(param.isGetWorkplace())
				.toGetDepartment(param.isGetDepartment())
				.toGetPosition(param.isGetJobTitle())
				.toGetEmployment(param.isGetEmployment())
				.toGetClassification(param.isGetClassification())
				.build();

		return this.employeeInformationPub.find(queryParam).stream()
				.map( item -> toImport(item) )
				.collect(Collectors.toList());

	}

	private EmployeeInfoImported toImport(EmployeeInformationExport item) {

		// 職場
		val workplace = Optional.ofNullable(item.getWorkplace())
				.map( wkp -> new WorkplaceImported(
								wkp.getWorkplaceId()
							,	wkp.getWorkplaceCode()
							,	wkp.getWorkplaceGenericName()
							,	wkp.getWorkplaceName()
						) );
		// 部門
		val department = Optional.ofNullable(item.getDepartment())
				.map( dpt -> new DepartmentImported(
								dpt.getDepartmentId()
							,	dpt.getDepartmentCode()
							,	dpt.getDepartmentGeneric()
							,	dpt.getDepartmentDisplayName()
						) );
		// 職位
		val position = Optional.ofNullable(item.getPosition())
				.map( pos -> new PositionImported(
								pos.getPositionId()
							,	pos.getPositionCode()
							,	pos.getPositionName()
						) );
		// 雇用
		val employment = Optional.ofNullable(item.getEmployment())
				.map( emp -> new EmploymentImported(
								emp.getEmploymentCode()
							,	emp.getEmploymentName()
						) );
		// 分類
		val classification = Optional.ofNullable(item.getClassification())
				.map( cls -> new ClassificationImported(
								cls.getClassificationCode()
							,	cls.getClassificationName()
						) );

		return EmployeeInfoImported.builder()

				.employeeId( item.getEmployeeId() )
				.employeeCode( item.getEmployeeCode() )

				.businessName( item.getBusinessName() )
				.businessNameKana( item.getBusinessNameKana() )

				.workplace( workplace )
				.department( department )
				.position( position )
				.employment( employment )
				.classification( classification )

				.build();

	}
}
