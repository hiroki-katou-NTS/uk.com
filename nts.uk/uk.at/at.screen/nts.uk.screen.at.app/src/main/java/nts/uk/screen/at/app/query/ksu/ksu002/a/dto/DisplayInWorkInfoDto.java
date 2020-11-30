package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chungnt 【Output】 ・
 * List<勤務種類, 必須任意不要区分, 出勤休日区分> ・
 *  List<勤務予定（勤務情報）dto>
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisplayInWorkInfoDto {
	public List<WorkTypeInfomation> listWorkTypeInfo; // List<勤務種類, 必須任意不要区分, 出勤休日区分>
	public List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor; // List<勤務予定（勤務情報）dto>
}
