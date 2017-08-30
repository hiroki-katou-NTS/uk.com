package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.Application;
/**
 * 15.詳細画面申請データを取得する
 * @author tutk
 *
 */
public interface DetailScreenApplicationDataService {

	public Optional<Application> detailScreenApplicationData(String appID);
	
}
