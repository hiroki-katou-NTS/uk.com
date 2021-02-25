package nts.uk.query.app.exi;

import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ExacErrorLogQueryFinder {

    @Inject
    private ExacErrorLogRepository exacErrorLogRepository;

    public List<ExacErrorLogQueryDto> getExacErrorLogByProcessId(String externalProcessId){
        return this.exacErrorLogRepository.getExacErrorLogByProcessId(externalProcessId)
                .stream()
                .map(item -> new ExacErrorLogQueryDto(
                    item.getLogSeqNumber(),
                    item.getCid(),
                    item.getExternalProcessId(),
                    item.getCsvErrorItemName(),
                    item.getCsvAcceptedValue(),
                    item.getErrorContents(),
                    item.getRecordNumber().v(),
                    item.getLogRegDateTime(),
                    item.getItemName(),
                    item.getErrorAtr().nameId
                )).collect(Collectors.toList());
    }
}
