package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.OutCndDetailItemCustom;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;

/**
 * 出力条件詳細(定型)
 */
@AllArgsConstructor
@Value
public class OutCndDetailDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private String conditionSettingCd;

	/**
	 * 条件SQL
	 */
	private String exterOutCdnSql;

	private List<OutCndDetailItemDto> listOutCndDetailItem;

	public static OutCndDetailDto fromDomain(OutCndDetail domain) {
		return new OutCndDetailDto(domain.getCid(), domain.getConditionSettingCd().v(), domain.getExterOutCdnSql().v(),
				domain.getListOutCndDetailItem().stream().map(x -> OutCndDetailItemDto.fromDomain((OutCndDetailItemCustom) x))
						.collect(Collectors.toList()));
	}
}