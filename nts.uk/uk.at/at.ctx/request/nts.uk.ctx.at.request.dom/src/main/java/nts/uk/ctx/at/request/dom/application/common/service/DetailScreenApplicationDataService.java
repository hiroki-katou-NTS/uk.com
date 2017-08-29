package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.Application;

public interface DetailScreenApplicationDataService {

	public Optional<Application> detailScreenApplicationDataService(String appID);
	
}
