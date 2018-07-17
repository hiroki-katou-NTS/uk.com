package nts.uk.ctx.at.function.dom.adapter.standardtime;

import java.util.Optional;

/**
 * @author dat.lh
 *
 */
public interface AgreementOperationSettingAdapter {
	Optional<AgreementOperationSettingImport> find(String cid);
}
