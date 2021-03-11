package nts.uk.screen.at.app.kmt001;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.screen.at.app.kmt009.TaskDisplayInfoDto;
import nts.uk.screen.at.app.query.kmt.kmt011.TaskOperationSettingDto;

import java.util.List;


public class KmtDto {

    // コード
    private String code;

    // 作業枠NO作業運用設定
    private Integer taskFrameNo;

    //作業運用設定
    TaskOperationSettingDto settingDto;

    //作業枠設定
    TaskFrameSetting frameSetting;

    //作業リスト（List<作業コード, 作業名>）
    private List<String> workList;

}
