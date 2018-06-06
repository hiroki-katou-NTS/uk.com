package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;

/**
 * 履歴参照基準日を適用する
 * @author hoatt
 *
 */
@Stateless
public class ReferenceHistoryFinder{

	@Inject
	private PerInfoHistorySelectionRepository repoHistSel;

	public ReferenceHistoryDto referHistSel(ReferenceHistoryDto param) {
		GeneralDate baseDate = GeneralDate.fromString(param.getBaseDate(), "yyyy-MM-dd");
		List<PerInfoHistorySelection> lstHistSel = repoHistSel.getHistorySelItemByDate(baseDate, param.getLstSelItemId());
		List<String> lstResult = new ArrayList<>();
		for (PerInfoHistorySelection perInfoHistorySelection : lstHistSel) {
			lstResult.add(perInfoHistorySelection.getSelectionItemId());
		}
		return new ReferenceHistoryDto(lstResult, param.getBaseDate());
	}

}
