package nts.uk.ctx.sys.assist.dom.favorite.service;

import java.util.Arrays;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.sys.assist.dom.favorite.TargetSelection;

public class FavoriteSpecificARTestHelper {

	public static FavoriteSpecify mockFavoriteInfoAR() {
		FavoriteSpecifyAR memento = FavoriteSpecifyAR.builder()
				.favoriteName("mock-favoriteName")
				.creatorId("mock-creatorId")
				.inputDate(GeneralDateTime.FAKED_NOW)
				.targetSelection(TargetSelection.WORKPLACE.value)
				.workplaceId(Arrays.asList("mock-workplaceId"))
				.order(1)
				.build();
		FavoriteSpecify domain = new FavoriteSpecify();
		domain.getMemento(memento);
		return domain;
	}
}
