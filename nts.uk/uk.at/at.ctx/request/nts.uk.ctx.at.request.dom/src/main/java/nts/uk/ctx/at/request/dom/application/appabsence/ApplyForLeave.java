package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.Application;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請
 * 休暇申請
 * 
 */

@Getter
@AllArgsConstructor
public class ApplyForLeave extends Application implements DomainAggregate {
    
    // 休暇申請反映情報
    private ReflectFreeTimeApp reflectFreeTimeApp;
    
    // 休暇申請画面描画情報
    private VacationRequestInfo vacationInfo;

    
    public ApplyForLeave(Application application) {
        super(application);
    }
    
    /**
     * 60H超休残数チェック
     */
    private void overtime60HRestCheck() {
        
    }
    
    /**
     * 介護上限日数チェック
     */
    private void longTermCareMaxDayCheck() {
        
    }
    
    /**
     * 子の看護上限日数チェック
     */
    private void childNursingMaxDayCheck() {
        
    }
    
    /**
     * 残数チェックする
     */
    public void checkRemaining() {
        
    }
    
    /**
     * 特別休暇残数チェック
     */
    private void checkRemainingVacation() {
        
    }
    
    /**
     * 申請内容
     * @return 
     */
    public String applicationContent() {
        return null;
    }
}
