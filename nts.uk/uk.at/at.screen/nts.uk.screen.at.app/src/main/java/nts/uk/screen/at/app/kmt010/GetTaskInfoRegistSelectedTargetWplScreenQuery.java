package nts.uk.screen.at.app.kmt010;

import nts.uk.screen.at.app.kmt009.TaskDto;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

/**
 * ScreenQuery: 選択されている対象職場に登録されている作業情報を取得する
 */

@Stateless
public class GetTaskInfoRegistSelectedTargetWplScreenQuery {

    public Map<Integer,List<TaskDto>>getTaskSelected(String workPlaceId){
            return null;
    }
}
