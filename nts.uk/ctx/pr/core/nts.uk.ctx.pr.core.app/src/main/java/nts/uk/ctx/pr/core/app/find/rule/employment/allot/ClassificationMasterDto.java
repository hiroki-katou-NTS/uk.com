package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import lombok.Value;
import nts.uk.ctx.pr.core.ac.basic.classification.ClassificationDto;

@Value
public class ClassificationMasterDto {

	String companyCode;
	String classificationName;
	String classificationOutCode;
	String classificationCode;
	String Memo;

	public static ClassificationMasterDto fromDomain(ClassificationDto classificationMaster) {
		return new ClassificationMasterDto(classificationMaster.getCompanyCode(),
				classificationMaster.getClassificationName(),
				classificationMaster.getClassificationCode(),
				classificationMaster.getClassificationOutCode(),
				classificationMaster.getClassificationMemo());
	}

}
