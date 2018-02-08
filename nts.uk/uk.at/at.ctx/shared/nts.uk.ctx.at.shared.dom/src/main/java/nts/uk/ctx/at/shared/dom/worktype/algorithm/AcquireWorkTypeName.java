package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務種類名称を取得する
 * @author tanlv
 *
 */
public class AcquireWorkTypeName {
	@Inject
	public WorkTypeRepository repository;
	
	/**
	 * 出勤系の勤務種類を取得する
	 * @return
	 */
	public String acquireWorkTypeName(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「勤務種類」を取得する
		Optional<WorkType> data = repository.findByDeprecated(companyId, workTypeCode);
		
		// ドメインモデル「勤務種類」．表示名を返す
		if(data.isPresent()) {
			return data.get().getName().v();
		}
		
		// nullを返す
		return null;
	}
}
