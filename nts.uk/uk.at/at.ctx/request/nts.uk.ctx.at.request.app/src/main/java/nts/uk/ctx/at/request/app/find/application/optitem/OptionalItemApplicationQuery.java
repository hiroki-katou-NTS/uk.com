package nts.uk.ctx.at.request.app.find.application.optitem;

import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.CalcResultRangeDto;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemApplicationDetail;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemApplicationDto;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemDto;
import nts.uk.ctx.at.request.dom.adapter.OptionalItemAdapter;
import nts.uk.ctx.at.request.dom.adapter.OptionalItemImport;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OptionalItemApplicationQuery {

    @Inject
    private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;


    @Inject
    private OptionalItemAdapter optionalItemAdapter;


    @Inject
    OptionalItemApplicationRepository repository;

    public List<ControlOfAttendanceItemsDto> findControlOfAttendance(List<Integer> optionalItemNos) {
        String cid = AppContexts.user().companyId();
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no + 640).collect(Collectors.toList());
        List<ControlOfAttendanceItems> controlOfAttendanceItems = controlOfAttendanceItemsRepository.getByItemDailyList(cid, daiLyList);
        return controlOfAttendanceItems.stream().map(item -> ControlOfAttendanceItemsDto.fromDomain(item)).collect(Collectors.toList());
    }

    public OptionalItemApplicationDetail getDetail(String applicationId) {
        String cid = AppContexts.user().companyId();
        OptionalItemApplicationDetail detail = new OptionalItemApplicationDetail();
        Optional<OptionalItemApplication> byAppId = this.repository.getByAppId(cid, applicationId);
        OptionalItemApplication domain = byAppId.get();
        List<Integer> optionalItemNos = domain.getOptionalItems().stream().map(item -> item.getItemNo().v()).collect(Collectors.toList());
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no - 640).collect(Collectors.toList());
        List<OptionalItemImport> optionalItems = optionalItemAdapter.findOptionalItem(cid, daiLyList);
        List<ControlOfAttendanceItems> controlOfAttendanceItems = controlOfAttendanceItemsRepository.getByItemDailyList(cid, optionalItemNos);
        detail.setControlOfAttendanceItems(controlOfAttendanceItems.stream().map(ControlOfAttendanceItemsDto::fromDomain).collect(Collectors.toList()));
        detail.setApplication(OptionalItemApplicationDto.fromDomain(domain));
        detail.setOptionalItems(optionalItems.stream().map(item ->
                {
                    CalcResultRangeDto calcResultRangeDto = new CalcResultRangeDto();
                    item.getCalcResultRange().saveToMemento(calcResultRangeDto);
                    OptionalItemDto optionalItemDto = new OptionalItemDto();
                    optionalItemDto.setOptionalItemNo(item.getOptionalItemNo());
                    optionalItemDto.setOptionalItemName(item.getOptionalItemName());
                    optionalItemDto.setUnit(item.getOptionalItemUnit());
                    optionalItemDto.setCalcResultRange(calcResultRangeDto);
                    optionalItemDto.setOptionalItemAtr(item.getOptionalItemAtr().value);
                    return optionalItemDto;
                }
        ).collect(Collectors.toList()));
        return detail;
    }
}
