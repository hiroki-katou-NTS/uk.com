/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeInformationExport.
 */
// 社員情報
@Builder
@Data
public class EmployeeInformationImport {

    /**
     * The employee id.
     */
    String employeeId; // 社員ID

    /**
     * The employee code.
     */
    String employeeCode; // 社員コード

    /**
     * The business name.
     */
    String businessName; // ビジネスネーム

    /**
     * The business name Kana.
     */
    String businessNameKana; // ビジネスネームカナ

    /**
     * The workplace.
     */
    WorkplaceImport workplace; // 所属職場

    /**
     * The classification.
     */
    ClassificationImport classification; // 所属分類

    /**
     * The department.
     */
    DepartmentImport department; // 所属部門

    /**
     * The position.
     */
    PositionImport position; // 所属職位

    /**
     * The employment.
     */
    EmploymentImport employment; // 所属雇用

    /**
     * The employment cls.
     */
    Integer employmentCls; // 就業区分

    //個人ID
    String personID;

    //社員名
    String employeeName;

    //顔写真ファイル
    FacePhotoFileImport avatarFile;

    //誕生日
    GeneralDate birthday;

    //年齢
    int age;

    int gender;

}
