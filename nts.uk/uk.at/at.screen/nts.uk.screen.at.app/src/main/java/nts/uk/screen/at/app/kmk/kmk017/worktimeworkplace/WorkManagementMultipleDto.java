package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.ejb.Stateless;

@Data
@AllArgsConstructor
public class WorkManagementMultipleDto {

    /** 0: 使用しない
     * 1: 使用する
     * */
    Integer useATR;

}
