package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kdw013.c.TaskDisplayInfoDto;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Getter
public class TaskDto {
	// コード
    private String code;

    // 作業枠NO
    private Integer taskFrameNo;

    // 外部連携情報
    private ExternalCooperationInfoDto cooperationInfo;

    // 子作業一覧
    private List<String> childTaskList;

    // 有効期限
    private GeneralDate expirationStartDate;

    private GeneralDate expirationEndDate;

    // 表示情報 : 作業表示情報
    private TaskDisplayInfoDto displayInfo;
    
}
