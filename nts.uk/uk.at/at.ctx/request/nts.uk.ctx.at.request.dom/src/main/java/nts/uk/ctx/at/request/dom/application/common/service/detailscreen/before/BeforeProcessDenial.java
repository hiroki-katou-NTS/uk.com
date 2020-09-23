package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

/**
 * 9-1.詳細画面否認前の処理
 * @author tutk
 *
 */
public interface BeforeProcessDenial {
	public void detailedScreenProcessBeforeDenial(String companyId, String appId, int version);
}
