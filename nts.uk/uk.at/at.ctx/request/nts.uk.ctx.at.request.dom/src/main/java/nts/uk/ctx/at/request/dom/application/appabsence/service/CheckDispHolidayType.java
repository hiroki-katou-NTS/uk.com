package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckDispHolidayType {

//	//A4_3 - 年休設定
//	private boolean yearManage;
//	//A4_4 - 代休管理設定
//	private boolean subHdManage;
//	//A4_5 - 振休管理設定
//	private boolean subVacaManage;
//	//A4_8 - 積立年休設定
//	private boolean retentionManage;
    /**
     * anhnm
     * Refactor 5
     * Update output xu ly 14.休暇種類表示チェック
     * 
     */
    
    // 年休管理
    private AnualLeaveManagement annAnualLeaveManagement;
    
    // 積休管理
    private AccumulatedRestManagement accumulatedRestManagement;
    
    // 代休管理
    private SubstituteLeaveManagement substituteLeaveManagement;
    
    // 振休管理
    private HolidayManagement holidayManagement;
    
    // 60H超休管理
    private Overtime60HManagement overtime60hManagement;
    
    // 介護看護休暇管理
    private NursingCareLeaveManagement nursingCareLeaveManagement;
}
