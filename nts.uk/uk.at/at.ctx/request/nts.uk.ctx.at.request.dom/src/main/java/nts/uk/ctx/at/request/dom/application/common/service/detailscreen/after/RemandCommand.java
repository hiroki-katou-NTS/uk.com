package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import lombok.Getter;
@Getter
public class RemandCommand {
	private List<String> appID;
	
	private Long version;
	//差し戻し先
	private Integer order;
	//差し戻しコメント
	private String remandReason;
	//申請本人の名前
	private String applicaintName;
}
