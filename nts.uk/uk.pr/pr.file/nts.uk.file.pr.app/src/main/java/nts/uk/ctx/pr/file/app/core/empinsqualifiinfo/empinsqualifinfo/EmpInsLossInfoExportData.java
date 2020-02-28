package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpInsLossInfoExportData {

    /**
     * 帳票種別
     */
    private Integer formType;
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
     * 外国人在留履歴情報
     */
    private ForeignerResHistInfo foreignerResHistInfo;
    /**
     * 提出日
     */
    private GeneralDate fillingDate;
    /**
     * 現住所履歴項目
     */
    private CurrentPersonResidence currentPersonResidence;
    /**
     * 社員ID
     */
    private String sid;
    /**
     * 社員雇用保険履歴
     */
    private EmpInsHist empInsHist;
    /**
     * 雇用保険喪失時情報
     */
    private EmpInsLossInfo empInsLossInfo;
    /**
     * 雇用保険番号情報
     */
    private EmpInsNumInfo empInsNumInfo;

    private EmpInsReportSetting empInsReportSetting;

    private String periodOfStay;

    private Integer workCategory;

    private String nationality;

    private String residenceStatus;


}
