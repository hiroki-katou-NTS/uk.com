package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstruction;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstructionCategoryNO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Index reconstruction dto.<br>
 * Dto インデックス再構成
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class IndexReconstructionDto {

	/**
	 * 統計情報を更新する
	 **/
	private int updateStats;

	/**
	 * 使用区分
	 **/
	private int classificationOfUse;

	/**
	 * カテゴリリスト
	 **/
	private List<Integer> categoryNo;


	/**
	 * No args constructor.
	 */
	private IndexReconstructionDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Index reconstruction dto
	 */
	public static IndexReconstructionDto createFromDomain(IndexReconstruction domain) {
		if (domain == null) {
			return null;
		}
		IndexReconstructionDto dto = new IndexReconstructionDto();
		dto.updateStats = domain.getUpdateStatistics().value;
		dto.classificationOfUse = domain.getIndexReorgAttr().value;
		dto.categoryNo = domain.getCategoryList()
							   .stream()
							   .map(IndexReconstructionCategoryNO::v)
							   .collect(Collectors.toList());
		return dto;
	}

}
