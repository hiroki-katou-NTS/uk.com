package nts.uk.ctx.at.request.ac;

import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameQueryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.application.AtApplicationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.application.dto.AtMenuNameQueryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.application.dto.AtStandardMenuNameImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AtApplicationAdapterImpl implements AtApplicationAdapter {

    @Inject
    StandardMenuAdaptor standardMenuAdaptor;

    @Override
    public List<AtStandardMenuNameImport> getMenuDisplayName(String companyId, List<AtMenuNameQueryImport> query) {
        List<StandardMenuNameQueryImport> queryImports = query.stream()
                .map(i -> new StandardMenuNameQueryImport(i.getProgramId(), i.getScreenId(), i.getQueryString()))
                .collect(Collectors.toList());
        return standardMenuAdaptor.getMenuDisplayName(companyId, queryImports)
                .stream()
                .map(i -> new AtStandardMenuNameImport(i.getProgramId(), i.getScreenId(), i.getQueryString(), i.getDisplayName()))
                .collect(Collectors.toList());
    }
}
