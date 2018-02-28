package nts.uk.ctx.at.request.app.command.application.workchange;

import lombok.Value;

@Value
public class AppWorkChangeSetCommand
{
    
    /**
    * スケジュールが休日の場合は除くを表示する
    */
    private int excludeHoliday;
    
    /**
    * 勤務時間を変更できる
    */
    private int workChangeTimeAtr;
    
    /**
    * 実績を表示する
    */
    private int displayResultAtr;
    
    /**
    * 勤務時間の初期表示
    */
    private int initDisplayWorktime;
    
    /**
    * コメント
    */
    private String commentContent1;
    
    /**
    * 太字
    */
    private int commentFontWeight1;
    
    /**
    * 文字色
    */
    private String commentFontColor1;
    
    /**
    * コメント
    */
    private String commentContent2;
    
    /**
    * 太字
    */
    private int commentFontWeight2;
    
    /**
    * 文字色
    */
    private String commentFontColor2;
}
