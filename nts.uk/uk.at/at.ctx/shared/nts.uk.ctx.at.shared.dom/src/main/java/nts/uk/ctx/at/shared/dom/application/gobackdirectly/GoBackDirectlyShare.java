package nts.uk.ctx.at.shared.dom.application.gobackdirectly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         直行直帰申請
 */
@Getter
@Setter
@AllArgsConstructor
public class GoBackDirectlyShare extends ApplicationShare {

	// 直帰区分
	private NotUseAtr straightDistinction;
	// 直行区分
	private NotUseAtr straightLine;
	// 勤務を変更する
	private Optional<NotUseAtr> isChangedWork = Optional.empty();
	// 勤務情報
	private Optional<WorkInformation> dataWork = Optional.empty();

	public GoBackDirectlyShare() {

	}

	// 申請内容
	public String getContent() {
		return null;
	}

	public GoBackDirectlyShare(NotUseAtr straightDistinction, NotUseAtr straightLine, Optional<NotUseAtr> isChangedWork,
			Optional<WorkInformation> dataWork, ApplicationShare appShare) {
		super(appShare);
		this.straightDistinction = straightDistinction;
		this.straightLine = straightLine;
		this.isChangedWork = isChangedWork;
		this.dataWork = dataWork;
	}

}
