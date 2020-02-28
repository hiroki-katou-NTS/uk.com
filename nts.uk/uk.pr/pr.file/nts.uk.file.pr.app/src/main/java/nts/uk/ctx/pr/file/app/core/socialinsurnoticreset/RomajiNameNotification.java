package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RomajiNameNotification {
    GeneralDate date;
    String personTarget;
    CompanyInfor companyInfor;
    RomajiNameNotiCreSetting romajiNameNotiCreSetting;
    List<RomajiNameNotiCreSetExport> romajiNameNotiCreSetExportList;
}
