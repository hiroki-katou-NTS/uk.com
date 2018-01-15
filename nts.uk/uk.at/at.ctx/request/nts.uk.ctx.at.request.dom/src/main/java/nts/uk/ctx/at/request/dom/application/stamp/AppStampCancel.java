package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 打刻取消申請
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppStampCancel extends DomainObject {
	private AppStampAtr stampAtr;
	
	// 打刻枠No
	private Integer stampFrameNo;
	
	// 実績取消
	private Integer cancelAtr;

}
