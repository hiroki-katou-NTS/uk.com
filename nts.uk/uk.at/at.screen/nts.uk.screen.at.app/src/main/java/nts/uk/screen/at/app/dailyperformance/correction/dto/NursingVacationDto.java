package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author anhnm
 * 子の看護残数
 * 介護残数
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NursingVacationDto {

    // 子の看護管理する
    // 介護管理する
    boolean manageNursing = false;
    
    // 時間子の看護管理する
    // 時間介護管理する
    boolean manageNursingTime = false;
    
    // 子の育児残数
    // 介護残数
    Double remainDays = 0.0;
    
    // 時間子の育児残数
    // 時間介護残数
    int remainTime = 0;
}
