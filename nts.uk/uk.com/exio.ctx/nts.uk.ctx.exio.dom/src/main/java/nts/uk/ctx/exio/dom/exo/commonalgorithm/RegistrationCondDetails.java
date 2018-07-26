package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;

@Stateless
public class RegistrationCondDetails {
	@Inject
	private OutCndDetailRepository stdOutCndDetailRepo;

	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepo;

	@Inject
	private SearchCodeListRepository searchCodeListRepo;

	/**
	 * 外部出力登録条件詳細
	 * 
	 * @param outCndDetail
	 *            条件詳細保存内容
	 * @param standardAtr
	 *            定型区分(定型/ユーザ)
	 * @param registerMode
	 *            登録モード(新規/更新)
	 */
	public void algorithm(Optional<OutCndDetail> outCndDetail, StandardAtr standardAtr, RegisterMode registerMode) {
		if (!outCndDetail.isPresent()) {
			return;
		}
		if (StandardAtr.STANDARD.equals(standardAtr)) {
			if (RegisterMode.NEW.equals(registerMode)) {
				stdOutCndDetailRepo.add(outCndDetail.get());
				for (OutCndDetailItem detailItem : outCndDetail.get().getListOutCndDetailItem()) {
					outCndDetailItemRepo.add(detailItem);
					for (SearchCodeList searchCodeList : detailItem.getListSearchCodeList()) {
						searchCodeListRepo.add(searchCodeList);
					}
				}
			} else {
				stdOutCndDetailRepo.update(outCndDetail.get());
				for (OutCndDetailItem detailItem : outCndDetail.get().getListOutCndDetailItem()) {
					outCndDetailItemRepo.update(detailItem);
					for (SearchCodeList searchCodeList : detailItem.getListSearchCodeList()) {
						searchCodeListRepo.update(searchCodeList);
					}
				}
			}
		}
	}
}
