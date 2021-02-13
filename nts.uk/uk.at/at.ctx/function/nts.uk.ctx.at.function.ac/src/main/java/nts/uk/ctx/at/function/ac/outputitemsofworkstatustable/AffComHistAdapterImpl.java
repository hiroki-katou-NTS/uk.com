package nts.uk.ctx.at.function.ac.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.AffiliationStatusDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmpAffInfoExportDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.PeriodInformationDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetAffiliationPeriodPub;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AffComHistAdapterImpl implements AffComHistAdapter {
    @Inject
    private SyCompanyRecordAdapter syCompanyRecordAdapter;
    @Inject
    private GetAffiliationPeriodPub getAffiliationPeriodPub;

    @Override
    public List<StatusOfEmployee> getListAffComHist(List<String> sid, DatePeriod datePeriod) {
        val rs = syCompanyRecordAdapter
                .getListAffComHistByListSidAndPeriod(sid, datePeriod);
        if (rs.isEmpty())
            return Collections.emptyList();
        return rs.stream().map(e -> new StatusOfEmployee(
                e.getEmployeeId(),
                e.getListPeriod()
        )).collect(Collectors.toList());
    }

    @Override
    public EmpAffInfoExportDto getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate) {
        val rs = getAffiliationPeriodPub.getAffiliationPeriod(listSid, YMPeriod, baseDate);
        return new EmpAffInfoExportDto(
                rs.getAffiliationStatus().stream().map(e -> new AffiliationStatusDto(
                        e.getEmployeeID(),
                        e.getPeriodInformation().stream().map(i -> new PeriodInformationDto(
                                i.getDatePeriod(),
                                i.getYearMonthPeriod()
                        )).collect(Collectors.toList()),
                        e.isNoEmployment()
                )).collect(Collectors.toList())
        );
    }
}
