package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.outcnddetail.CtgItemDataCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailService;

/**
 * 出力条件詳細(定型)
 */
@Stateless
public class OutCndDetailFinder {
	@Inject
	private OutCndDetailService outCndDetailService;

	public CtgItemDataCndDetailDto getDataItemDetail(String condSetCd, int categoryId) {
		CtgItemDataCndDetail domain = outCndDetailService.outputExCndList(condSetCd, categoryId);
		return CtgItemDataCndDetailDto.fromDomain(domain);
	}

}