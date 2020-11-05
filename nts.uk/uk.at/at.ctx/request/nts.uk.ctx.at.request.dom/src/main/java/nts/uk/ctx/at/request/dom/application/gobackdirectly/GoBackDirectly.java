package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.enumcommon.NotUseAtr;


@Getter
@Setter
//直行直帰申請
public class GoBackDirectly extends Application {

	// 直行区分
	private NotUseAtr straightDistinction;
	// 直帰区分
	private NotUseAtr straightLine;
	// 勤務を変更する
	private Optional<NotUseAtr> isChangedWork = Optional.empty();
	// 勤務情報
	private Optional<WorkInformation> dataWork = Optional.empty();

	public GoBackDirectly() {
		
	}

	// 申請内容
	public String getContent() {
		return null;
	}

}
