package nts.uk.screen.at.app.ksc001.d;

import lombok.val;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * ScreenQuery: 設定した要件によると対象社員を取得する
 */
@Stateless
public class GetEligibleEmployeesScreenQuery {


    public List<String> getListEmployeeId(ConditionDto conditionDto){
        val listId = conditionDto.getListEmployeeId();
        List<String> rs = new ArrayList<>();
        listId.forEach(e ->{
            if(true){
                rs.add(e);
            }
        });
        return rs;
    }
}
