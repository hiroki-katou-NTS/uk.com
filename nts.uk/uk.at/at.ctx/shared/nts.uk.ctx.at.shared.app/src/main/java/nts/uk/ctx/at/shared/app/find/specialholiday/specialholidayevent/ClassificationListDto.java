package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.ClassificationList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassificationListDto {

	/* 会社ID */
	String companyId;

	/* 特別休暇枠NO */
	int specialHolidayEventNo;

	/* 雇用コード */
	String classificationCd;

	public static List<ClassificationListDto> fromClsList(List<ClassificationList> clsList) {

		return clsList.stream().map(x -> fromDomain(x)).collect(Collectors.toList());

	}

	private static ClassificationListDto fromDomain(ClassificationList domain) {

		return new ClassificationListDto(domain.getCompanyId(), domain.getSpecialHolidayEventNo(),
				domain.getClassificationCd());
	}

}
