/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.query.pub.classification.ClassificationExport;
import nts.uk.query.pub.department.DepartmentExport;
import nts.uk.query.pub.employement.EmploymentExport;
import nts.uk.query.pub.position.PositionExport;
import nts.uk.query.pub.workplace.WorkplaceExport;

/**
 * The Class EmployeeInformationExport.
 */
// 社員情報
@Builder
@Data
public class EmployeeInformationExport implements Comparable<EmployeeInformationExport> {

	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

	/** The business name Kana. */
	String businessNameKana; // ビジネスネームカナ

	/** The workplace. */
	WorkplaceExport workplace; // 所属職場

	/** The classification. */
	ClassificationExport classification; // 所属分類

	/** The department. */
	DepartmentExport department; // 所属部門

	/** The position. */
	PositionExport position; // 所属職位

	/** The employment. */
	EmploymentExport employment; // 所属雇用

	/** The employment cls. */
	Integer employmentCls; // 就業区分
	
	//個人ID
	String personID;
	
	//社員名
	String employeeName;
	
	//顔写真ファイル
	FacePhotoFileExport avatarFile;
	
	//誕生日
	GeneralDate birthday;
	
	//年齢
	int age;
	
	int gender;

	@Override
	public int compareTo(EmployeeInformationExport o) {
		return this.employeeCode.compareTo(o.employeeCode);
	}
	
}
