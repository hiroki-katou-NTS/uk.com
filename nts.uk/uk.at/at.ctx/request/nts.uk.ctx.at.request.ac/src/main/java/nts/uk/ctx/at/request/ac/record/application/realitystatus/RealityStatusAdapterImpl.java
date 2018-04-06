package nts.uk.ctx.at.request.ac.record.application.realitystatus;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.application.realitystatus.RealityStatusPub;
import nts.uk.ctx.at.record.pub.application.realitystatus.UseSetingExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.application.realitystatus.RealityStatusAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.application.realitystatus.UseSetingImport;

/**
 * @author dat.lh
 *
 */
@Stateless
public class RealityStatusAdapterImpl implements RealityStatusAdapter {
	@Inject
	RealityStatusPub realityStatusPub;

	@Override
	public UseSetingImport getUseSetting(String cid) {
		UseSetingExport setting = realityStatusPub.getUseSetting(cid);
		return new UseSetingImport(setting.isMonthlyConfirm(), setting.isUseBossConfirm(),
				setting.isUsePersonConfirm());
	}
}
