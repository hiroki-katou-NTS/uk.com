package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder

//	残数データ情報パラメータ
public class RemainInfoData {

	//	発生管理データID
	Optional<String> occurrenceId;
	
	//	発生時間数
	Optional<Integer> occurrenceHour; 
	
	//	発生日数
	Optional<Double> occurrenceDay;
	
	//	発生日
	Optional<GeneralDate> accrualDate;
	
	//	消化管理データID
	Optional<String> digestionId;
	
	//	消化時間数
	Optional<Integer> digestionTimes;
	
	//	/消化日数
	Optional<Double> digestionDays;
	
	//	消化日
	Optional<GeneralDate> digestionDay;
	
	//	法定区分
	Optional<Integer> legalDistinction;
	
	//	残数時間数
	Optional<Integer> remainingHours;
	
	//	残数日数
	Double dayLetf;
	
	//	期限日
	Optional<GeneralDate> deadLine;
	
	//	使用期限時間数
	Integer usedTime;
	
	//	使用期限日数
	Double usedDay;
}
