package nts.uk.ctx.at.request.dom.application.businesstrip.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeGetOuput {
    // 開始時刻１(Optional）
    Optional<Integer> startTime1 = Optional.empty();
    
    // 終了時刻１(Optional）
    Optional<Integer> endTime1 = Optional.empty();
    
    // 開始時刻２(Optional）
    Optional<Integer> startTime2 = Optional.empty();
    
    // 終了時刻２(Optional）
    Optional<Integer> endTime2 = Optional.empty();
}
