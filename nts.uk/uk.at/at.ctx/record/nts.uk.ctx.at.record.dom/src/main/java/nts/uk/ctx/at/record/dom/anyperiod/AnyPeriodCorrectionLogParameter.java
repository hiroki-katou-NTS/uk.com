package nts.uk.ctx.at.record.dom.anyperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AnyPeriodCorrectionLogParameter {
    private List<AnyPeriodLogContent> contents;
}
