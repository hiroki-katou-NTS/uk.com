package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.stampsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.*;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRQMT_APP_STAMP")
public class KrqmtAppStamp extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "SUP_DISP_CNT")
    private Integer supDispCnt;

    @Column(name = "STAMP_PLACE_DISP_ATR")
    private Integer stampPlaceDispAtr;

    @Column(name = "CANCEL_DISP_ATR")
    private Integer cancelDispAtr;

    @Column(name = "STAMP_OUT_PRI_DISP_ATR")
    private Integer stampOutPriDispAtr;

    @Column(name = "STAMP_OUT_PUB_DISP_ATR")
    private Integer stampOutPubDispAtr;

    @Column(name = "STAMP_OUT_COMP_DISP_ATR")
    private Integer stampOutCompDispAtr;

    @Column(name = "STAMP_OUT_UNION_DISP_ATR")
    private Integer stampOutUnionDispAtr;

    @Column(name = "WK_CMT_FONT_COLOR1")
    private String workCmtFontColor1;

    @Column(name = "WK_CMT_CONTENT1")
    private String workCmtContent1;

    @Column(name = "WK_CMT_FONT_WEIGHT1")
    private Integer workCmtFontWeight1;

    @Column(name = "WK_CMT_FONT_COLOR2")
    private String workCmtFontColor2;

    @Column(name = "WK_CMT_CONTENT2")
    private String workCmtContent2;

    @Column(name = "WK_CMT_FONT_WEIGHT2")
    private Integer workCmtFontWeight2;

    @Column(name = "GOOUT_CMT_FONT_COLOR1")
    private String goOutCmtFontColor1;

    @Column(name = "GOOUT_CMT_CONTENT1")
    private String goOutCmtContent1;

    @Column(name = "GOOUT_CMT_FONT_WEIGHT1")
    private Integer goOutCmtFontWeight1;

    @Column(name = "GOOUT_CMT_FONT_COLOR2")
    private String goOutCmtFontColor2;

    @Column(name = "GOOUT_CMT_CONTENT2")
    private String goOutCmtContent2;

    @Column(name = "GOOUT_CMT_FONT_WEIGHT2")
    private Integer goOutCmtFontWeight2;

    @Column(name = "CHILD_CARE_CMT_FONT_COLOR1")
    private String childCareCmtFontColor1;

    @Column(name = "CHILD_CARE_CMT_CONTENT1")
    private String childCareCmtContent1;

    @Column(name = "CHILD_CARE_CMT_FONT_WEIGHT1")
    private Integer childCareCmtFontWeight1;

    @Column(name = "CHILD_CARE_CMT_FONT_COLOR2")
    private String childCareCmtFontColor2;

    @Column(name = "CHILD_CARE_CMT_CONTENT2")
    private String childCareCmtContent2;

    @Column(name = "CHILD_CARE_CMT_FONT_WEIGHT2")
    private Integer childCareCmtFontWeight2;

    @Column(name = "SUP_CMT_FONT_COLOR1")
    private String supCmtFontColor1;

    @Column(name = "SUP_CMT_CONTENT1")
    private String supCmtContent1;

    @Column(name = "SUP_CMT_FONT_WEIGHT1")
    private Integer supCmtFontWeight1;

    @Column(name = "SUP_CMT_FONT_COLOR2")
    private String supCmtFontColor2;

    @Column(name = "SUP_CMT_CONTENT2")
    private String supCmtContent2;

    @Column(name = "SUP_CMT_FONT_WEIGHT2")
    private Integer supCmtFontWeight2;

    @Column(name = "CARE_CMT_FONT_COLOR1")
    private String careCmtFontColor1;

    @Column(name = "CARE_CMT_CONTENT1")
    private String careCmtContent1;

    @Column(name = "CARE_CMT_FONT_WEIGHT1")
    private Integer careCmtFontWeight1;

    @Column(name = "CARE_CMT_FONT_COLOR2")
    private String careCmtFontColor2;

    @Column(name = "CARE_CMT_CONTENT2")
    private String careCmtContent2;

    @Column(name = "CARE_CMT_FONT_WEIGHT2")
    private Integer careCmtFontWeight2;

    @Column(name = "BREAK_CMT_FONT_COLOR1")
    private String breakCmtFontColor1;

    @Column(name = "BREAK_CMT_CONTENT1")
    private String breakCmtContent1;

    @Column(name = "BREAK_CMT_FONT_WEIGHT1")
    private Integer breakCmtFontWeight1;

    @Column(name = "BREAK_CMT_FONT_COLOR2")
    private String breakCmtFontColor2;

    @Column(name = "BREAK_CMT_CONTENT2")
    private String breakCmtContent2;

    @Column(name = "BREAK_CMT_FONT_WEIGHT2")
    private Integer breakCmtFontWeight2;

    @Column(name = "NR_CMT_FONT_COLOR1")
    private String nrCmtFontColor1;

    @Column(name = "NR_CMT_CONTENT1")
    private String nrCmtContent1;

    @Column(name = "NR_CMT_FONT_WEIGHT1")
    private Integer nrCmtFontWeight1;

    @Column(name = "NR_CMT_FONT_COLOR2")
    private String nrCmtFontColor2;

    @Column(name = "NR_CMT_CONTENT2")
    private String nrCmtContent2;

    @Column(name = "NR_CMT_FONT_WEIGHT2")
    private Integer nrCmtFontWeight2;

    @Column(name = "CHILD_CARE_TIME_REFLECT_ATR")
    private Integer childCareTimeReflectAtr;

    @Column(name = "SUP_TIME_REFLECT_ATR")
    private Integer supTimeReflectAtr;

    @Column(name = "CARE_TIME_REFLECT_ATR")
    private Integer careTimeReflectAtr;

    @Column(name = "GOOUT_TIME_REFLECT_ATR")
    private Integer goOutTimeReflectAtr;

    @Column(name = "BREAK_TIME_REFLECT_ATR")
    private Integer breakTimeReflectAtr;

    @Column(name = "WORK_TIME_REFLECT_ATR")
    private Integer workTimeReflectAtr;

    @Column(name = "EXTRA_WORK_TIME_REFLECT_ATR")
    private Integer extraWorkTimeReflectAtr;

    @Override
    protected Object getKey() {
        return null;
    }

    public AppStampSetting toSettingDomain() {
        return new AppStampSetting(
                companyId,
                new SupportFrameDispNO(supDispCnt),
                EnumAdaptor.valueOf(cancelDispAtr, UseDivision.class),
                Arrays.asList(
                        new SettingForEachType(
                                StampAtr.ATTENDANCE_RETIREMENT,
                                new AppCommentSet(new Comment(workCmtContent1), BooleanUtils.toBoolean(workCmtFontWeight1), new ColorCode(workCmtFontColor1)),
                                new AppCommentSet(new Comment(workCmtContent2), BooleanUtils.toBoolean(workCmtFontWeight2), new ColorCode(workCmtFontColor2))
                        ),
                        new SettingForEachType(
                                StampAtr.GOING_OUT_RETURNING,
                                new AppCommentSet(new Comment(goOutCmtContent1), BooleanUtils.toBoolean(goOutCmtFontWeight1), new ColorCode(goOutCmtFontColor1)),
                                new AppCommentSet(new Comment(goOutCmtContent2), BooleanUtils.toBoolean(goOutCmtFontWeight2), new ColorCode(goOutCmtFontColor2))
                        ),
                        new SettingForEachType(
                                StampAtr.CHILDCARE_OUT_RETURN,
                                new AppCommentSet(new Comment(childCareCmtContent1), BooleanUtils.toBoolean(childCareCmtFontWeight1), new ColorCode(childCareCmtFontColor1)),
                                new AppCommentSet(new Comment(childCareCmtContent2), BooleanUtils.toBoolean(childCareCmtFontWeight2), new ColorCode(childCareCmtFontColor2))
                        ),
                        new SettingForEachType(
                                StampAtr.SUPPORT_IN_SUPPORT_OUT,
                                new AppCommentSet(new Comment(supCmtContent1), BooleanUtils.toBoolean(supCmtFontWeight1), new ColorCode(supCmtFontColor1)),
                                new AppCommentSet(new Comment(supCmtContent2), BooleanUtils.toBoolean(supCmtFontWeight2), new ColorCode(supCmtFontColor2))
                        ),
                        new SettingForEachType(
                                StampAtr.OUT_OF_CARE_RETURN_OF_CARE,
                                new AppCommentSet(new Comment(careCmtContent1), BooleanUtils.toBoolean(careCmtFontWeight1), new ColorCode(careCmtFontColor1)),
                                new AppCommentSet(new Comment(careCmtContent2), BooleanUtils.toBoolean(careCmtFontWeight2), new ColorCode(careCmtFontColor2))
                        ),
                        new SettingForEachType(
                                StampAtr.BREAK,
                                new AppCommentSet(new Comment(breakCmtContent1), BooleanUtils.toBoolean(breakCmtFontWeight1), new ColorCode(breakCmtFontColor1)),
                                new AppCommentSet(new Comment(breakCmtContent2), BooleanUtils.toBoolean(breakCmtFontWeight2), new ColorCode(breakCmtFontColor2))
                        )
                        ,new SettingForEachType(
                                StampAtr.RECORDER_IMAGE,
                                new AppCommentSet(new Comment(nrCmtContent1), BooleanUtils.toBoolean(nrCmtFontWeight1), new ColorCode(nrCmtFontColor1)),
                                new AppCommentSet(new Comment(nrCmtContent2), BooleanUtils.toBoolean(nrCmtFontWeight2), new ColorCode(nrCmtFontColor2))
                        )
                ),
                Arrays.asList(
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(stampOutPriDispAtr, DisplayAtr.class),
                                GoOutType.PRIVATE
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(stampOutPriDispAtr, DisplayAtr.class),
                                GoOutType.PRIVATE
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(stampOutPriDispAtr, DisplayAtr.class),
                                GoOutType.PRIVATE
                        ),
                        new GoOutTypeDispControl(
                                EnumAdaptor.valueOf(stampOutPriDispAtr, DisplayAtr.class),
                                GoOutType.PRIVATE
                        )
                )
        );
    }
}
