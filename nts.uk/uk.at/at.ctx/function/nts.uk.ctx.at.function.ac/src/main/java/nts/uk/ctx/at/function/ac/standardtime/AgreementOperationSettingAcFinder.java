package nts.uk.ctx.at.function.ac.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingPub;

@Stateless
public class AgreementOperationSettingAcFinder implements AgreementOperationSettingAdapter {
	@Inject
	private AgreementOperationSettingPub pub;
	@Override
	public AgreementOperationSettingImport find() {
		return pub.find().map(f -> new AgreementOperationSettingImport(f.getStartingMonth())).orElse(null);
	}
}
