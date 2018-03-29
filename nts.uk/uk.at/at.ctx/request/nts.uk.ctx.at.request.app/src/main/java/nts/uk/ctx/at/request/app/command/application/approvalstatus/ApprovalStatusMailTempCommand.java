package nts.uk.ctx.at.request.app.command.application.approvalstatus;

import lombok.Value;

@Value
public class ApprovalStatusMailTempCommand
{
    /**
    * メール種類
    */
    private int mailType;
    
    /**
    * URL承認埋込
    */
    private int urlApprovalEmbed;
    
    /**
    * URL日別埋込
    */
    private int urlDayEmbed;
    
    /**
    * URL月別埋込
    */
    private int urlMonthEmbed;
    
    /**
    * メール件名
    */
    private String mailSubject;
    
    /**
    * メール本文
    */
    private String mailContent;
    
    private int editMode;
}
