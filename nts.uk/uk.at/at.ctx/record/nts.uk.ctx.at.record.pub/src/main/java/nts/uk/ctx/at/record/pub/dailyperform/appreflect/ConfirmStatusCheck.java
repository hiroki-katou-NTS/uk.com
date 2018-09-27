package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConfirmStatusCheck {
	private String cid;
	private String sid;
	private GeneralDate appDate;
	private PrePostRecordAtr prePost;
	private ApplicationType appType;
	private ObjectCheck chkObject;
	/**
	 * ドメインモデル「反映情報」．実績強制反映をチェックする
	 */
	private boolean recordReflect;
	/**
	 * ドメインモデル「反映情報」．予定強制反映をチェックする
	 */
	private boolean scheReflect;
	/**
	 * 確定区分
	 */
	private boolean confirmedAtr;
}
