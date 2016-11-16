package nts.uk.ctx.pr.proto.app.layoutmasterdetail.find;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;

@RequestScoped
public class LayoutMasterDetailFinder {
	@Inject
	private LayoutMasterDetailRepository repository;
	
}
