package nts.uk.screen.at.app.ksu001.summarycategory;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.app.find.schedulecounter.personal.PersonalCounterFinder;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkpcounter.WorkplaceCounterFinder;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounter;
import nts.uk.shr.com.context.AppContexts;

/**
 *  集計カテゴリを取得する
 *  UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正(職場別).メニュー別OCD
 * @author hoangnd
 *
 */
@Stateless
public class GetSummaryCategory {

	@Inject
	private WorkplaceCounterFinder workplaceCounterFinder;
	
	@Inject
	private PersonalCounterFinder personalCounterFinder;
	
	/**
	 * 
	 * @param displayFormat 表示形式
	 * @return
	 */
	public SummaryCategoryDto get(String displayFormat) {
		SummaryCategoryDto output = new SummaryCategoryDto();
		String companyId = AppContexts.user().companyId();
		// 1 取得する()
		// type is incorrect
		Optional<WorkplaceCounter> workplaceCounterOp = Optional.empty();
		if (workplaceCounterOp.isPresent()) {
			output.setUseCategoriesWorkplace(workplaceCounterOp.get().getUseCategories());
		}
		
		// 2 スケジュール個人計情報を取得する()
		// type is incorrect
		Optional<PersonalCounter> personalCounterOp = Optional.empty();
		
		if (personalCounterOp.isPresent()) {
			output.setUseCategoriesPersonal(personalCounterOp.get().getUseCategories());
		}
		
		return output;
	}
}
