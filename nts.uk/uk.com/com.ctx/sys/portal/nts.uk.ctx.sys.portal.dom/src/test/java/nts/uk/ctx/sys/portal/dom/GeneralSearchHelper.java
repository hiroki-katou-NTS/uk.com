package nts.uk.ctx.sys.portal.dom;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.generalsearch.GeneralSearchHistoryDto;

public class GeneralSearchHelper {

	public static class GeneralSearchHistoryHelper {
		public static GeneralSearchHistoryDto getMockDto() {
			return GeneralSearchHistoryDto.builder()
				.companyID("companyID")
				.contents("contents")
				.searchCategory(0)
				.searchDate(GeneralDateTime.now())
				.userID("userID")
				.build();
		}
	}
}
