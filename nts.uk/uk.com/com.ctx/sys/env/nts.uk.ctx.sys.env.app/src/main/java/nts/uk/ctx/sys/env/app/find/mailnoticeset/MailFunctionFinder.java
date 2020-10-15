/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset;

import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.MailFunctionDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class MailFunctionFinder.
 */
@Stateless
public class MailFunctionFinder {

    /**
     * The mail function finder repository.
     */
    @Inject
    private MailFunctionRepository mailFunctionFinderRepository;

    /**
     * Find by cid and setting mail.
     *
     * @param proprietySendMailSettingAtr the propriety send mail setting atr
     * @return the list
     */
    public List<MailFunctionDto> findByCidAndSettingMail(Boolean proprietySendMailSettingAtr) {
        List<MailFunction> lstMailFunction = mailFunctionFinderRepository.findAll(proprietySendMailSettingAtr);
        return lstMailFunction.stream().map(item -> {
            MailFunctionDto dto = new MailFunctionDto();
            item.saveToMemento(dto);
            return dto;
        }).collect(Collectors.toList());

    }

    public List<MailFunctionDto> findAll() {
        return mailFunctionFinderRepository.findAll().stream()
                .map(item -> {
                    MailFunctionDto dto = new MailFunctionDto();
                    item.saveToMemento(dto);
                    return dto;
                }).collect(Collectors.toList());
    }
}
