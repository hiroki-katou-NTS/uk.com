package nts.uk.ctx.at.request.app.find.application.optitem;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.app.command.application.optionalitem.OptionalItemApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.*;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetFinder;
import nts.uk.ctx.at.request.dom.adapter.OptionalItemAdapter;
import nts.uk.ctx.at.request.dom.adapter.OptionalItemImport;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationTypeCode;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OptionalItemApplicationQuery {

    private static final Integer OPTIONAL_ITEM_NO_CONVERT_CONST = 640;

    @Inject
    private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;


    @Inject
    private OptionalItemAdapter optionalItemAdapter;


    @Inject
    private OptionalItemApplicationRepository repository;

    @Inject
    private OptionalItemRepository optionalItemRepository;

    @Inject
    private OptionalItemAppSetFinder optionalItemAppSetFinder;


    /**
     * ドメインモデル「任意項目」を取得する
     */
    public List<ControlOfAttendanceItemsDto> findControlOfAttendance(List<Integer> optionalItemNos) {
        String cid = AppContexts.user().companyId();
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no + OPTIONAL_ITEM_NO_CONVERT_CONST).collect(Collectors.toList());
        List<ControlOfAttendanceItems> controlOfAttendanceItems = controlOfAttendanceItemsRepository.getByItemDailyList(cid, daiLyList);
        return controlOfAttendanceItems.stream().map(item -> ControlOfAttendanceItemsDto.fromDomain(item)).collect(Collectors.toList());
    }

    public OptionalItemApplicationDetail getDetail(String applicationId) {
        String cid = AppContexts.user().companyId();
        OptionalItemApplicationDetail detail = new OptionalItemApplicationDetail();
        Optional<OptionalItemApplication> byAppId = this.repository.getByAppId(cid, applicationId);
        OptionalItemApplication domain = byAppId.get();
        String settingCode = domain.getCode().v();
        OptionalItemAppSetDto setting = optionalItemAppSetFinder.findByCode(new OptionalItemApplicationTypeCode(settingCode).v());
        List<Integer> optionalItemNos = domain.getOptionalItems().stream().map(item -> item.getItemNo().v()).collect(Collectors.toList());
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no - OPTIONAL_ITEM_NO_CONVERT_CONST).collect(Collectors.toList());
        List<OptionalItemImport> optionalItems = optionalItemAdapter.findOptionalItem(cid, daiLyList);
        List<ControlOfAttendanceItems> controlOfAttendanceItems = controlOfAttendanceItemsRepository.getByItemDailyList(cid, optionalItemNos);
        detail.setControlOfAttendanceItems(controlOfAttendanceItems.stream().map(ControlOfAttendanceItemsDto::fromDomain).collect(Collectors.toList()));
        detail.setApplication(OptionalItemApplicationDto.fromDomain(domain));
        detail.getApplication().setName(setting.getName());
        detail.getApplication().setNote(setting.getNote());
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

    public void checkBeforeUpdate(OptionalItemApplicationCommand params) {
        String cid = AppContexts.user().companyId();
        /*登録時チェック処理（全申請共通）*/
        List<AnyItemValueDto> optionalItems = params.getOptionalItems();
        boolean register = false;
        List<Integer> optionalItemNos = optionalItems.stream().map(anyItemNo -> anyItemNo.getItemNo()).collect(Collectors.toList());
        Map<Integer, OptionalItem> optionalItemMap = optionalItemRepository.findByListNos(cid, optionalItemNos).stream().collect(Collectors.toMap(optionalItem -> optionalItem.getOptionalItemNo().v(), item -> item));
        List<Integer> daiLyList = optionalItemNos.stream().map(no -> no + OPTIONAL_ITEM_NO_CONVERT_CONST).collect(Collectors.toList());
        Map<Integer, ControlOfAttendanceItems> controlOfAttendanceItemsMap = controlOfAttendanceItemsRepository.getByItemDailyList(cid, daiLyList).stream().collect(Collectors.toMap(item -> item.getItemDailyID(), item -> item));
        for (Iterator<AnyItemValueDto> iterator = optionalItems.iterator(); iterator.hasNext(); ) {
            AnyItemValueDto inputOptionalItem = iterator.next();
            /* Kiểm tra giá trị nằm trong giới hạn, vượt ra ngoài khoảng giới hạn thì thông báo lỗi Msg_1692 */
            ControlOfAttendanceItems controlOfAttendanceItems = controlOfAttendanceItemsMap.get(inputOptionalItem.getItemNo() + OPTIONAL_ITEM_NO_CONVERT_CONST);
            Optional<TimeInputUnit> unit = controlOfAttendanceItems.getInputUnitOfTimeItem();
            /* kiểm tra bội của đơn vị, không phải là bội thì thông báo lỗi Msg_1693*/
            OptionalItem optionalItem = optionalItemMap.get(inputOptionalItem.getItemNo());
            CalcResultRange range = optionalItem.getCalcResultRange();
            if (inputOptionalItem.getAmount() != null) {
                Integer amountLower = null;
                Integer amountUpper = null;
                Integer amount = inputOptionalItem.getAmount();
                if (range.getAmountRange().isPresent() && range.getAmountRange().get().getLowerLimit().isPresent()) {
                    amountLower = range.getAmountRange().get().getLowerLimit().get().v();
                }
                if (range.getAmountRange().isPresent() && range.getAmountRange().get().getUpperLimit().isPresent()) {
                    amountLower = range.getAmountRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && amountLower != null && amountLower.compareTo(amount) > 0)
                        || (range.getUpperLimit().isSET() && amountUpper != null && amountUpper.compareTo(amount) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
//                if (unit.isPresent() && (amount % unit.get().value != 0)) {
//                    throw new BusinessException("Msg_1693");
//                }
                register = true;
            }
            if (inputOptionalItem.getTimes() != null) {
                Double numberLower = null;
                Double numberUpper = null;
                BigDecimal times = inputOptionalItem.getTimes();
                if (range.getNumberRange().isPresent() && range.getNumberRange().get().getLowerLimit().isPresent()) {
                    numberLower = range.getNumberRange().get().getLowerLimit().get().v();
                }
                if (range.getNumberRange().isPresent() && range.getNumberRange().get().getUpperLimit().isPresent()) {
                    numberUpper = range.getNumberRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && numberLower != null && numberLower.compareTo(times.doubleValue()) > 0)
                        || (range.getUpperLimit().isSET() && numberUpper != null && numberUpper.compareTo(times.doubleValue()) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
//                if (unit.isPresent() && (times % unit.get().value != 0)) {
//                    throw new BusinessException("Msg_1693");
//                }
                register = true;
            }
            if (inputOptionalItem.getTime() != null) {
                Integer timeLower = null;
                Integer timeUpper = null;
                Integer time = inputOptionalItem.getTime();
                if (range.getTimeRange().isPresent() && range.getTimeRange().get().getLowerLimit().isPresent()) {
                    timeLower = range.getTimeRange().get().getLowerLimit().get().v();
                }
                if (range.getTimeRange().isPresent() && range.getTimeRange().get().getUpperLimit().isPresent()) {
                    timeUpper = range.getTimeRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && timeLower != null && timeLower.compareTo(time) > 0)
                        || (range.getUpperLimit().isSET() && timeUpper != null && timeUpper.compareTo(time) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
//                if (unit.isPresent() && (time % unit.get().value != 0)) {
//                    throw new BusinessException("Msg_1693", "KAF020_22");
//                }
                register = true;
            }
        }
        /*Không có dữ liệu thì hiển thị lỗi*/
        if (!register) {
            throw new BusinessException("Msg_1691");
        }
    }
}
