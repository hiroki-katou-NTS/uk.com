package nts.uk.cnv.dom.td.alteration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.gul.text.StringUtil;

/**
 * メタ情報
 */
@Getter
@EqualsAndHashCode
public class AlterationMetaData {
	private final String userName;
	private final String comment;
	
	public AlterationMetaData(String userName, String comment) {
		
		if (StringUtil.isNullOrEmpty(userName, true)) {
			throw new BusinessException(new RawErrorMessage("nameを入力してください"));
		}
		
		if (StringUtil.isNullOrEmpty(comment, true)) {
			throw new BusinessException(new RawErrorMessage("コメントを入力してください"));
		}
		
		this.userName = userName;
		this.comment = comment;
	}
}
