package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//メール送信の結果（送信先リスト、エラーメッセージ）
public class SendMailResult {
	
	private List<String> receptList;
	
	private String errorMsg;

}
