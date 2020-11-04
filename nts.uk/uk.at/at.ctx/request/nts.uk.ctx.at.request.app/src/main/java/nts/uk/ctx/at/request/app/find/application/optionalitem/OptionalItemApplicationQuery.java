package nts.uk.ctx.at.request.app.find.application.optionalitem;

import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OptionalItemApplicationQuery {

    @Inject
    private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;

    public List<ControlOfAttendanceItemsDto> findControlOfAttendance(List<Integer> optionalItemNos) {
        String cid = AppContexts.user().companyId();
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no + 640).collect(Collectors.toList());
        List<ControlOfAttendanceItems> controlOfAttendanceItems = controlOfAttendanceItemsRepository.getByItemDailyList(cid, daiLyList);
        return controlOfAttendanceItems.stream().map(item -> ControlOfAttendanceItemsDto.fromDomain(item)).collect(Collectors.toList());
    }
}
