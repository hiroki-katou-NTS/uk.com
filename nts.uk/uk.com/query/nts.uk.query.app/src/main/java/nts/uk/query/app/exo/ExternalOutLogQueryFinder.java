package nts.uk.query.app.exo;

import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ExternalOutLogQueryFinder {

    @Inject
    private ExternalOutLogRepository externalOutLogRepository;

    public List<ExternalOutLogQueryDto> getExternalOutLogById(String cid, String outProcessId, int processingClassification){
        return this.externalOutLogRepository.getExternalOutLogById(cid,outProcessId,processingClassification)
                .stream()
                .map(item -> new ExternalOutLogQueryDto(
                        item.getCompanyId(),
                        item.getOutputProcessId(),
                        item.getErrorContent(),
                        item.getErrorTargetValue(),
                        item.getErrorDate(),
                        item.getErrorEmployee(),
                        item.getErrorItem(),
                        item.getLogRegisterDateTime(),
                        item.getLogSequenceNumber(),
                        item.getProcessCount(),
                        item.getProcessContent().nameId
                ))
                .collect(Collectors.toList());
    }
}
