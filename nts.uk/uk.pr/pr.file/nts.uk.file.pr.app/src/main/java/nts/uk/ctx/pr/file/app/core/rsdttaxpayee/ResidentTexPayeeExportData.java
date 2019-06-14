package nts.uk.ctx.pr.file.app.core.rsdttaxpayee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentTexPayeeExportData {

    private String code;

    private String name;

    private String kanaName;

    private int prefectures;

    private String reportCd;

    private String reportName;

    private String accountNumber;

    private String subscriberName;

    private String designationNum;

    private String compileStationZipcode;

    private String compileStationName;
}
