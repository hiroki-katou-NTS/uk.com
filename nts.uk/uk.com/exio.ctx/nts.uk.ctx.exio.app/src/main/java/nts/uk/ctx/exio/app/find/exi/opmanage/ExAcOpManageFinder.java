package nts.uk.ctx.exio.app.find.exi.opmanage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManageRepository;

@Stateless
/**
 * 外部受入動作管理
 */
public class ExAcOpManageFinder {

	@Inject
	private ExAcOpManageRepository finder;

	public List<ExAcOpManageDto> getAllExAcOpManage() {
		return finder.getAllExAcOpManage().stream().map(item -> ExAcOpManageDto.fromDomain(item))
				.collect(Collectors.toList());
	}

}
