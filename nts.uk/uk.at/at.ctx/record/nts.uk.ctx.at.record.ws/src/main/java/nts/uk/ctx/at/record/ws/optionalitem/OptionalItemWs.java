/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.optionalitem;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.optitem.OptionalItemSaveCommand;
import nts.uk.ctx.at.record.app.command.optitem.OptionalItemSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.optitem.*;
import nts.uk.ctx.at.record.app.find.optitem.language.OptionalItemNameOther;
import nts.uk.ctx.at.record.app.find.optitem.language.OptionalItemNameOtherFinder;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * The Class OptionalItemWs.
 */
@Path("ctx/at/record/optionalitem/")
@Produces("application/json")
public class OptionalItemWs extends WebService {

    /**
     * The finder.
     */
    @Inject
    private OptionalItemFinder finder;

    /**
     * The save.
     */
    @Inject
    private OptionalItemSaveCommandHandler handler;

    /**
     * The i 18 n.
     */
    @Inject
    private I18NResourcesForUK i18n;

    @Inject
    private OptionalItemNameOtherFinder optionalItemNameOtherFinder;

    /**
     * Find.
     *
     * @return the optional item dto
     */
    @POST
    @Path("find/{itemNo}/{langId}")
    public OptionalItemDto find(@PathParam("itemNo") Integer itemNo, @PathParam("langId") String langId) {
        return this.finder.findWithLang(itemNo, langId);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    @POST
    @Path("findall")
    public List<OptionalItemHeaderDto> findAll() {
        return this.finder.findAll();
    }

    @POST
    @Path("findall/{langId}")
    public List<OptionalItemHeaderDto> findAll(@PathParam("langId") String langId) {
        return this.finder.findAllWithLang(langId);
    }

    /**
     * Save.
     *
     * @param command the command
     */
    @POST
    @Path("save")
    public void save(OptionalItemSaveCommand command) {
        this.handler.handle(command);
    }

    /**
     * Gets the enum.
     *
     * @return the enum
     */
    @POST
    @Path("getenum")
    public OptItemEnumDto getEnum() {
        return OptItemEnumDto.init(i18n);
    }

    @POST
    @Path("findNameOther/{langId}")
    public List<OptionalItemNameOther> findNameOther(@PathParam("langId") String langId) {
        return this.optionalItemNameOtherFinder.findAllNameLangguage(langId);
    }

    @POST
    @Path("findByListItemNo")
    public List<OptionalItemDto> find(OptionalItemRequestDto params) {
        return this.finder.findByListNo(params.getOptionalItemNos());
    }
}
