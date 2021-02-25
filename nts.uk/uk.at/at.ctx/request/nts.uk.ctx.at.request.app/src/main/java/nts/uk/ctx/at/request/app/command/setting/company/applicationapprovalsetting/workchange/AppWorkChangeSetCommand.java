package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.workchange;

import org.apache.commons.lang3.BooleanUtils;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.InitDisplayWorktimeAtr;
import nts.uk.shr.com.color.ColorCode;

/**
 * refactor4 refactor 4
 */

@Value
public class AppWorkChangeSetCommand {
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

    private int workTimeReflectAtr;

    public AppWorkChangeSet toDomain(String companyId) {
        return new AppWorkChangeSet(
                companyId,
                new AppCommentSet(new Comment(commentContent1), BooleanUtils.toBoolean(commentFontWeight1), new ColorCode(commentFontColor1)),
                new AppCommentSet(new Comment(commentContent2), BooleanUtils.toBoolean(commentFontWeight2), new ColorCode(commentFontColor2)),
                EnumAdaptor.valueOf(initDisplayWorktime, InitDisplayWorktimeAtr.class)
        );
    }
}
