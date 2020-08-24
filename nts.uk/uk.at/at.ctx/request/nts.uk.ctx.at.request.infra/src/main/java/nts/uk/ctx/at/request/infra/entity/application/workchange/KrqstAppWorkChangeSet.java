package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.InitDisplayWorktimeAtr;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQMT_APP_WORK_CHANGE")
public class KrqstAppWorkChangeSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqstAppWorkChangeSetPk appWorkChangeSetPk;

	/**
	 * 勤務時間の初期表示
	 */
	@Basic(optional = true)
	@Column(name = "INIT_DISPLAY_WORKTIME")
	public int initDisplayWorktime;

	/**
	 * コメント
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_CONTENT1")
	public String commentContent1;

	/**
	 * 太字
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_FONT_WEIGHT1")
	public int commentFontWeight1;

	/**
	 * 文字色
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_FONT_COLOR1")
	public String commentFontColor1;

	/**
	 * コメント
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_CONTENT2")
	public String commentContent2;

	/**
	 * 太字
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_FONT_WEIGHT2")
	public int commentFontWeight2;

	/**
	 * 文字色
	 */
	@Basic(optional = true)
	@Column(name = "COMMENT_FONT_COLOR2")
	public String commentFontColor2;
	// 出退勤を反映するか
	@Basic(optional = true)
	@Column(name = "WORK_TIME_REFLECT_ATR")
	public String workTimeReflectAtr;

	@Override
	protected Object getKey() {
		return appWorkChangeSetPk;
	}

	// このMAPPERの定義が重要！
	public static final JpaEntityMapper<KrqstAppWorkChangeSet> MAPPER = new JpaEntityMapper<>(
			KrqstAppWorkChangeSet.class);

	public AppWorkChangeSet toDomain() {
		AppWorkChangeSet appWorkChangeSet = new AppWorkChangeSet();
		appWorkChangeSet.setCompanyID(appWorkChangeSetPk.cid);
		AppCommentSet appCommentSet1 = new AppCommentSet();

		appCommentSet1.setComment(new Comment(commentContent1));
		appCommentSet1.setBold(commentFontWeight1 == 1);
		appCommentSet1.setColorCode(new ColorCode(commentFontColor1));

		AppCommentSet appCommentSet2 = new AppCommentSet();

		appCommentSet2.setComment(new Comment(commentContent2));
		appCommentSet2.setBold(commentFontWeight2 == 1);
		appCommentSet2.setColorCode(new ColorCode(commentFontColor2));

		appWorkChangeSet.setComment1(appCommentSet1);
		appWorkChangeSet.setComment2(appCommentSet2);
		appWorkChangeSet
				.setInitDisplayWorktimeAtr(EnumAdaptor.valueOf(initDisplayWorktime, InitDisplayWorktimeAtr.class));

		return appWorkChangeSet;
	}
}
