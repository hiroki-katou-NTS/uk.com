package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.stampsetting;

import java.util.Arrays;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.GoOutType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.GoOutTypeDispControl;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.SettingForEachType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.StampAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.SupportFrameDispNO;
import nts.uk.shr.com.color.ColorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppStampSettingCommand {
    private int cancelAtr;

    private String workComment1Content;
    private String workComment1Color;
    private boolean workComment1Bold;
    private String workComment2Content;
    private String workComment2Color;
    private boolean workComment2Bold;

    private int goOutPrivateDispAtr;
    private int goOutOfficeDispAtr;
    private int goOutCompensationDispAtr;
    private int goOutUnionDispAtr;
    private String goOutComment1Content;
    private String goOutComment1Color;
    private boolean goOutComment1Bold;
    private String goOutComment2Content;
    private String goOutComment2Color;
    private boolean goOutComment2Bold;

    private String childCareComment1Content;
    private String childCareComment1Color;
    private boolean childCareComment1Bold;
    private String childCareComment2Content;
    private String childCareComment2Color;
    private boolean childCareComment2Bold;

    private int supportFrameDispNO;
    private String supportComment1Content;
    private String supportComment1Color;
    private boolean supportComment1Bold;
    private String supportComment2Content;
    private String supportComment2Color;
    private boolean supportComment2Bold;

    private String careComment1Content;
    private String careComment1Color;
    private boolean careComment1Bold;
    private String careComment2Content;
    private String careComment2Color;
    private boolean careComment2Bold;

    private String breakComment1Content;
    private String breakComment1Color;
    private boolean breakComment1Bold;
    private String breakComment2Content;
    private String breakComment2Color;
    private boolean breakComment2Bold;

    private String imageRecordComment1Content;
    private String imageRecordComment1Color;
    private boolean imageRecordComment1Bold;
    private String imageRecordComment2Content;
    private String imageRecordComment2Color;
    private boolean imageRecordComment2Bold;

    public AppStampSetting toDomain(String companyId) {
        return new AppStampSetting(
                companyId,
                new SupportFrameDispNO(supportFrameDispNO),
                EnumAdaptor.valueOf(cancelAtr, UseDivision.class),
                Arrays.asList(
                        new SettingForEachType(
                                StampAtr.ATTENDANCE_RETIREMENT,
                                new AppCommentSet(new Comment(workComment1Content), BooleanUtils.toBoolean(workComment1Bold), new ColorCode(workComment1Color)),
                                new AppCommentSet(new Comment(workComment2Content), BooleanUtils.toBoolean(workComment2Bold), new ColorCode(workComment2Color))
                        ),
                        new SettingForEachType(
                                StampAtr.GOING_OUT_RETURNING,
                                new AppCommentSet(new Comment(goOutComment1Content), BooleanUtils.toBoolean(goOutComment1Bold), new ColorCode(goOutComment1Color)),
                                new AppCommentSet(new Comment(goOutComment2Content), BooleanUtils.toBoolean(goOutComment2Bold), new ColorCode(goOutComment2Color))
                        ),
                        new SettingForEachType(
                                StampAtr.CHILDCARE_OUT_RETURN,
                                new AppCommentSet(new Comment(childCareComment1Content), BooleanUtils.toBoolean(childCareComment1Bold), new ColorCode(childCareComment1Color)),
                                new AppCommentSet(new Comment(childCareComment2Content), BooleanUtils.toBoolean(childCareComment2Bold), new ColorCode(childCareComment2Color))
                        ),
                        new SettingForEachType(
                                StampAtr.SUPPORT_IN_SUPPORT_OUT,
                                new AppCommentSet(new Comment(supportComment1Content), BooleanUtils.toBoolean(supportComment1Bold), new ColorCode(supportComment1Color)),
                                new AppCommentSet(new Comment(supportComment2Content), BooleanUtils.toBoolean(supportComment2Bold), new ColorCode(supportComment2Color))
                        ),
                        new SettingForEachType(
                                StampAtr.OUT_OF_CARE_RETURN_OF_CARE,
                                new AppCommentSet(new Comment(careComment1Content), BooleanUtils.toBoolean(careComment1Bold), new ColorCode(careComment1Color)),
                                new AppCommentSet(new Comment(careComment2Content), BooleanUtils.toBoolean(careComment2Bold), new ColorCode(careComment2Color))
                        ),
                        new SettingForEachType(
                                StampAtr.BREAK,
                                new AppCommentSet(new Comment(breakComment1Content), BooleanUtils.toBoolean(breakComment1Bold), new ColorCode(breakComment1Color)),
                                new AppCommentSet(new Comment(breakComment2Content), BooleanUtils.toBoolean(breakComment2Bold), new ColorCode(breakComment2Color))
                        )
                        ,new SettingForEachType(
                                StampAtr.RECORDER_IMAGE,
                                new AppCommentSet(new Comment(imageRecordComment1Content), BooleanUtils.toBoolean(imageRecordComment1Bold), new ColorCode(imageRecordComment1Color)),
                                new AppCommentSet(new Comment(imageRecordComment2Content), BooleanUtils.toBoolean(imageRecordComment2Bold), new ColorCode(imageRecordComment2Color))
                        )
                ),
                Arrays.asList(
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(goOutPrivateDispAtr, DisplayAtr.class),
                                GoOutType.PRIVATE
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(goOutOfficeDispAtr, DisplayAtr.class),
                                GoOutType.OFFICE
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(goOutCompensationDispAtr, DisplayAtr.class),
                                GoOutType.COMPENSATION
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(goOutUnionDispAtr, DisplayAtr.class),
                                GoOutType.UNION
                        )
                )
        );
    }
}
