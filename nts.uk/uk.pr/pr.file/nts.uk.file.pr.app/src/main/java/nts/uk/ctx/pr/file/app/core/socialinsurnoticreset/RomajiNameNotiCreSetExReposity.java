package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;


import nts.arc.time.GeneralDate;

import java.util.List;

public interface RomajiNameNotiCreSetExReposity {
    List<RomajiNameNotiCreSetExport> getEmpNameReportList(List<String> empList, String cid);
    List<RomajiNameNotiCreSetExport> getEmpBasicPenNumInforList(List<String> empList, String cid);
    List<RomajiNameNotiCreSetExport> getEmpFamilySocialInsList(List<String> empList, String cid, int familyId, GeneralDate date);
    List<RomajiNameNotiCreSetExport> getSocialInsuranceOfficeList(List<String> empList, String cid);
}
