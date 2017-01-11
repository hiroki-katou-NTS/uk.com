package nts.uk.ctx.basic.dom.organization.classification;

import lombok.Getter;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class Classification {

	private final int companyCode;

	private final ClassificationCode classificationCode;

	private ClassificationName classificationName;

	private ClassificationCode classificationOutCode;

	private Memo classificationMemo;

	public Classification(int companyCode, ClassificationCode classificationCode, ClassificationName classificationName,
			ClassificationCode classificationOutCode, Memo classificationMemo) {
		this.companyCode = companyCode;
		this.classificationCode = classificationCode;
		this.classificationName = classificationName;
		this.classificationOutCode = classificationOutCode;
		this.classificationMemo = classificationMemo;
	}

}
