package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.InitDisplayWorktimeAtr;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQMT_APP_WORK_CHANGE")
public class KrqmtAppWorkChangeSet extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqmtAppWorkChangeSetPk appWorkChangeSetPk;

	/**
	 * 勤務時間の初期表示
	 */
	@Column(name = "INIT_DISPLAY_WORKTIME")
	public int initDisplayWorktime;

	/**
	 * コメント
	 */
	@Column(name = "COMMENT_CONTENT1")
	public String commentContent1;

	/**
	 * 太字
	 */
	@Column(name = "COMMENT_FONT_WEIGHT1")
	public int commentFontWeight1;

	/**
	 * 文字色
	 */
	@Column(name = "COMMENT_FONT_COLOR1")
	public String commentFontColor1;

	/**
	 * コメント
	 */
	@Column(name = "COMMENT_CONTENT2")
	public String commentContent2;

	/**
	 * 太字
	 */
	@Column(name = "COMMENT_FONT_WEIGHT2")
	public int commentFontWeight2;

	/**
	 * 文字色
	 */
	@Column(name = "COMMENT_FONT_COLOR2")
	public String commentFontColor2;
	// 出退勤を反映するか

	@Column(name = "WORK_TIME_REFLECT_ATR")
	public int workTimeReflectAtr;

	@Override
	protected Object getKey() {
		return appWorkChangeSetPk;
	}

	// このMAPPERの定義が重要！
	public static final JpaEntityMapper<KrqmtAppWorkChangeSet> MAPPER = new JpaEntityMapper<>(
			KrqmtAppWorkChangeSet.class);

	public AppWorkChangeSet toSettingDomain() {
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
		appWorkChangeSet.setInitDisplayWorktimeAtr(EnumAdaptor.valueOf(initDisplayWorktime, InitDisplayWorktimeAtr.class));

		return appWorkChangeSet;
	}

	public ReflectWorkChangeApp toReflectDomain() {
		return new ReflectWorkChangeApp(appWorkChangeSetPk.cid, EnumAdaptor.valueOf(workTimeReflectAtr, NotUseAtr.class));
	}
}
