package nts.uk.ctx.at.record.ac.appdisplayname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDispNameExp;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDisplayNameAdapter;
import nts.uk.ctx.at.request.pub.appdisplayname.export.AppDispNameExport;
import nts.uk.ctx.at.request.pub.appdisplayname.export.AppDisplayNamePub;

/**
 * @author anhdt
 *
 */
@Stateless
public class AppDisplayNameAdapterImpl implements AppDisplayNameAdapter {
	@Inject
	private AppDisplayNamePub appDisPub;

	@Override
	public List<AppDispNameExp> getAppDisplay() {
		List<AppDispNameExport> appDisplayNames = appDisPub.getAppDisplayNameByCid();

		if (!CollectionUtil.isEmpty(appDisplayNames)) {
			List <AppDispNameExp> results = new ArrayList<>();
			for(AppDispNameExport app : appDisplayNames) {
				results.add(AppDispNameExp.builder()
						.companyId(app.getCompanyId())
						.appType(app.getAppType())
						.dispName(app.getDispName())
						.build());
			}
			return results;
		}
		return Collections.emptyList();
	}
}
