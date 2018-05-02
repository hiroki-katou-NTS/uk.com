/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMtRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author nam.lh
 *
 */
@Stateless
public class ManualSetOfDataSaveServiceImpl implements ManualSetOfDataSaveService {
	@Inject
	private ResultOfSavingRepository repoResultSaving;
	@Inject
	private DataStorageMngRepository repoDataSto;
	@Inject
	private ManualSetOfDataSaveRepository repoMalSetOfSave;
	@Inject
	private CategoryRepository repoCategory;
	@Inject
	private CategoryFieldMtRepository repoCateField;

	@Override
	public void serverManualSaveProcessing(String storeProcessingId) {
		// ドメインモデル「データ保存の保存結果」へ書き出す
		Optional<ResultOfSaving> otpResultSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
		// ドメインモデル「データ保存動作管理」を登録する ( Save data to Data storage operation
		// management )
		// todo Q&A
		int categoryTotalCount = 0;
		int categoryCount = 0;
		int errorCount = 0;
		OperatingCondition operatingCondition = EnumAdaptor.valueOf(0, OperatingCondition.class);
		NotUseAtr doNotInterrupt = EnumAdaptor.valueOf(0, NotUseAtr.class);
		DataStorageMng domain = new DataStorageMng(storeProcessingId, doNotInterrupt, categoryCount, categoryTotalCount,
				errorCount, operatingCondition);

		repoDataSto.add(domain);

		// Selection of target table and condition setting
		Optional<ManualSetOfDataSave> optManualSetting = repoMalSetOfSave.getManualSetOfDataSaveById(storeProcessingId);

		if (optManualSetting.isPresent()) {
			List<TargetCategory> targetCategories = optManualSetting.get().getCategory();
			List<String> categoryIds = targetCategories.stream().map(x -> {
				return x.getCategoryId();
			}).collect(Collectors.toList());
			List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
			List<CategoryFieldMt> categoryFieldMts = repoCateField.getCategoryFieldMtByListId(categoryIds);

			// update domain 「データ保存動作管理」 Data storage operation management
			storeProcessingId = domain.getStoreProcessingId();
			categoryTotalCount = categorys.size();
			categoryCount = 1;
			operatingCondition = EnumAdaptor.valueOf(1, OperatingCondition.class);
			repoDataSto.update(storeProcessingId, categoryTotalCount, categoryCount, operatingCondition);
		}

	}

}
