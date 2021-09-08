package nts.uk.ctx.at.request.app.command.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttMailTestParam {
	/**
    * メール種類
    */
    private int mailType;
    
    /**
    * 画面の承認URL
    */
    private boolean screenUrlApprovalEmbed;
    
    /**
    * 画面の日別URL
    */
    private boolean screenUrlDayEmbed;
    
    /**
    * 画面の月別URL
    */
    private boolean screenUrlMonthEmbed;
}
