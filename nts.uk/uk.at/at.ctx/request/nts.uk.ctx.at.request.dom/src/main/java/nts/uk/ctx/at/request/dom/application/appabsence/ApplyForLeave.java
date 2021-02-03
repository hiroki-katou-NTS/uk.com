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
    
    public void setApplication(Application application) {
        this.setVersion(application.getVersion());
        this.setAppID(application.getAppID());
        this.setPrePostAtr(application.getPrePostAtr());
        this.setEmployeeID(application.getEmployeeID());
        this.setAppType(application.getAppType());
        this.setAppDate(application.getAppDate());
        this.setEnteredPersonID(application.getEnteredPersonID());
        this.setInputDate(application.getInputDate());
        this.setReflectionStatus(application.getReflectionStatus());
        this.setOpStampRequestMode(application.getOpStampRequestMode());
        this.setOpReversionReason(application.getOpReversionReason());
        this.setOpAppStartDate(application.getOpAppStartDate());
        this.setOpAppEndDate(application.getOpAppEndDate());
        this.setOpAppReason(application.getOpAppReason());
        this.setOpAppStandardReasonCD(application.getOpAppStandardReasonCD());
    }
    
    public Application getApplication() {
        return new Application(
                this.getVersion(),
                this.getAppID(),
                this.getPrePostAtr(),
                this.getEmployeeID(),
                this.getAppType(),
                this.getAppDate(),
                this.getEnteredPersonID(),
                this.getInputDate(),
                this.getReflectionStatus(),
                this.getOpStampRequestMode(),
                this.getOpReversionReason(),
                this.getOpAppStartDate(),
                this.getOpAppEndDate(),
                this.getOpAppReason(),
                this.getOpAppStandardReasonCD());
    }
}
