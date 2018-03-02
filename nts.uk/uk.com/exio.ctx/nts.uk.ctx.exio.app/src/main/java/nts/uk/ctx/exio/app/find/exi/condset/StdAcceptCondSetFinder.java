package nts.uk.ctx.exio.app.find.exi.condset;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCategoryDto;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCtgItemDatDto;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.service.FileUtil;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 受入条件設定（定型）
 */
public class StdAcceptCondSetFinder {

	@Inject
	private StdAcceptCondSetRepository stdConditionRepo;

	@Inject
	private StoredFileStreamService fileStreamService;

	public List<StdAcceptCondSetDto> getAllStdAcceptCondSet(int systemType) {
		String companyId = AppContexts.user().companyId();
		return stdConditionRepo.getStdAcceptCondSetBySysType(companyId, systemType).stream()
				.map(item -> StdAcceptCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public int getTotalRecordCsv(String fileId) {
		int totalRecord = 0;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			totalRecord = FileUtil.getTotalRecord(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return totalRecord;
	}

	/**
	 * Dummies Data category
	 * 
	 * @return
	 */
	public List<ExAcpCategoryDto> getAllCategory() {

		List<ExAcpCategoryDto> lstDataCategory = new ArrayList<ExAcpCategoryDto>();
		for (int i = 1; i <= 4; i++) {
			lstDataCategory.add(new ExAcpCategoryDto("1", "カテゴリ名　" + i, 0L));
		}
		return lstDataCategory;
	}

	public List<ExAcpCtgItemDatDto> getCategoryItemData(String categoryId) {
		List<ExAcpCtgItemDatDto> lstCategoryItemData = new ArrayList<ExAcpCtgItemDatDto>();
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j < 11; j++) {
				lstCategoryItemData.add(new ExAcpCtgItemDatDto("" + i, j, "カテゴリ項目データ" + "" + i + "" + j, j, 1, 1, 1,
						"1", 1, 1, 1, "5", "5", "5", "5", j, 1, 0L));
			}
		}

		return lstCategoryItemData;
	}
}
