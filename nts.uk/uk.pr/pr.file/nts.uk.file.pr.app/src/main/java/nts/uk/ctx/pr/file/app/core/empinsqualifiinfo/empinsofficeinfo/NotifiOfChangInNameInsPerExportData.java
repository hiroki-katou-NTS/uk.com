package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * 雇用保険被保険者住所変更届
 */
public class NotifiOfChangInNameInsPerExportData {
    /**
     * 会社情報
     */
    private CompanyInfor companyInfor;
    /**
     * 個人.個人名グループ.個人名.個人名ローマ字.氏名
     */
    private String fullName;
    /**
     * 個人.個人名グループ.個人名.個人名ローマ字.氏名カナ
     */
    private String fullNameKana;
    /**
     * 個人.個人名グループ.個人名.氏名
     */
    private String name;
    /**
     * 個人.個人名グループ.個人名.氏名カナ
     */
    private String nameKana;
    /**
     * 個人.個人名グループ.個人届出名称.氏名
     */
    private String reportFullName;
    /**
     * 個人.個人名グループ.個人届出名称.氏名カナ
     */
    private String reportFullNameKana;
    /**
     * 個人.個人名グループ.個人旧氏名.氏名
     */
    private String oldName;
    /**
     * 個人.個人名グループ.個人旧氏名.氏名カナ
     */
    private String oldNameKana;
    /**
     * 個人.性別
     */
    private int gender;
    /**
     * 個人.生年月日
     */
    private String brithDay;
    /**
     * 労働保険事業所
     */
    private LaborInsuranceOffice laborInsuranceOffice;
    /**
     * 変更年月日
     */
    private String changeDate;
    /**
     * 社員雇用保険履歴
     */
    private EmpInsHist empInsHist;
    /**
     * 雇用保険番号情報
     */
    private EmpInsNumInfo empInsNumInfo;

    private String employeeCode;

    private EmpInsReportSetting empInsReportSetting;

    private String fillingDate;
}
