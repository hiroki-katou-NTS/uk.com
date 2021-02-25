package nts.uk.ctx.at.request.dom.application;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 就業確定済みかのチェック
 *
 */
public interface RegisterMailSendCheck {
	public ProcessResult sendMail(Application application);
}