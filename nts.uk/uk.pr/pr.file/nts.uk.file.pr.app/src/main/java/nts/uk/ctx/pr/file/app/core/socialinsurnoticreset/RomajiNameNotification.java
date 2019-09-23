package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHis;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialIns;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RomajiNameNotification {
    EmpNameReport empNameReport;
    EmpFamilySocialIns empFamilySocialIns;
    FamilyMember familyMember;
    EmpBasicPenNumInfor empBasicPenNumInfor;
    PersonInfo personInfo;
    CompanyInfor companyInfor;
    GeneralDate date;
    String personTarget;
    //RomajiNameNotiCreSetExport romajiNameNotiCreSetExport;
    SocialInsuranceOffice socialInsuranceOffice;
    RomajiNameNotiCreSetting romajiNameNotiCreSetting;
}
