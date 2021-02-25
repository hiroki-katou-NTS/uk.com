package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

/**
 * 10-1.詳細画面解除前の処理
 * @author tutk
 *
 */
public interface BeforeProcessReleasing {
	public void  detailScreenProcessBeforeReleasing(String companyId, String appId, int version);
}
