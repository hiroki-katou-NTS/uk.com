package nts.uk.ctx.at.record.dom.anyperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class AnyPeriodCorrectionLogParameter implements Serializable {
    private List<AnyPeriodLogContent> contents;
}
