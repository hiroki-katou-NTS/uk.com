package nts.uk.screen.at.app.kha003.exportcsv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.DisplayInformation;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ManHourHierarchyFlatData {
    private String codeLv1;
    private String codeLv2;
    private String codeLv3;
    private String codeLv4;
    private DisplayInformation lstDispInfo;
    private Map<String, Integer> lstVerticalValue;
    private int total;
}

//@AllArgsConstructor
//@Getter
//class HierarchyData {
//    HierarchyDetailInfo level1;
//    HierarchyDetailInfo level2;
//    HierarchyDetailInfo level3;
//    HierarchyDetailInfo level4;
//}
//
//@AllArgsConstructor
//@Getter
//class HierarchyDetailInfo {
//    private String code;
//    private List<DisplayInformation> lstDispInfo;
//    private Map<String, Integer> lstVerticalValue;
//    private int total;
//}
