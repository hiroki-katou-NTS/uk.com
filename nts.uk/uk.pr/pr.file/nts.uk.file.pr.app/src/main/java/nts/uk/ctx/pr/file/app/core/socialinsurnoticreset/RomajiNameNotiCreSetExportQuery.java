package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;

import java.util.List;

@Data
public class RomajiNameNotiCreSetExportQuery {
    private GeneralDate date;
    private String personTarget;
    private RomajiNameNotiCreSetExport romajiNameNotiCreSetting;
    private List<String> empIds;
    private int addressOutputClass;
}
