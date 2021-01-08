package nts.uk.ctx.office.dom.favorite.service;

import java.util.Arrays;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.TargetSelection;

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
