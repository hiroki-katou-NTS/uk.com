package nts.uk.ctx.at.request.dom.application.common.adapter.application;

import nts.uk.ctx.at.request.dom.application.common.adapter.application.dto.AtStandardMenuNameImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.application.dto.AtMenuNameQueryImport;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public interface AtApplicationAdapter {

    List<AtStandardMenuNameImport> getMenuDisplayName(String companyId, List<AtMenuNameQueryImport> query);

}
